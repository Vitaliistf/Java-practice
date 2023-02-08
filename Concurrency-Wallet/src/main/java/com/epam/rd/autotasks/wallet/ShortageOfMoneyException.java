package com.epam.rd.autotasks.wallet;

/**
 * This exception is used to indicate that wallet is shortage of money and
 * could not register payment.
 * <p/>
 * It must contain valid information about payment such as recipient and amount
 * which were used.
 */
public class ShortageOfMoneyException extends Exception {

    private final String recipient;
    private final long amount;

    public ShortageOfMoneyException(String recipient, long amount) {
        super(String.format("Not enough money to pay %d to %s", amount, recipient));
        this.recipient = recipient;
        this.amount = amount;
    }

    public String getRecipient() {
        return recipient;
    }

    public long getAmount() {
        return amount;
    }
}
