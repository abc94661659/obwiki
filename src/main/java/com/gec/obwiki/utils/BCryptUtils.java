package com.gec.obwiki.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptUtils {

    // 明确指定强度为 12（与数据库中的密码一致）
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public static String encode(String password) {
        return encoder.encode(password);
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}