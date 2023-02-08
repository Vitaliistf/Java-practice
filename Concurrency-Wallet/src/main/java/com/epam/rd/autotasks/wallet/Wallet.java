package com.epam.rd.autotasks.wallet;

/**
 * Wallet is a aggregation of multiple accounts and payment log.
 * <p/>
 * The goal of the wallet is to find account with enough money to register payment
 * and log it using payment log.
 * <p/>
 * Wallet must work properly in multi-threaded environment, so you need to keep in mind
 * synchronization between multiple accounts and the fact that their state changes after
 * successful payment. You must only use advanced synchronization primitives such as locks,
 * semaphores, barriers, etc.
 * <p/>
 * You need to implement this interface in order to complete the task.
 *
 * @see Account
 * @see PaymentLog
 * @see ShortageOfMoneyException
 */
public interface Wallet {

    /**
     * Executes payment by looking for an account which balance is greater or equal to {@code amount},
     * subtracts it from accounts balance and log this operation using payment log.
     * <p/>
     * Be aware that this method will be executed in multithreaded environment. Moreover you must use
     * only advanced synchronization primitives such as locks, semaphores, barriers, etc.
     * <p/>
     * This method throws {@code Exception} just for convenince, so you don't have to catch all
     * checked exception such as {@linkplain InterruptedException}. But if any exception is thrown
     * besides {@linkplain ShortageOfMoneyException}, it'll be count as an error.
     *
     * @param recipient who must receive payment
     * @param amount of money which must be paid
     * @throws ShortageOfMoneyException when no account with enough money is found
     */
    public void pay(String recipient, long amount) throws Exception;
}
