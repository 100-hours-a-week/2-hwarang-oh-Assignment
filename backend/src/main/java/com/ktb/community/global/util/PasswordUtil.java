package com.ktb.community.global.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * IMP : PasswordUtil은 BCryptPasswordEncoder를 사용하여, 암호화하고, 비밀번호가 일치하는지 확인합니다.
 */
public class PasswordUtil {

    private PasswordUtil() {
        // IMP : Should not be instantiated
    }

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encode(String password) {
        return encoder.encode(password);
    }

    public static boolean match(String password, String encodedPassword) {
        return encoder.matches(password, encodedPassword);
    }
}
