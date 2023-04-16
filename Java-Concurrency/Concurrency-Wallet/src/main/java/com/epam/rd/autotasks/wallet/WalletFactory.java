package com.epam.rd.autotasks.wallet;

import java.util.List;

/**
 * Wallet creator. Is used to create a {@linkplain Wallet} instance.
 * <p/>
 * You need to specify your implementation in {@linkplain #wallet(List, PaymentLog)} method.
 */
public final class WalletFactory {

    private WalletFactory() {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates new {@linkplain Wallet} instance and passes {@code accounts} and {@code log} to it.
     * <p/>
     * You must return your implementation here.
     *
     * @param accounts which will be used for payments
     * @param log which will be used to log payments
     * @return new {@linkplain Wallet} instance
     */
    public static Wallet wallet(List<Account> accounts, PaymentLog log) {
        return new WalletImpl(accounts, log);
    }
}
