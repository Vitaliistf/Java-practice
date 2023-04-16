package com.epam.rd.autotasks.wallet;

import java.util.List;
import java.util.concurrent.locks.Lock;

public class WalletImpl implements Wallet{
    List<Account> accounts;
    PaymentLog log;
    public WalletImpl(List<Account> accounts, PaymentLog log){
        this.accounts = accounts;
        this.log = log;
    }
    @Override
    public void pay(String recipient, long amount) throws Exception {
        for (Account account : accounts) {
            if(account.balance() >= amount) {
                Lock accountLock = account.lock();
                accountLock.lock();
                account.pay(amount);
                accountLock.unlock();
                log.add(account, recipient, amount);

                return;
            }
        }
        throw new ShortageOfMoneyException(recipient, amount);
    }
}
