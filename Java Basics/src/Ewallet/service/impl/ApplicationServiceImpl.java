package Ewallet.service.impl;

import Ewallet.model.Account;
import Ewallet.model.WalletSystem;
import Ewallet.service.AccountService;
import Ewallet.service.ApplicationService;

import java.util.Scanner;

public class ApplicationServiceImpl implements ApplicationService {
    private Scanner scanner = new Scanner(System.in);
    private WalletSystem walletSystem = new WalletSystem();
    private AccountService accountService = new AccountServiceImpl(walletSystem);
    private DataValidationImpl dataValidation= new DataValidationImpl();

    public ApplicationServiceImpl(WalletSystem walletSystem) {
        this.walletSystem = walletSystem;
        this.accountService = new AccountServiceImpl(walletSystem);
    }

    @Override
    public void start() {
        System.out.println("------Welcome to the Ewallet Application------");
        int failCount = 0;

        while (true) {
            if (failCount == 3) {
                System.out.println("You consumed the maximum number of trails please try again later");
                break;
            }
            System.out.println("Please Input your choice");
            System.out.println("1 Login");
            System.out.println("2 Create Account");
            System.out.println("3 Exit");
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                failCount++;
                continue;
            }

            boolean exit = false;

            switch (choice) {
                case 1 -> login();
                case 2 -> createAccount();
                case 3 -> {
                    System.out.println("Have a nice day :D");
                    exit = true;
                }
                default -> {
                    failCount++;
                    if (failCount != 3) {
                        System.out.println("Invalid Choice. Please try again.");
                    }
                }
            }

            if (exit) {
                break;
            }
        }

    }

    private void createAccount() {
        Account account = extractAccount();
        if(account==null)
            return;
        boolean creationStatus = accountService.createAccount(account);

        if (creationStatus) {
            System.out.println("Account Created Successful");
        }
        else {
            System.out.println("Username Already Exist");
        }
        }

    private void login () {
        Account account = extractAccount();
        if(account==null)
            return;
        account = accountService.findAccount(account);

        if(account !=null)
        {
            mainPage(account);
        }
        else {
            System.out.println("Invalid credentials");
        }
        }
    private Account extractAccount() {
        System.out.println("Please Enter Username: ");
        String userName = scanner.nextLine();
        if(!dataValidation.validateUserName(userName))
        {
            System.out.println("Invalid username, must be more than 2 letters and the first letter must be capital");
            return null;
        }

        System.out.println("Please Enter Password: ");
        String password = scanner.nextLine();
        if(!dataValidation.validatePassword(password))
        {
            System.out.println("Invalid password, must be more than 5 letters and contain 1 (uppercase,lowercase,digit,specialCharacter)");
            return null;
        }
        return new Account(userName, password);
    }
    private void mainPage(Account account) {
        System.out.println("------Hello "+account.getUserName()+" welcome to your wallet------");

        int failCount = 0;
        boolean exit = false;

        while (true) {
            if (failCount == 3) {
                System.out.println("You consumed the maximum number of trails please try again later");
                break;
            }
            System.out.println("a. Deposite");
            System.out.println("b. Withdraw");
            System.out.println("c. Transfer");
            System.out.println("d. Show Profile");
            System.out.println("e. Show Transactions");
            System.out.println("f. Exit");

            char choice = ' ';
            choice = scanner.next().charAt(0);
            scanner.nextLine();
            if (!Character.isLetter(choice)) {
                System.out.println("Please enter a valid letter.");
                failCount++;
                continue;
            }
            switch (choice) {
                case 'a' -> deposit(account);
                case 'b' -> withdraw(account);
                case 'c' -> transfer(account);
                case 'd' -> showProfile(account);
                case 'e' -> showTransactions(account);
                case 'f' -> {
                    System.out.println("Have a nice day :D");
                    exit = true;
                }
                default -> {
                    failCount++;
                    if (failCount != 3) {
                        System.out.println("Invalid Choice. Please try again.");
                    }
                }
            }

            if (exit) {
                break;
            }
        }

    }

    private void showTransactions(Account account) {
        accountService.showTransactions(account);
    }

    private void showProfile(Account account) {
        accountService.showProfile(account);
    }

    private void transfer(Account account) {
        System.out.println("Please enter the username of the account you want to transfer to");
        String receiverName= scanner.nextLine();
        if(!dataValidation.validateUserName(receiverName))
        {
            System.out.println("Invalid username, must be more than 2 letters and the first letter must be capital");
            return;
        }
        else {
            System.out.println("Please enter the amount you want to transfer");
            double transferAmount= scanner.nextDouble();
            scanner.nextLine();
            boolean transferStatus= accountService.transfer(account,receiverName,transferAmount);

            if(transferStatus)
            {
                System.out.println(transferAmount+" was transferred from "+ account.getUserName()+" to "+receiverName);
            }
            else{
                System.out.println("Transfer failed, please try again later");
            }
        }
    }

    private void withdraw(Account account) {
        System.out.println("Please enter the amount you want to withdraw");
        int withdrawAmount= scanner.nextInt();
        scanner.nextLine();
        boolean withDrawStatus=accountService.withdraw(account,withdrawAmount);

        if(withDrawStatus)
        {
            System.out.println("Withdraw successful, please receive the cash");
        }
        else {
            System.out.println("Please Enter a valid amount");
        }
    }

    private void deposit(Account account) {
        System.out.println("Please enter the amount you want to Deposit");
        int depositAmount= scanner.nextInt();
        scanner.nextLine();
        boolean depositStatus=accountService.deposit(account,depositAmount);

        if(depositStatus)
        {
            System.out.println("Deposit successful, please receive the receipt");
        }
        else {
            System.out.println("Deposit failed");
        }
    }
}
