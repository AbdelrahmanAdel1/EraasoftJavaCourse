package Ewallet.service;

import Ewallet.model.Account;

public interface AccountService {
    boolean createAccount(Account account);
    Account findAccount(Account account);
    boolean withdraw(Account account,int withdrawAmount);
    boolean deposit(Account account,int depositAmount);
    void showProfile(Account account);
    boolean transfer(Account account,String receiverAccount, double transferAmount);
    void showTransactions(Account account);
}