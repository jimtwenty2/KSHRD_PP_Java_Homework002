package Classes;
import Enums.AccountType;
import Enums.TransactionType;

public class TransactionHistory {
    public static int countTransactionID = 100000;
    private AccountType accountType;
    private final String transactionID;
    private final TransactionType transactionType;
    private final String transactionDate;
    private final String remark;
    private final double amount;
    private AccountType fromAccountType;
    private AccountType toAccountType;
    public TransactionHistory(TransactionType transactionType, String transactionDate, AccountType accountType, String remark, double amount){
        countTransactionID += 1;
        transactionID = "" + countTransactionID;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.accountType = accountType;
        this.remark = remark;
        this.amount = amount;
    }
    public TransactionHistory(TransactionType transactionType,AccountType fromAccountType, AccountType toAccountType, String transactionDate, String remark, double amount){
        countTransactionID += 1;
        transactionID = "" + countTransactionID;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.fromAccountType = fromAccountType;
        this.toAccountType = toAccountType;
        this.remark = remark;
        this.amount = amount;
    }
    public void displayHistory(){
        System.out.println("=".repeat(70));
        System.out.println("Transaction ID : " + transactionID);
        System.out.println("Transaction Type : " + transactionType);
        if (fromAccountType != null) System.out.println("From account : " + fromAccountType);
        if (toAccountType != null) System.out.println("To account : " + toAccountType);
        System.out.println("Transaction Date : " + transactionDate);
        System.out.println("Amount : $" + amount);
        if(remark != null) System.out.println("Remark : " + remark);
        System.out.println("=".repeat(70));
    }
}
