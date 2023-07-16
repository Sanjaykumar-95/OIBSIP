import java.util.*;

class Transaction {
    private String transactionType;
    private double amount;
    private String date;

    public Transaction(String transactionType, double amount, String date) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String toString() {
        return "[" + date + "] " + transactionType + ": $" + amount;
    }
}

class User {
    private String userId;
    private String userPin;
    private double accountBalance;
    private List<Transaction> transactionHistory;

    public User(String userId, String userPin, double accountBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.accountBalance = accountBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPin() {
        return userPin;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }
}

class ATMService {
    private User currentUser;

    public ATMService(User user) {
        this.currentUser = user;
    }

    public void displayMainMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n------ Main Menu ------");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    displayTransactionHistory();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    System.out.println("Exiting the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 5);
    }

    public void displayTransactionHistory() {
        List<Transaction> history = currentUser.getTransactionHistory();
        System.out.println("\n----- Transaction History -----");
        if (history.isEmpty()) {
            System.out.println("No transactions found.");
        } 
        else {
            for (Transaction transaction : history) {
                System.out.println(transaction);
            }
        }
    }

    public void withdraw() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the amount to withdraw: $");
        double amount = sc.nextDouble();
        if (amount <= 0) {
            System.out.println("Invalid amount. Withdrawal failed.");
        } 
        else if (amount > currentUser.getAccountBalance()) {
            System.out.println("Insufficient balance. Withdrawal failed.");
        } 
        else {
            currentUser.setAccountBalance(currentUser.getAccountBalance() - amount);
            Transaction transaction = new Transaction("Withdraw", amount, getCurrentDate());
            currentUser.addTransaction(transaction);
            System.out.println("Withdrawal successful. Current balance: $" + currentUser.getAccountBalance());
        }
    }

    public void deposit() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the amount to deposit: $");
        double amount = sc.nextDouble();
        if (amount <= 0) {
            System.out.println("Invalid amount. Deposit failed.");
        } 
        else {
            currentUser.setAccountBalance(currentUser.getAccountBalance() + amount);
            Transaction transaction = new Transaction("Deposit", amount, getCurrentDate());
            currentUser.addTransaction(transaction);
            System.out.println("Deposit successful. Current balance: $" + currentUser.getAccountBalance());
        }
    }

    public void transfer() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the recipient's User ID: ");
        String recipientId = sc.next();
        User recipient = findUserById(recipientId);
        if (recipient == null) {
            System.out.println("Recipient User ID not found. Transfer failed.");
            return;
        }
        System.out.print("Enter the amount to transfer: $");
        double amount = sc.nextDouble();
        if (amount <= 0) {
            System.out.println("Invalid amount. Transfer failed.");
        } 
        else if (amount > currentUser.getAccountBalance()) {
            System.out.println("Insufficient balance. Transfer failed.");
        } 
        else {
            currentUser.setAccountBalance(currentUser.getAccountBalance() - amount);
            recipient.setAccountBalance(recipient.getAccountBalance() + amount);

            Transaction sendTransaction = new Transaction("Transfer to " + recipientId, amount, getCurrentDate());
            Transaction receiveTransaction = new Transaction("Received from " + currentUser.getUserId(), amount, getCurrentDate());

            currentUser.addTransaction(sendTransaction);
            recipient.addTransaction(receiveTransaction);

            System.out.println("Transfer successful. Current balance: $" + currentUser.getAccountBalance());
        }
    }

    private User findUserById(String userId) {
        return currentUser.getUserId().equals(userId) ? currentUser : null;
    }

    private String getCurrentDate() {
        return "CurrentDate";
    }
}

class ATM {
    public static void main(String[] args) {
        User user = new User("user123", "1234", 1000);

        Scanner sc = new Scanner(System.in);
        System.out.println("----- Welcome to the ATM -----");
        System.out.print("Enter your User ID: ");
        String userId = sc.next();
        System.out.print("Enter your User PIN: ");
        String userPin = sc.next();

        if (userId.equals(user.getUserId()) && userPin.equals(user.getUserPin())) {
            ATMService atmService = new ATMService(user);
            atmService.displayMainMenu();
        } 
        else {
            System.out.println("Invalid User ID or PIN. Exiting the ATM.");
        }

        sc.close();
    }
}
