import Classes.CheckingAccount;
import Classes.Message;
import Classes.SavingAccount;
import Enums.AccountType;
import Enums.Gender;
import Enums.Month;
import Interfaces.IAccount;
import Utils.Banner;
import Utils.Color;
import Utils.Menu;
import Utils.Regex;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Matcher;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static LocalDate today = LocalDate.now();
    public static IAccount savingAccount;
    public static IAccount checkingAccount;
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
                case 8 -> {
                    Message.showSuccess("Exiting The P r O g r a M, Thank YOU :)");
                    System.exit(0);
                }
                case 6 -> displayAccountInfo();
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
                username = input;
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
            if (today.getYear() - year < 16) {
                Message.showError("Invalid Age. Must be at least 16 years old.");
                continue;
            }
            if(year > today.getYear()) {
                Message.showError("Invalid Year, Year cannot be in the future.");
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
            System.out.print("Please Enter your phone number (0222233444): ");
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
        if (year % 4 != 0) {
            return false;
        } else if (year % 100 != 0) {
            return true;
        } else if (year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void displayAccountInfo(){
        if(savingAccount == null && checkingAccount == null){
            Message.showError("You do not have any account, No information will be displayed.");
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
}