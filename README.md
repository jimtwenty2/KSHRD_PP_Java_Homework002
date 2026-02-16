# Homework 02 - BANK MANAGEMENT SYSTEM - KSHRD Center

## I. Overview : 
Create a comprehensive BANK MANAGEMENT SYSTEM application using Java that demonstrates object-oriented programming principles through interfaces, inheritance, and polymorphism. The system will manage both Checking and Saving accounts with full transaction capabilities.

## II. System Architecture
The `Classes.Account` class should define the following methods:

- `deposit(double amount)`
- `withdraw(double amount)`
- `transfer(double amount, Classes.Account targetAccount)`
- `displayAccountInfo()`

`Checking Classes.Account
(accountNumber, userName, dateOfBirth, gender, phoneNumber, balance)`

`Saving Classes.Account
(accountNumber,userName, dateOfBirth, gender, phoneNumber, balance, rate=0.05)`

## III. Required Features
1. **Create Account** - Allow users to create both Saving and Checking accounts with complete user information.
2. **Deposit Money** - Enable deposits into existing accounts with validation.
3. **Withdraw Money** - Allow withdrawals with sufficient balance checking.
4. **Transfer Money** - Transfer amounts between accounts (both Checking and Saving).
5. **Display Account Information** - Show complete account details including balance and (for Saving accounts) interest rate
6. **Delete Account** - Remove accounts from the system with confirmation.
