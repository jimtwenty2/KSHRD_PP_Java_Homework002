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
            case "genderMenu" ->{
                for(String menu : arrayGenderMenu){
                    count += 1;
                    System.out.println(count + ". " + menu);
                }
            }
            default -> Message.showError("Invalid menu");
        }
    }
}
