package com.epam.rd.autotasks.wallet;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimplePaymentLog implements PaymentLog {

    static class Record {
        final Account account;
        final String recipient;
        final long amount;

        private Record(Account account, String recipient, long amount) {
            this.account = account;
            this.recipient = recipient;
            this.amount = amount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Record record = (Record) o;
            return amount == record.amount && account.equals(record.account) && recipient.equals(record.recipient);
        }

        @Override
        public int hashCode() {
            return Objects.hash(account, recipient, amount);
        }
    }

    private final List<Record> records = new CopyOnWriteArrayList<>();

    @Override
    public void add(Account account, String recipient, long amount) {
        records.add(new Record(account, recipient, amount));
    }

    public List<Record> all() {
        return records;
    }
}
