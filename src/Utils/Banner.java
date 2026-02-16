package Utils;
public class Banner {
    public static void showBannerOne(String text){
        int width = 70;
        int padding = (width - text.length()) / 2;
        System.out.println(Color.BLUE + "-".repeat(width));
        System.out.print("|");
        System.out.print(" ".repeat(padding-2));
        System.out.print(text);
        System.out.print(" ".repeat(width - padding - text.length()));
        System.out.println("|");
        System.out.println("-".repeat(width) + Color.RESET);
    }
}
