package com.gym.utils;

import java.security.SecureRandom;
import java.util.Set;


public class Utils {
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWYXZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-=+?<>";

    public static String generatePassword() {
        String allowedCharacters = getAllowedSymbols();
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            char randomChar = allowedCharacters.charAt(randomIndex);
            password.append(randomChar);
        }
        return password.toString();
    }

    public static String generateUsername(String firstName, String secondName) {
        return firstName + "." + secondName;
    }

    public static Integer getLastMapObjectId(Set<Integer> keySet) {
        return keySet
                .stream()
                .max(Integer::compareTo)
                .orElse(0);
    }

    private static String getAllowedSymbols() {
        return UPPERCASE_LETTERS +
                LOWERCASE_LETTERS +
                DIGITS +
                SYMBOLS;
    }


}
