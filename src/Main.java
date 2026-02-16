import Classes.*;
import Enums.AccountType;
import Enums.Gender;
import Enums.Month;
import Enums.TransactionType;
import Interfaces.IAccount;
import Utils.Banner;
import Utils.Color;
import Utils.Menu;
import Utils.Regex;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static LocalDateTime now = LocalDateTime.now();
    public static String pattern = "dd MMMM yyyy HH:mm:ss";
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    public static String formattedDateTime = now.format(formatter);
    public static IAccount savingAccount;
    public static IAccount checkingAccount;
    public static TransactionHistory[] transactionCheckingHistories = new TransactionHistory[100];
    public static TransactionHistory[] transactionSavingHistories = new TransactionHistory[100];
    public static TransactionHistory transactionHistory;
    public static void main(String[] args) {
        processMainMenu();
    }
    public static void processMainMenu() {
        while (true) {
            Banner.showBannerOne("Online Banking System");
            Menu.menuList("mainMenu");
            int option = chooseOption();
            switch (option) {
                case 1 -> createAccountProcess();
                case 2 -> depositMoneyProcess();
                case 3 -> withdrawMoneyProcess();
                case 4 -> transferMoneyProcess();
                case 5 -> processTransactionHistory();
                case 6 -> displayAccountInfo();
                case 7 -> processDeleteAccount();
                case 8 -> {
                    Message.showByeBye();
                    System.exit(0);
                }
                default -> Message.showError("Invalid Input.");
            }
        }
    }

    public static int chooseOption() {
        System.out.print("\n" + Color.BLUE + "--> Please Enter Your Option : " + Color.RESET);
        String input = scanner.nextLine().trim();
        System.out.println();
        if (input.isEmpty() || !Regex.patternInputOptionNumberOnly.matcher(input).matches()) {
            return -1;
        }
        return Integer.parseInt(input);
    }
    public static void pressEnterToContinue() {
        do {
            System.out.println();
            System.out.print(Color.PURPLE + "[+] Press Enter to continue..." + Color.RESET);
            String input = scanner.nextLine();
            System.out.println();
            if (input.isEmpty()) {
                break;
            }
        }while (true);
    }
    public static void createAccountProcess(){
        Banner.showBannerOne("Creating Bank Account");
        Menu.menuList("creatingAccountMenu");
        while (true){
            int optionForCreatingAccount = chooseOption();
            switch (optionForCreatingAccount){
                case 1 -> createBankAccount(AccountType.CHECKING_ACCOUNT);
                case 2 -> createBankAccount(AccountType.SAVING_ACCOUNT);
                case 3 -> processMainMenu();
                default -> Message.showError("Invalid option.");
            }
        }
    }
    public static void createBankAccount(AccountType type) {
        String username, dateOfBirth, phoneNumber;
        Gender gender;
        if(type == AccountType.CHECKING_ACCOUNT){
            if (checkingAccount != null) {
                Message.showError("Checking account already exists. You cannot create another.");
                pressEnterToContinue();
                createAccountProcess();
                return;
            }
            Banner.showBannerOne("Input Information For Checking Account");
        }
        if(type == AccountType.SAVING_ACCOUNT){
            if (savingAccount != null) {
                Message.showError("Saving account already exists. You cannot create another.");
                pressEnterToContinue();
                createAccountProcess();
                return;
            }
            Banner.showBannerOne("Input Information For Saving Account");
        }
        while (true) {
            System.out.print("Please Enter your username: ");
            String input = scanner.nextLine();
            if (Regex.patternInputSpaceAndLetterOnly.matcher(input).matches()) {
                username = input.toUpperCase();
                break;
            }
            Message.showError("Username allows only Letters and Spacing.");
        }
        while (true) {
            System.out.print("Please Enter Date of Birth (DD-MM-YYYY): ");
            String input = scanner.nextLine();
            Matcher matcher = Regex.patternBirthYear.matcher(input);
            if (!matcher.matches()) {
                Message.showError("Invalid format. Please use this format - DD-MM-YYYY.");
                continue;
            }
            int day = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));
            if (month < 1 || month > 12) {
                Message.showError("Month must be between 1 and 12.");
                continue;
            }
            int maxDays = switch (month) {
                case 1, 3, 5, 7, 8, 10, 12 -> 31;
                case 4, 6, 9, 11 -> 30;
                case 2 -> isLeapYear(year) ? 29 : 28;
                default -> 0;
            };
            String monthName = switch (month) {
                case 1 -> Month.JANUARY.name();
                case 2 -> Month.FEBRUARY.name();
                case 3 -> Month.MARCH.name();
                case 4 -> Month.APRIL.name();
                case 5 -> Month.MAY.name();
                case 6 -> Month.JUNE.name();
                case 7 -> Month.JULY.name();
                case 8 -> Month.AUGUST.name();
                case 9 -> Month.SEPTEMBER.name();
                case 10 -> Month.OCTOBER.name();
                case 11 -> Month.NOVEMBER.name();
                case 12 -> Month.DECEMBER.name();
                default -> null;
            };
            if (day < 1 || day > maxDays) {
                Message.showError("Invalid day for " + monthName + ", It's cannot be " + day + " days");
                continue;
            }
            if (now.getYear() - year < 16) {
                Message.showError("Invalid Age. Must be at least 16 years old.");
                continue;
            }
            if(year > now.getYear()) {
                Message.showError("Invalid Year, Year cannot be in the future.");
                continue;
            }
            if(year < 1900) {
                Message.showError("Sorry!, The longest verified human lifespan is 122 years and 164 days, achieved by Jeanne Calment of France.");
                continue;
            }
            dateOfBirth = String.format("%02d-%02d-%d", day, month, year);
            break;
        }
        Menu.menuList("genderMenu");
        while (true) {
            System.out.print("Please Enter your gender (1 for Male, 2 for Female): ");
            String input = scanner.nextLine();
            if (input.equals("1")) {
                gender = Gender.MALE;
                break;
            } else if (input.equals("2")) {
                gender = Gender.FEMALE;
                break;
            }
            Message.showError("Invalid Option. Please enter 1 or 2.");
        }
        while (true) {
            System.out.print("Please Enter your phone number (098345232): ");
            String input = scanner.nextLine();
            Matcher matcher = Regex.patternPhoneNumber.matcher(input);
            if (matcher.matches()) {
                int  firstPath = Integer.parseInt(matcher.group(1));
                int  secondPath = Integer.parseInt(matcher.group(2));
                int  thirdPath = Integer.parseInt(matcher.group(3));
                phoneNumber = "0" + firstPath + " " + secondPath + " " + thirdPath;
                break;
            }
            Message.showError("Invalid phone number. Must start with 0 and be 9-10 digits and spacing is not allowed.");
        }
        if(type == AccountType.CHECKING_ACCOUNT){
            checkingAccount = new CheckingAccount();
            checkingAccount.setUserName(username);
            checkingAccount.setDateOfBirth(dateOfBirth);
            checkingAccount.setGender(gender);
            checkingAccount.setPhoneNumber(phoneNumber);
            Message.showSuccess("Checking Account has been created successfully.");
            checkingAccount.displayAccountInfo();
        }else if (type == AccountType.SAVING_ACCOUNT){
            savingAccount = new SavingAccount();
            savingAccount.setUserName(username);
            savingAccount.setDateOfBirth(dateOfBirth);
            savingAccount.setGender(gender);
            savingAccount.setPhoneNumber(phoneNumber);
            Message.showSuccess("Saving Account has been created successfully.");
            savingAccount.displayAccountInfo();
        }
        pressEnterToContinue();
        createAccountProcess();
    }
    public static boolean isLeapYear(int year){
        if (year % 4 == 0 && year % 100 != 0) {
            return true;
        } else if (year % 400 == 0) {
            return true;
        }
        return false;
    }
    public static void displayAccountInfo(){
        if(savingAccount == null && checkingAccount == null){
            Message.showWarning("You do not have any account, No information will be displayed.");
            pressEnterToContinue();
            processMainMenu();
            return;
        }
        Banner.showBannerOne("Displaying Account's Information");
        if(checkingAccount != null){
            Banner.showBannerOne("Checking Account's Information");
            checkingAccount.displayAccountInfo();
        }
        if(savingAccount != null){
            Banner.showBannerOne("Saving Account's Information");
            savingAccount.displayAccountInfo();
        }
        pressEnterToContinue();
        processMainMenu();
    }
    public static void depositMoneyProcess(){
        Banner.showBannerOne("Deposit To Account");
        Menu.menuList("depositToAccount");
        while (true){
            int optionForDepositToAccount = chooseOption();
            switch (optionForDepositToAccount){
                case 1 -> transactionProcess(AccountType.CHECKING_ACCOUNT,TransactionType.DEPOSIT);
                case 2 -> transactionProcess(AccountType.SAVING_ACCOUNT,TransactionType.DEPOSIT);
                case 3 -> processMainMenu();
                default -> Message.showError("Invalid option.");
            }
        }
    }
    public static void withdrawMoneyProcess(){
        Banner.showBannerOne("Withdraw From Account");
        Menu.menuList("withdrawToAccount");
        while (true){
            int optionForDepositToAccount = chooseOption();
            switch (optionForDepositToAccount){
                case 1 -> transactionProcess(AccountType.CHECKING_ACCOUNT,TransactionType.WITHDRAW);
                case 2 -> transactionProcess(AccountType.SAVING_ACCOUNT,TransactionType.WITHDRAW);
                case 3 -> processMainMenu();
                default -> Message.showError("Invalid option.");
            }
        }
    }
    public static void transactionProcess(AccountType toAccount, TransactionType transactionType){

        if(transactionType == TransactionType.DEPOSIT){
            if(toAccount == AccountType.CHECKING_ACCOUNT){
                if(checkingAccount == null){
                    Message.showError("Checking account is not exists. You cannot Deposit.");
                    pressEnterToContinue();
                    depositMoneyProcess();
                }else{
                    do {
                        Banner.showBannerOne("Deposit To Checking Account");
                        System.out.print("Enter amount of money to deposit : $ ");
                        String input = scanner.nextLine();
                        if(!Regex.patternNumberOnlyForTransaction.matcher(input).matches()){
                            Message.showError("Amount of money must be number only.");
                        }else {
                            double amount = Double.parseDouble(input);
                            checkingAccount.deposit(amount);
                            System.out.print("Enter remark (optional) : ");
                            String remark = scanner.nextLine();
                            if(remark.isEmpty()) remark = null;
                            recordTransactionHistory(transactionType,AccountType.CHECKING_ACCOUNT,remark,amount);
                            System.out.println("Checking Account");
                            System.out.println("Deposited amount : \t\t\t$ " + input);
                            System.out.println("Total balance : \t\t\t$ " + checkingAccount.getBalance());
                            Message.showSuccess("Deposited to Checking Account successfully.");
                            pressEnterToContinue();
                            depositMoneyProcess();
                            return;
                        }
                    }while (true);
                }
            } else if (toAccount == AccountType.SAVING_ACCOUNT) {
                if(savingAccount == null){
                    Message.showError("Saving account is not exists. You cannot Deposit.");
                    pressEnterToContinue();
                    depositMoneyProcess();
                }else{
                    do {
                        Banner.showBannerOne("Deposit To Saving Account");
                        System.out.print("Enter amount of money to deposit : $ ");
                        String input = scanner.nextLine();
                        if(!Regex.patternNumberOnlyForTransaction.matcher(input).matches()){
                            Message.showError("Amount of money must be number only.");
                        }else {
                            double amount = Double.parseDouble(input);
                            boolean depositSuccess = savingAccount.deposit(amount);
                            if(depositSuccess){
                                System.out.print("Enter remark (optional) : ");
                                String remark = scanner.nextLine();
                                if(remark.isEmpty()) remark = null;
                                recordTransactionHistory(transactionType,AccountType.SAVING_ACCOUNT,remark,amount);
                                System.out.println("Saving Account");
                                System.out.println("Deposited amount : \t\t\t$ " + input);
                                System.out.println("Total balance : \t\t\t$ " + savingAccount.getBalance());
                                Message.showSuccess("Deposited to Saving Account successfully.");
                                pressEnterToContinue();
                                depositMoneyProcess();
                            }else{
                                System.out.println("=".repeat(70));
                                Message.showError("Deposit unsuccessfully.");
                                System.out.println("=".repeat(70));
                                pressEnterToContinue();
                                depositMoneyProcess();
                            }
                            return;
                        }
                    }while (true);
                }
            }
        }
        if(transactionType == TransactionType.WITHDRAW){
            if(toAccount == AccountType.CHECKING_ACCOUNT){
                if(checkingAccount == null){
                    Message.showError("Checking account is not exists. You cannot Withdraw.");
                    pressEnterToContinue();
                    withdrawMoneyProcess();
                }else{
                    do {
                        Banner.showBannerOne("Withdraw From Checking Account");
                        System.out.print("Enter amount of money to withdraw : $ ");
                        String input = scanner.nextLine();
                        if(!Regex.patternNumberOnlyForTransaction.matcher(input).matches()){
                            Message.showError("Amount of money must be number only.");
                        }else {
                            double amount = Double.parseDouble(input);
                            boolean withdrawSuccess = checkingAccount.withdraw(amount);
                            if(withdrawSuccess){
                                System.out.print("Enter remark (optional) : ");
                                String remark = scanner.nextLine();
                                if(remark.isEmpty()) remark = null;
                                recordTransactionHistory(transactionType,AccountType.CHECKING_ACCOUNT,remark,amount);
                                System.out.println("Checking Account");
                                System.out.println("Withdraw amount : \t\t\t$ " + input);
                                System.out.println("Total balance : \t\t\t$ " + checkingAccount.getBalance());
                                Message.showSuccess("Withdraw from Checking Account successfully.");
                                pressEnterToContinue();
                                withdrawMoneyProcess();
                            }else{
                                System.out.println("=".repeat(70));
                                Message.showWarning("Insufficient funds to withdraw! Total Balance = $" + checkingAccount.getBalance());
                                System.out.println("=".repeat(70));
                                pressEnterToContinue();
                                withdrawMoneyProcess();
                            }
                            return;
                        }
                    }while (true);
                }
            } else if (toAccount == AccountType.SAVING_ACCOUNT) {
                if(savingAccount == null){
                    Message.showError("Saving account is not exists. You cannot Withdraw.");
                    pressEnterToContinue();
                    withdrawMoneyProcess();
                }else{
                    do {
                        Banner.showBannerOne("Withdraw From Saving Account");
                        System.out.print("Enter amount of money to withdraw : $ ");
                        String input = scanner.nextLine();
                        if(!Regex.patternNumberOnlyForTransaction.matcher(input).matches()){
                            Message.showError("Amount of money must be number only.");
                        }else {
                            double amount = Double.parseDouble(input);
                            boolean withdrawSuccess = savingAccount.withdraw(amount);
                            if(withdrawSuccess){
                                System.out.print("Enter remark (optional) : ");
                                String remark = scanner.nextLine();
                                if(remark.isEmpty()) remark = null;
                                recordTransactionHistory(transactionType,AccountType.SAVING_ACCOUNT,remark,amount);
                                System.out.println("Saving Account");
                                System.out.println("Withdraw amount : \t\t\t$ " + input);
                                System.out.println("Total balance : \t\t\t$ " + savingAccount.getBalance());
                                Message.showSuccess("Withdraw from Saving Account successfully.");
                                pressEnterToContinue();
                                withdrawMoneyProcess();
                            }else{
                                System.out.println("=".repeat(70));
                                Message.showWarning("Insufficient funds to withdraw! Total Balance = $" + savingAccount.getBalance());
                                System.out.println("=".repeat(70));
                                pressEnterToContinue();
                                withdrawMoneyProcess();
                            }
                            return;
                        }
                    }while (true);
                }
            }
        }
    }
    public static void processDeleteAccount(){
        Banner.showBannerOne("Delete Account");
        Menu.menuList("deleteAccount");
        while (true){
            int optionForDeleteAccount = chooseOption();
            switch (optionForDeleteAccount){
                case 1 -> deleteAccount(AccountType.CHECKING_ACCOUNT);
                case 2 -> deleteAccount(AccountType.SAVING_ACCOUNT);
                case 3 -> processMainMenu();
                default -> Message.showError("Invalid option.");
            }
        }
    }
    public static void deleteAccount(AccountType typeToDelete){
            if(typeToDelete == AccountType.CHECKING_ACCOUNT) {
                if (checkingAccount == null) {
                    Message.showError("Checking account is not exists, You cannot Delete this account.");
                    pressEnterToContinue();
                    processDeleteAccount();
                } else {
                    double remainingBalance = checkingAccount.getBalance();
                    checkingAccount.displayAccountInfo();
                    if(remainingBalance > 0){
                        if(savingAccount == null){
                            Message.showError("Cannot delete this Account Because there is no other account to transfer the remaining balance to!.");
                            pressEnterToContinue();
                            processDeleteAccount();
                        }else{
                            do{
                                System.out.print(Color.PURPLE + "Since your balance = $" + checkingAccount.getBalance() + ", We will transferring the remaining balance to your other account.\nNoted : That all history of that account will also deleted.\nAre you sure to delete this account? (Yy/Nn) : " + Color.RESET);
                                String confirmation = scanner.nextLine().toUpperCase();
                                if(confirmation.equals("Y")){
                                    System.out.println(Color.YELLOW + "Transferring remaining balance $" + checkingAccount.getBalance() + " To account " + savingAccount.getUsername() + "(" + savingAccount.getAccountNumber() + ")" + Color.RESET);
                                    boolean transferSuccess = checkingAccount.transfer(remainingBalance,savingAccount);
                                    if(transferSuccess){
                                        recordTransactionTransferHistory(TransactionType.TRANSFER,AccountType.CHECKING_ACCOUNT,AccountType.SAVING_ACCOUNT,"Transfer when deleting account",remainingBalance);
                                        checkingAccount = null;
                                        Message.showSuccess("Deleted Checking Account Successfully!");
                                        pressEnterToContinue();
                                        processDeleteAccount();
                                        return;
                                    }else {
                                        Message.showError("Error transferring and deletion");
                                        return;
                                    }
                                }else if(confirmation.equals("N")){
                                    processDeleteAccount();
                                    return;
                                }else{
                                    Message.showError("Invalid Option.");
                                }
                            }while (true);
                        }
                    }else{
                        do{
                            System.out.print(Color.PURPLE + "Since your balance = $" + checkingAccount.getBalance() + ",We will directly delete it,\nNoted : That all history of that account will also deleted.\nAre you sure to delete this account? (Yy/Nn) : " + Color.RESET);
                            String confirmation = scanner.nextLine().toUpperCase();
                            if(confirmation.equals("Y")){
                                System.out.println(Color.YELLOW + "Deleting Checking Account ..." + Color.RESET);
                                checkingAccount = null;
                                Message.showSuccess("Deleted Checking Account Successfully!");
                                pressEnterToContinue();
                                processDeleteAccount();
                                return;
                            }else if(confirmation.equals("N")){
                                processDeleteAccount();
                                return;
                            }else{
                                Message.showError("Invalid Option.");
                            }
                        }while (true);
                    }
                }
            }else if(typeToDelete == AccountType.SAVING_ACCOUNT){
                if (savingAccount == null) {
                    Message.showError("Saving account is not exists. You cannot Delete this account.");
                    pressEnterToContinue();
                    processDeleteAccount();
                } else {
                    double remainingBalance = savingAccount.getBalance();
                    savingAccount.displayAccountInfo();
                    if(remainingBalance > 0){
                        if(checkingAccount == null){
                            Message.showError("Cannot delete this Account Because there is \nno other account to transfer the remaining balance to!.");
                            pressEnterToContinue();
                            processDeleteAccount();
                        }else{
                            do{
                                System.out.print(Color.PURPLE + "Since your balance = $" + savingAccount.getBalance() + ", We will transferring the remaining balance to your other account,\nNoted : That all history of that account will also deleted.\nAre you sure to delete this account? (Yy/Nn) : " + Color.RESET);
                                String confirmation = scanner.nextLine().toUpperCase();
                                if(confirmation.equals("Y")){
                                    System.out.println(Color.YELLOW + "Transferring remaining balance $" + savingAccount.getBalance() + " To account " + checkingAccount.getUsername() + "(" + checkingAccount.getAccountNumber() + ")" + Color.RESET);
                                    boolean transferSuccess = savingAccount.transfer(remainingBalance,checkingAccount);
                                    if(transferSuccess){
                                        recordTransactionTransferHistory(TransactionType.TRANSFER,AccountType.SAVING_ACCOUNT,AccountType.CHECKING_ACCOUNT,"Transfer when deleting account",remainingBalance);
                                        savingAccount = null;
                                        Message.showSuccess("Deleted Saving Account Successfully!");
                                        pressEnterToContinue();
                                        processDeleteAccount();
                                        return;
                                    }else {
                                        System.out.println("Error transferring");
                                    }
                                }else if(confirmation.equals("N")){
                                    processDeleteAccount();
                                    return;
                                }else{
                                    Message.showError("Invalid Option.");
                                }
                            }while (true);
                        }
                    }else{
                        do{
                            System.out.print(Color.PURPLE + "Since your balance = $" + savingAccount.getBalance() + ", We will directly delete it,\nNoted : That all history of that account will also deleted.\n Are you sure to delete this account? (Yy/Nn) : " + Color.RESET);
                            String confirmation = scanner.nextLine().toUpperCase();
                            if(confirmation.equals("Y")){
                                System.out.println(Color.YELLOW + "Deleting Saving Account ...");
                                savingAccount = null;
                                Message.showSuccess("Deleted Saving Account Successfully!");
                                pressEnterToContinue();
                                processDeleteAccount();
                                return;
                            }else if(confirmation.equals("N")){
                                processDeleteAccount();
                                return;
                            }else{
                                Message.showError("Invalid Option.");
                            }
                        }while (true);
                    }
                }
            }
    }
    public static void transferMoneyProcess(){
        Banner.showBannerOne("Transfer Account");
        Menu.menuList("transferProcess");
        while (true){
            int optionForTransfer = chooseOption();
            switch (optionForTransfer){
                case 1 -> transferMoneyToAccount(AccountType.SAVING_ACCOUNT);
                case 2 -> transferMoneyToAccount(AccountType.CHECKING_ACCOUNT);
                case 3 -> processMainMenu();
                default -> Message.showError("Invalid option.");
            }
        }
    }
    public static void transferMoneyToAccount(AccountType targetAccount) {
        if (targetAccount == AccountType.SAVING_ACCOUNT) {
            if (checkingAccount == null) {
                Message.showError("Cannot Transfer, Your source account (Checking Account) doesn't Exist.");
                pressEnterToContinue();
                transferMoneyProcess();
            } else {
                if (savingAccount == null) {
                    Message.showError("Cannot Transfer, Your target account (Saving Account) doesn't Exist.");
                    pressEnterToContinue();
                    transferMoneyProcess();
                } else {
                    processTransfer:
                    do {
                        Banner.showBannerOne("Transfer From Checking Account --> Saving Account");
                        System.out.print("Enter amount to Transfer from Checking to Saving Account : ");
                        String input = scanner.nextLine();
                        if (!Regex.patternNumberOnlyForTransaction.matcher(input).matches()) {
                            Message.showError("Amount of money must be number only.");
                        } else {
                            double amountToTransfer = Double.parseDouble(input);
                            boolean transferSuccess = checkingAccount.transfer(amountToTransfer, savingAccount);
                            if (!transferSuccess) {
                                Message.showWarning("Insufficient Amount in Account named " + checkingAccount.getUsername() + " to transfer!");
                                while (true) {
                                    System.out.print(Color.PURPLE + "Do you still want to continue ? (Yy/Nn) : " + Color.RESET);
                                    String conAsk = scanner.nextLine().toUpperCase();
                                    if (conAsk.equals("Y")) {
                                        continue processTransfer;
                                    } else if (conAsk.equals("N")) {
                                        transferMoneyProcess();
                                        return;
                                    } else {
                                        Message.showError("Invalid Input.");
                                    }
                                }
                            }
                            System.out.print("Enter remark (optional) : ");
                            String remark = scanner.nextLine();
                            if(remark.isEmpty()) remark = null;
                            recordTransactionTransferHistory(TransactionType.TRANSFER,AccountType.CHECKING_ACCOUNT,AccountType.SAVING_ACCOUNT,remark,amountToTransfer);
                            Message.showSuccess("Transferred Successfully!");
                            pressEnterToContinue();
                            transferMoneyProcess();
                            return;
                        }
                    } while (true);
                }
            }
        }
        else if (targetAccount == AccountType.CHECKING_ACCOUNT) {
            if (savingAccount == null) {
                Message.showError("Cannot Transfer, Your source account (Saving Account) doesn't Exist.");
                pressEnterToContinue();
                transferMoneyProcess();
            } else {
                if (checkingAccount == null) {
                    Message.showError("Cannot Transfer, Your target account (Checking Account) doesn't Exist.");
                    pressEnterToContinue();
                    transferMoneyProcess();
                } else {
                    processTransfer:
                    do {
                        Banner.showBannerOne("Transfer From Saving Account --> Checking Account");
                        System.out.print("Enter amount to Transfer from Saving to Checking Account : ");
                        String input = scanner.nextLine();
                        if (!Regex.patternNumberOnlyForTransaction.matcher(input).matches()) {
                            Message.showError("Amount of money must be number only.");
                        } else {
                            double amountToTransfer = Double.parseDouble(input);
                            boolean transferSuccess = savingAccount.transfer(amountToTransfer, checkingAccount);
                            if (!transferSuccess) {
                                Message.showWarning("Insufficient Amount in Account named " + savingAccount.getUsername() + " to transfer!");
                                while (true) {
                                    System.out.print(Color.PURPLE + "Do you still want to continue ? (Yy/Nn) : " + Color.RESET);
                                    String conAsk = scanner.nextLine().toUpperCase();
                                    if (conAsk.equals("Y")) {
                                        continue processTransfer;
                                    } else if (conAsk.equals("N")) {
                                        transferMoneyProcess();
                                        return;
                                    } else {
                                        Message.showError("Invalid Input.");
                                    }
                                }
                            }
                            System.out.print("Enter remark (optional) : ");
                            String remark = scanner.nextLine();
                            if(remark.isEmpty()) remark = null;
                            recordTransactionTransferHistory(TransactionType.TRANSFER,AccountType.SAVING_ACCOUNT,AccountType.CHECKING_ACCOUNT,remark,amountToTransfer);
                            Message.showSuccess("Transferred Successfully!");
                            pressEnterToContinue();
                            transferMoneyProcess();
                            return;
                        }
                    } while (true);
                }
            }
        }
    }
    public static void processTransactionHistory(){
        Banner.showBannerOne("Transaction History Account");
        Menu.menuList("transactions");
        while (true){
            int optionForDeleteAccount = chooseOption();
            switch (optionForDeleteAccount){
                case 1 -> transactionHistoryOf(AccountType.CHECKING_ACCOUNT);
                case 2 -> transactionHistoryOf(AccountType.SAVING_ACCOUNT);
                case 3 -> processMainMenu();
                default -> Message.showError("Invalid option.");
            }
        }
    }

    public static void transactionHistoryOf(AccountType accountToCheckHistory){
        if(accountToCheckHistory == AccountType.CHECKING_ACCOUNT) {
            if (checkingAccount == null) {
                Message.showError("Checking account is not exists. You cannot View History.");
                pressEnterToContinue();
                processTransactionHistory();
            }else{
                showHistory(accountToCheckHistory);
            }
        }else if(accountToCheckHistory == AccountType.SAVING_ACCOUNT){
            if (savingAccount == null) {
                Message.showError("Saving account is not exists. You cannot View History.");
                pressEnterToContinue();
                processTransactionHistory();
            }else{
                showHistory(accountToCheckHistory);
            }
        }
    }
    public static void recordTransactionHistory(TransactionType transactionType, AccountType accountType, String remark, double amount){
        now = LocalDateTime.now();
        formattedDateTime = now.format(formatter);
        if(accountType == AccountType.CHECKING_ACCOUNT){
            transactionHistory = new TransactionHistory(transactionType,formattedDateTime,AccountType.CHECKING_ACCOUNT,remark,amount);
            for(int i=0; i<transactionCheckingHistories.length;i++){
                if(transactionCheckingHistories[i] == null) {
                    transactionCheckingHistories[i] = transactionHistory;
                    break;
                }
            }
        }else if(accountType == AccountType.SAVING_ACCOUNT){
            transactionHistory = new TransactionHistory(transactionType,formattedDateTime,AccountType.SAVING_ACCOUNT,remark,amount);
            for(int i=0; i<transactionSavingHistories.length;i++){
                if(transactionSavingHistories[i] == null) {
                    transactionSavingHistories[i] = transactionHistory;
                    break;
                }
            }
        }
    }
    public static void recordTransactionTransferHistory(TransactionType transactionType, AccountType fromAccount, AccountType toAccount, String remark, double amount) {
        now = LocalDateTime.now();
        formattedDateTime = now.format(formatter);
        transactionHistory = new TransactionHistory(transactionType, fromAccount, toAccount, formattedDateTime, remark, amount);
        for (int i = 0; i < transactionCheckingHistories.length; i++) {
            if (transactionCheckingHistories[i] == null) {
                transactionCheckingHistories[i] = transactionHistory;
                break;
            }
        }
        for (int i = 0; i < transactionSavingHistories.length; i++) {
            if (transactionSavingHistories[i] == null) {
                transactionSavingHistories[i] = transactionHistory;
                break;
            }
        }
    }
    public static void showHistory(AccountType typeToShow) {
        TransactionHistory[] targetArray = (typeToShow == AccountType.CHECKING_ACCOUNT)
                ? transactionCheckingHistories
                : transactionSavingHistories;
        if (targetArray == null || targetArray[0] == null) {
            Message.showWarning("You don't have any Transaction to be shown yet.");
            pressEnterToContinue();
            return;
        }
        String accountLabel = (typeToShow == AccountType.CHECKING_ACCOUNT) ? "Checking" : "Saving";
        Banner.showBannerOne("Transaction History For " + accountLabel + " Account");
        for (int i = 0; i < targetArray.length; i++) {
            if (targetArray[i] == null) {
                break;
            }
            targetArray[i].displayHistory();
        }
        pressEnterToContinue();
        processTransactionHistory();
    }
}