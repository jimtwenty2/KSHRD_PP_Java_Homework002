package Utils;

import Classes.Message;

public class Menu {
    public static void menuList(String optionList){
        String[] arrayMainMenu = {
                "Create Account","Deposit Money",
                "Withdraw Money","Transfer Money"
                ,"View Transaction History","Display Account Information",
                "Delete Account","Exit"};
        String[] arrayCreateAccountMenu = {"Create Checking Account","Create Saving Account", "Back"};
        String[] arrayGenderMenu = {"Male","Female"};
        String[] depositAccounts = {"To Checking Account","To Saving Account","Back"};
        String[] withdrawAccounts = {"From Checking Account","From Saving Account","Back"};
        String[] deleteAccounts = {"Delete Checking Account","Delete Saving Account","Back"};
        String[] transfersAccounts = {"Checking Account -> Saving Account","Saving Account -> Checking Account","Back"};
        String[] transactions = {"Transaction History of Checking Account","Transaction History of Saving Account","Back"};
        int count = 0;
        switch (optionList){
            case "mainMenu" -> {
                for(String menu : arrayMainMenu){
                    count += 1;
                    System.out.println(count + ". " + menu);
                }
            }
            case "creatingAccountMenu" -> {
                for(String menu : arrayCreateAccountMenu){
                    count += 1;
                    System.out.println(count + ". " + menu);
                }
            }
            case "genderMenu" -> {
                for(String menu : arrayGenderMenu){
                    count += 1;
                    System.out.println(count + ". " + menu);
                }
            }
            case "depositToAccount" -> {
                for(String menu : depositAccounts){
                    count += 1;
                    System.out.println(count + ". " + menu);
                }
            }
            case "withdrawToAccount" -> {
                for(String menu : withdrawAccounts){
                    count += 1;
                    System.out.println(count + ". " + menu);
                }
            }
            case "deleteAccount" -> {
                for(String menu : deleteAccounts){
                    count += 1;
                    System.out.println(count + ". " + menu);
                }
            }
            case "transferProcess" -> {
                for(String menu : transfersAccounts){
                    count += 1;
                    System.out.println(count + ". " + menu);
                }
            }
            case "transactions" -> {
                for(String menu : transactions){
                    count += 1;
                    System.out.println(count + ". " + menu);
                }
            }
            default -> Message.showError("Invalid Menu.");
        }
    }
}
