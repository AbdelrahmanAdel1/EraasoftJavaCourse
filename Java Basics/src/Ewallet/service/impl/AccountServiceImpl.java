package Ewallet.service.impl;

import Ewallet.model.Account;
import Ewallet.model.WalletSystem;
import Ewallet.service.AccountService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    private WalletSystem walletSystem;
    private static final DateTimeFormatter LOG_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public AccountServiceImpl(WalletSystem walletSystem) {
        this.walletSystem = walletSystem;
    }

    @Override
    public boolean createAccount(Account account) {
        List<Account> accounts = walletSystem.getAccounts();
        for (Account acc : accounts) {
            if (acc.getUserName().equals(account.getUserName())) {
                return false;
            }
        }
        walletSystem.addAccount(account);
        return true;
    }

    @Override
    public Account findAccount(Account account) {
        List<Account> accounts = walletSystem.getAccounts();
        for (Account acc : accounts) {
            if (acc.getUserName().equals(account.getUserName()) && acc.getPassword().equals(account.getPassword())) {
                return acc;
            }
        }
        return null;
    }

    @Override
    public boolean withdraw(Account account, int withdrawAmount) {
        if(findAccount(account)==null) {
            System.out.println("Your Account Does not exist, please make sure of the provided credentials");
            return false;
        }

        if(account.getBalance()<withdrawAmount)
        {
            return false;
        }
        else {
            account.setBalance(account.getBalance()-withdrawAmount);
            account.getAccountLog().add("[" + LocalDateTime.now().format(LOG_FORMATTER) + "] "+ "Withdraw : "+withdrawAmount);
            return true;
        }
    }

    @Override
    public boolean deposit(Account account, int depositAmount) {
        if(findAccount(account)==null) {
            System.out.println("Your Account Does not exist, please make sure of the provided credentials");
            return false;
        }
        try
        {
            account.setBalance(account.getBalance()+depositAmount);
            account.getAccountLog().add("[" + LocalDateTime.now().format(LOG_FORMATTER) + "] "+ "Deposite : "+depositAmount);
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            return false;
        }
    }

    @Override
    public void showProfile(Account account) {
        if(findAccount(account)==null) {
            System.out.println("Your Account Does not exist, please make sure of the provided credentials");
            return;
        }
        System.out.println("Account Information:");
        System.out.println("Name: "+account.getUserName());
        System.out.println("Balance: "+ account.getBalance());
        System.out.println("Account Status: "+ account.isActive());
    }

    @Override
    public boolean transfer(Account account, String receiverAccount, double transferAmount) {
        if(findAccount(account)==null) {
            System.out.println("Your Account Does not exist, please make sure of the provided credentials");
            return false;
        }
        if(account.getBalance()<transferAmount){
            System.out.println("You don't have enough balance for the transfer");
            return false;
        }
        List<Account> accounts = walletSystem.getAccounts();
        for(Account acc : accounts){
            if(acc.getUserName().equals(receiverAccount)){
                account.setBalance(account.getBalance()-transferAmount);
                account.getAccountLog().add("[" + LocalDateTime.now().format(LOG_FORMATTER) + "] "+ "Transferred : "+transferAmount+" To "+ acc.getUserName());
                acc.setBalance(acc.getBalance()+transferAmount);
                acc.getAccountLog().add("[" + LocalDateTime.now().format(LOG_FORMATTER) + "] "+ "Received : "+transferAmount+" From "+account.getUserName());
                return true;
            }
        }
        System.out.println("Invalid receiver name");
        return false;
    }

    @Override
    public void showTransactions(Account account) {
        if(findAccount(account)==null) {
            System.out.println("Your Account Does not exist, please make sure of the provided credentials");
            return;
        }
        for(String tran: account.getAccountLog())
        {
            System.out.println(tran);
        }
    }
}