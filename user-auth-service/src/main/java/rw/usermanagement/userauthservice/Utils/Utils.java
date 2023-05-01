package rw.usermanagement.userauthservice.Utils;

import java.util.Objects;
import java.util.Random;

public class Utils {

    public static String SECRET = "1765gnoDfGjjyytt+4";
    public static final long EXPIRATION_TIME = 1; // 60 mins
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/auth/register";
    public static final String PASSWORD_NOT_MATCH_MSG = "Password And Confirm-Password Does Not Match";

    public static boolean doesBothStringMatch(String firstText, String secondText){
        if (Objects.nonNull(firstText) && Objects.nonNull(secondText)) {
            return Objects.equals(firstText, secondText);
        }
        return false;
    }

    public static String GenerateOtp() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }


}
