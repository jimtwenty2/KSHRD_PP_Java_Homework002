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
        System.out.println("=".repeat(70));
        System.out.println(Color.GREEN + "[" + "\u2713" + "] Success : " + successMessage + Color.RESET);
        System.out.println("=".repeat(70));
    }
    public static void showByeBye(){
        System.out.println("=".repeat(70));
        System.out.println(
                Color.GREEN + "[" + "\u2713" + "] (^_^)/ : Bye Bye " + Color.RESET +
                        Color.RED + "E" + Color.YELLOW + "x" + Color.GREEN + "i" + Color.BLUE + "t" + Color.PURPLE + "i" + Color.RED + "n" + Color.YELLOW + "g " +
                        Color.GREEN + "T" + Color.BLUE + "h" + Color.PURPLE + "e " +
                        Color.RED + "P " + Color.YELLOW + "r " + Color.GREEN + "O " + Color.BLUE + "g " + Color.PURPLE + "r " + Color.RED + "a " + Color.YELLOW + "M" + Color.RESET +
                        ", Thank YOU :))"
        );
        System.out.println("=".repeat(70));
    }
}
