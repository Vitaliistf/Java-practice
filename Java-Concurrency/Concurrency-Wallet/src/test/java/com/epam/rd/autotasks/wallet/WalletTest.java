package com.epam.rd.autotasks.wallet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.epam.rd.autotasks.wallet.WalletFactory.wallet;
import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WalletTest {

    private List<Account> accounts;
    private SimplePaymentLog log;

    @BeforeEach
    void setUp() {
        accounts = new ArrayList<>(List.of(
                new SimpleAccount("cash", 50),
                new SimpleAccount("debit", 200),
                new SimpleAccount("credit", 500)
        ));
        log = new SimplePaymentLog();
    }

    @Test
    @DisplayName("'synchrnozied' keyword is not used")
    void testNoSynchronizedKeywordIsUsed() throws Exception {
        boolean containsSynchronizedKeyword = Files.walk(Paths.get("src", "main", "java"))
                .filter(p -> p.getFileName().toString().endsWith(".java"))
                .flatMap(p -> {
                    try {
                        return Files.lines(p);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .anyMatch(line -> line.matches(".*\\bsynchronized\\b.*"));
        assertFalse(containsSynchronizedKeyword);
    }

    @Test
    @DisplayName("Wallet can be successfully created")
    void testWalletCreation() {
        var wallet = Assertions.assertDoesNotThrow(() -> wallet(accounts, log));
        assertNotNull(wallet);
    }

    @Test
    @DisplayName("Newly created wallet has a proper state and does not affected by accounts changes")
    void testNewlyWalletStateAndRobust() {
        Wallet wallet = wallet(accounts, log);
        List<Account> accounts = List.copyOf(this.accounts);
        this.accounts.clear();

        assertEquals(3, accounts.size());
        assertEquals(new SimpleAccount("cash", 50), accounts.get(0));
        assertEquals(new SimpleAccount("debit", 200), accounts.get(1));
        assertEquals(new SimpleAccount("credit", 500), accounts.get(2));
        assertTrue(log.all().isEmpty());
    }

    @Test
    @DisplayName("Wallet throws exception when it does not have enough money to pay on all accounts")
    void testShortageOfMoney() {
        Wallet wallet = wallet(accounts, log);
        assertThrows(ShortageOfMoneyException.class, () -> wallet.pay("recipient", 1_000_000));
    }

    @Test
    @DisplayName("Wallet is valid in sequential execution")
    void testWalletWorkflowSequentially() throws Exception {
        Wallet wallet = wallet(accounts, log);
        long startBalance = totalBalance();
        AtomicLong totalPaid = new AtomicLong();
        final long price = 7;
        int counts = 100;

        for (int i = 1; i <= counts; ++i) {
            final int iRef = i;
            assertDoesNotThrow(() -> executePayment(wallet, "recipient-" + iRef, price, totalPaid));
        }

        checkWallet(startBalance, totalPaid.get(), counts, counts);
    }

    @ParameterizedTest(name = "Parallelism = {0}")
    @ValueSource(ints = {1, 2, 3, 4, 8, 16, 32})
    @DisplayName("Parallel execution test")
    void testingParallelism(int parallelism) throws Exception {
        Wallet wallet = wallet(accounts, log);
        long startBalance = totalBalance();
        AtomicLong totalPaid = new AtomicLong();
        long price = 7;
        int counts = 100;
        ExecutorService executor = Executors.newFixedThreadPool(parallelism);

        Stream<Callable<Void>> callables = IntStream.rangeClosed(1, counts)
                .mapToObj(i -> "recipient-" + i)
                .map(recipient -> (Callable<Void>) () -> {
                    executePayment(wallet, recipient, price, totalPaid);
                    return null;
                });
        Collection<Future<Void>> futures = toFutures(executor, callables);
        futures.forEach(f -> {
            try {
                f.get();
            } catch (Exception e) {
                throw new AssertionError("One of the transaction failed", e);
            }
        });

        checkWallet(startBalance, totalPaid.get(), counts, counts);
    }

    @ParameterizedTest(name = "Parallelism = {0}")
    @DisplayName("Parallel execution test with non-unique recipients")
    @ValueSource(ints = {1, 2, 3, 4, 8, 16, 32})
    void testWithNonUniqueRecipients(int parallelism) throws Exception {
        Wallet wallet = wallet(accounts, log);
        long startBalance = totalBalance();
        AtomicLong totalPaid = new AtomicLong();
        Map<String, Long> recipientLog = new ConcurrentHashMap<>();
        final Random rnd = new Random(System.currentTimeMillis());
        ExecutorService executor = Executors.newFixedThreadPool(parallelism);

        Stream<Callable<Void>> callables = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> "recipient-" + i)
                .peek(recipient -> recipientLog.putIfAbsent(recipient, 0L))
                .flatMap(recipient -> rnd.ints(10, 1, 5)
                        .mapToObj(i -> entry(recipient, i)))
                .map(entry -> (Callable<Void>) () -> {
                    final String recipient = entry.getKey();
                    final Integer amount = entry.getValue();
                    recipientLog.compute(recipient, (k, v) -> v + amount);
                    executePayment(wallet, recipient, amount, totalPaid);
                    return null;
                });
        Collection<Future<Void>> futures = toFutures(executor, callables);

        checkWallet(startBalance, totalPaid.get(), 100, 10);

        Map<String, Long> spendByRecipient = log.all().stream().collect(Collectors.toMap(
                r -> r.recipient,
                r -> r.amount,
                Long::sum
        ));
        assertEquals(recipientLog.size(), spendByRecipient.size());
        spendByRecipient.forEach((recipient, total) -> {
            assertTrue(recipientLog.containsKey(recipient));
            assertEquals(recipientLog.get(recipient), total);
            recipientLog.remove(recipient);
        });
        assertTrue(recipientLog.isEmpty());
    }

    @ParameterizedTest(name = "Parallelism = {0}")
    @ValueSource(ints = {1, 2, 3, 4, 8, 16, 32})
    @DisplayName("Wallet handles shortage properly in concurrent execution")
    void testWithShortage(int parallelism) throws Exception {
        Wallet wallet = wallet(List.of(
                new SimpleAccount("acc-1", 10),
                new SimpleAccount("acc-2", 15),
                new SimpleAccount("acc-3", 25)
        ), log);
        ExecutorService executor = Executors.newFixedThreadPool(parallelism);

        Stream<Callable<Void>> callables = IntStream.generate(() -> 5).limit(15)
                .mapToObj(amount -> (Callable<Void>) () -> {
                    wallet.pay("recipient", amount);
                    return null;
                });
        Collection<Future<Void>> futures = toFutures(executor, callables);

        final AtomicInteger errorCounter = new AtomicInteger();
        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException e) {
                throw new IllegalStateException("Unexpected exception", e);
            } catch (ExecutionException e) {
                if (e.getCause() == null || !(e.getCause() instanceof ShortageOfMoneyException)) {
                    throw new IllegalStateException("Unexpected exception", e);
                }
                errorCounter.incrementAndGet();
                ShortageOfMoneyException cause = (ShortageOfMoneyException) e.getCause();
                assertEquals("recipient", cause.getRecipient());
                assertEquals(5, cause.getAmount());
            }
        });
        assertEquals(5, errorCounter.get());
    }

    private Collection<Future<Void>> toFutures(
            ExecutorService executor,
            Stream<? extends Callable<Void>> callables) throws Exception {
        List<? extends Callable<Void>> list = callables.collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(list);
        List<Future<Void>> futures = executor.invokeAll(list);
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        return futures;
    }

    private long totalBalance() {
        return accounts.stream().mapToLong(Account::balance).sum();
    }

    private void executePayment(Wallet wallet, String recipient, long price, AtomicLong totalPaid)
            throws Exception {
        totalPaid.addAndGet(price);
        wallet.pay(recipient, price);
    }

    private void checkWallet(long startBalance, long totalPaid, int transactionsCount, int uniqueRecipients) {
        checkResultingBalanceAndCummulativePrice(startBalance, totalPaid);
        checkOverdraftAbsence();
        checkLog(transactionsCount, uniqueRecipients, totalPaid);
    }

    private void checkResultingBalanceAndCummulativePrice(long startBalance, long totalPaid) {
        long currentBalance = totalBalance();
        assertEquals(totalPaid, startBalance - currentBalance);
    }

    private void checkOverdraftAbsence() {
        Boolean overdraftIsAbsent = accounts.stream()
                .map(Account::balance)
                .map(balance -> balance >= 0)
                .reduce(Boolean::logicalAnd)
                .orElse(true);
        assertTrue(overdraftIsAbsent);
    }

    private void checkLog(int transactionsCount, int uniqueRecipients, long totalPaid) {
        List<SimplePaymentLog.Record> records = log.all();
        assertEquals(transactionsCount, records.size());
        checkUniqueRecipientsAmount(records.stream(), uniqueRecipients);
        checkLoggedSpendedMoney(records.stream(), totalPaid);
    }

    private void checkUniqueRecipientsAmount(Stream<SimplePaymentLog.Record> records, int expectedSize) {
        long uniqueRecipients = records
                .map(record -> record.recipient)
                .distinct()
                .count();
        assertEquals(expectedSize, uniqueRecipients);
    }

    private void checkLoggedSpendedMoney(Stream<SimplePaymentLog.Record> records, long totalPaid) {
        long loggedSpends = records.mapToLong(record -> record.amount).sum();
        assertEquals(totalPaid, loggedSpends);
    }
}
