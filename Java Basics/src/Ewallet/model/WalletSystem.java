package Ewallet.model;

import java.util.ArrayList;
import java.util.List;

public class WalletSystem {
    private final String walletName="Eraasoft Wallet";
    private List<Account> accounts=new ArrayList<>();

    public String getWalletName() {
        return walletName;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

}