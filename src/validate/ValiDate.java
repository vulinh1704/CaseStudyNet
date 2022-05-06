package validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValiDate {
    public static final String USER_NAME_SEVER = "(((\\w+\\d+)|(\\d+\\w+))@(\\w+)\\.com)";
    public static final String PASS_WORD_SEVER = "((\\w+\\d+)|(\\d+\\w+))";
    public static final String USER_NAME_PLAY = "(\\w{2,6}\\d{2,4})";
    public static final String PASS_WORD_PLAY = "(\\d{1,5})";

    public static boolean getValiDateUsrSv(String userName) {
        boolean checkValidate;
        Pattern pattern = Pattern.compile(USER_NAME_SEVER);
        Matcher matcher = pattern.matcher(userName);
        checkValidate = matcher.matches();
        return checkValidate;
    }

    public static boolean getValiDatePswSv(String passWord) {
        boolean checkValidate;
        Pattern pattern = Pattern.compile(PASS_WORD_SEVER);
        Matcher matcher = pattern.matcher(passWord);
        checkValidate = matcher.matches();
        return checkValidate;
    }

    public static boolean getValiDateUsrPlay(String userName) {
        boolean checkValidate;
        Pattern pattern = Pattern.compile(USER_NAME_PLAY);
        Matcher matcher = pattern.matcher(userName);
        checkValidate = matcher.matches();
        return checkValidate;
    }

    public static boolean getValiDatePswPlay(String passWord) {
        boolean checkValidate;
        Pattern pattern = Pattern.compile(PASS_WORD_PLAY);
        Matcher matcher = pattern.matcher(passWord);
        checkValidate = matcher.matches();
        return checkValidate;
    }
}
