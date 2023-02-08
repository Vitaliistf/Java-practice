package com.epam.rd.autotasks.wallet;

/**
 * This interface is used to log the successful payments.
 * <p/>
 * You don't need to implement this interface in order to complete the task.
 */
public interface PaymentLog {

    /**
     * Logs information about successful payment.
     *
     * @param account which handled the payment
     * @param recipient whom will receive payment
     * @param amount of money which was paid
     */
    void add(Account account, String recipient, long amount);
}
