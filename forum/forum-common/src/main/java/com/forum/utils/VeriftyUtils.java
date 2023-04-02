package com.forum.utils;

import com.forum.enums.VerifyRegexEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VeriftyUtils {
    public static Boolean verfity(String regs, String value) {
        if (StringTools.isEmpty(value)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regs);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static Boolean verfity(VerifyRegexEnum regs, String value) {
        return verfity(regs.getRegex(), value);
    }
}
