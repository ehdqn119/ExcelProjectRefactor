package org.example.validation;

import java.util.regex.Pattern;

public class RegexUtil {

    public static void checkIp(String  str) {
        String pattern = "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}";
        boolean result = Pattern.matches(pattern, str);
        if(result) {
            System.out.println("ip 타입 체크 완료");
        }
        else {
            throw new IllegalArgumentException("잘못된 Ip 타입입니다.");
        }
    }

    public static void checkPortNumber(String  str) {
        String pattern = "^([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$";
        boolean result = Pattern.matches(pattern, str);
        if(result) {
            System.out.println("PortNumber 타입 체크 완료");
        }
        else {
            throw new IllegalArgumentException("잘못된  타입입니다.");
        }
    }

}
