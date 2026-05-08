package com.nosql_tree.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * HashPassword.java
 * <p>
 * Description: Auxiliary class to encrypt the Passwords, use basic configuration, only for example
 *
 * @author aleja
 * @since 07/05/2026
 */

@Component
public class HashPasswordUtil {
    private HashPasswordUtil(){}

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encryptPassword(String password){
        return encoder.encode(password);
    }

    public static boolean verify(String rawPassword, String expectedPassword){
        return encoder.matches(rawPassword, expectedPassword);
    }

}
