package Interfaces;
import Enums.Gender;
public interface IAccount {
    boolean deposit(double amount);
    boolean withdraw(double amount);
    boolean transfer(double amount,IAccount targetAccount);
    void displayAccountInfo();
    double getBalance();
    void setUserName(String userName);
    void setDateOfBirth(String dateOfBirth);
    void setGender(Gender gender);
    void setPhoneNumber(String phoneNumber);
}
