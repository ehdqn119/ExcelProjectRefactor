package org.example.validation;

import java.util.Objects;
import java.util.regex.Pattern;


public enum RegexPattern {

    IP_PATTERN(Pattern.compile("^([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$"), "Ip 번호 타입이 아닙니다. 다시 입력해주세요"),
    PORT_NUMBER_PATTERN(Pattern.compile("^([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$"), "Port 번호 타입이 아닙니다. 다시 입력해주세요");
    Pattern pattern;
    String errorMsg;
    RegexPattern(Pattern pattern, String errorMsg){
        this.pattern = pattern;
        this.errorMsg = errorMsg;
    }
    public void verify(String str) {
        if (pattern.pattern().matches(str)) {
            System.out.println("정규식을 통과하였습니다.");
        } else {
            throw new IllegalStateException(errorMsg);
        }
    }
}
