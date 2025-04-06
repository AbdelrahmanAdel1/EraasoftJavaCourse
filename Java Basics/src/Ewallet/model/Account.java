package Ewallet.model;

import java.util.ArrayList;

public class Account {
    private String userName;
    private String password;
    private boolean active;
    private double balance;
    private ArrayList<String> accountLog= new ArrayList<String>();


    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.active = true;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<String> getAccountLog() {
        return accountLog;
    }

    public void setAccountLog(ArrayList<String> accountLog) {
        this.accountLog = accountLog;
    }
}