package com.epam.rd.autotasks.wallet;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleAccount implements Account {

    private final String name;
    private long balance;

    private final Lock lock = new ReentrantLock();

    public SimpleAccount(String name, long balance) {
        this.name = name;
        this.balance = balance;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void pay(long price) {
        balance -= price;
    }

    @Override
    public long balance() {
        return balance;
    }

    @Override
    public Lock lock() {
        return lock;
    }

    @Override
    public String toString() {
        return "SimpleAccount{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SimpleAccount that = (SimpleAccount) o;
        return balance == that.balance && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, balance);
    }
}
