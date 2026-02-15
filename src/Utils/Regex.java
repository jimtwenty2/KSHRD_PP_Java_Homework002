package Utils;

import java.util.regex.Pattern;

public class Regex {
    public static Pattern patternInputOptionNumberOnly = Pattern.compile("^[1-9]$");
    public static Pattern patternInputSpaceAndLetterOnly = Pattern.compile("^[A-Za-z][A-Za-z ]*$");
    public static Pattern patternBirthYear = Pattern.compile("^(\\d{1,2})-(\\d{1,2})-(\\d{3,4})$");
    public static Pattern patternPhoneNumber = Pattern.compile("^(0\\d{2})(\\d{3})(\\d{3,4})$");
}
