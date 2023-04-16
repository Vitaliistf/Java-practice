package com.epam.rd.autotasks.wallet;

import java.util.concurrent.locks.Lock;

/**
 * Represents named account wwith balance checking and payment features. Besides
 * it provides lock method which might be convenient in concurrent environment.
 * <p/>
 * Account by default does not support any overdraft (its balance always non-negative).
 * Accounts balance can not be increased after creation.
 * Accounts name and lock does not change after creation.
 * <p/>
 * You don't need to implement this interface in order to complete the task.
 */
public interface Account {

    /**
     * Returns name of this account. The same account instance will always return the same value.
     *
     * @return non-null, non-blank string
     */
    String name();

    /**
     * Reduces accounts balance by {@code price}.
     *
     * @param price defines how much must be subtracted from accounts balance.
     */
    void pay(long price);

    /**
     * Returns current value of accounts balance.
     *
     * @return non-negative value
     */
    long balance();

    /**
     * Returns {@linkplain Lock} associated with this account. Always the same for the same instance.
     *
     * @return non-null {@linkplain Lock} instance
     */
    Lock lock();
}
