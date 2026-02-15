package Classes;
import Enums.Gender;
import Interfaces.IAccount;
public class Account implements IAccount {
    private String accountNumber;
    private String userName;
    private String dateOfBirth;
    private Gender gender;
    private String phoneNumber;
    private double balance;
    Account(String accountNumber){
        this.accountNumber = accountNumber;
        balance = 0;
    }
    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean deposit(double amount){
        if(amount <=0 ) return false;
        balance += amount; // success, update balance
        return true;
    }
    @Override
    public boolean withdraw(double amount) {
        if(amount <= 0) return false;
        else if(amount > balance) return false;
        balance -= amount; // success, update balance
        return true;
    }
    @Override
    public boolean transfer(double amount, IAccount targetAccount) {
        if(amount <= 0) return false;
        else if(amount > balance) return false;
        balance -= amount;
        targetAccount.deposit(amount);
        return true;
    }
    @Override
    public void displayAccountInfo() {
        System.out.println("Account Type : " + this.getClass().getSimpleName());
        System.out.println("Account Number : " + accountNumber);
        System.out.println("User Name : " + userName);
        System.out.println("Date of Birth : " + dateOfBirth);
        System.out.println("Gender : " + gender);
        System.out.println("Phone Number : " + phoneNumber);
        System.out.println("Balance : $" + balance);
        System.out.println("=".repeat(60));
    }
}
