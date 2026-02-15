package Classes;

import Utils.Color;

public class Message {
    public static void showError(String errorMessage) {
        System.out.println(Color.RED + "[!] ERROR : " + errorMessage + Color.RESET);
    }
    public static void showWarning(String warningMessage) {
        System.out.println(Color.YELLOW + "[#] WARNING : " + warningMessage + Color.RESET);
    }
    public static void showSuccess(String successMessage){
        System.out.println("\n" + Color.GREEN + "[" + "\u2713" + "] Success : " + successMessage + Color.RESET + "\n");
    }
}
