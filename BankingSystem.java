import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private List<String> transactionHistory;

    public BankAccount(String accountNumber, String accountHolderName) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposit: +" + amount);
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add("Withdrawal: -" + amount);
            return true;
        }
        return false;
    }

    public void transfer(BankAccount receiver, double amount) {
        if (withdraw(amount)) {
            receiver.deposit(amount);
            transactionHistory.add("Transfer to " + receiver.getAccountNumber() + ": -" + amount);
        }
    }
}

class Bank {
    private Map<String, BankAccount> accounts;

    public Bank() {
        this.accounts = new HashMap<>();
    }

    public void addAccount(BankAccount account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public void displayTransactionHistory(String accountNumber) {
        BankAccount account = getAccount(accountNumber);
        if (account != null) {
            System.out.println("Transaction History for Account: " + accountNumber);
            for (String transaction : account.getTransactionHistory()) {
                System.out.println(transaction);
            }
        } else {
            System.out.println("Account not found!");
        }
    }
}

public class BankingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();

        // Sample data
        BankAccount account1 = new BankAccount("123456", "John Doe");
        bank.addAccount(account1);

        BankAccount account2 = new BankAccount("789012", "Jane Smith");
        bank.addAccount(account2);

        while (true) {
            System.out.println("\nWelcome to the Online Banking System");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Transaction History");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    String depositAccountNumber = scanner.nextLine();
                    BankAccount depositAccount = bank.getAccount(depositAccountNumber);
                    if (depositAccount != null) {
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        depositAccount.deposit(depositAmount);
                        System.out.println("Deposit successful!");
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;
                case 2:
                    System.out.print("Enter account number: ");
                    String withdrawAccountNumber = scanner.nextLine();
                    BankAccount withdrawAccount = bank.getAccount(withdrawAccountNumber);
                    if (withdrawAccount != null) {
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawalAmount = scanner.nextDouble();
                        if (withdrawAccount.withdraw(withdrawalAmount)) {
                            System.out.println("Withdrawal successful!");
                        } else {
                            System.out.println("Insufficient balance!");
                        }
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;
                case 3:
                    System.out.print("Enter sender account number: ");
                    String senderAccountNumber = scanner.nextLine();
                    BankAccount senderAccount = bank.getAccount(senderAccountNumber);
                    if (senderAccount != null) {
                        System.out.print("Enter receiver account number: ");
                        String receiverAccountNumber = scanner.nextLine();
                        BankAccount receiverAccount = bank.getAccount(receiverAccountNumber);
                        if (receiverAccount != null) {
                            System.out.print("Enter transfer amount: ");
                            double transferAmount = scanner.nextDouble();
                            senderAccount.transfer(receiverAccount, transferAmount);
                            System.out.println("Transfer successful!");
                        } else {
                            System.out.println("Receiver account not found!");
                        }
                    } else {
                        System.out.println("Sender account not found!");
                    }
                    break;
                case 4:
                    System.out.print("Enter account number: ");
                    String historyAccountNumber = scanner.nextLine();
                    bank.displayTransactionHistory(historyAccountNumber);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
