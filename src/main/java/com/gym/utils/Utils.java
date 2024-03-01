package com.gym.utils;

import static java.util.stream.IntStream.range;

import java.security.SecureRandom;


public class Utils {
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWYXZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-=+?<>";

    public static String generatePassword() {
        String allowedCharacters = getAllowedSymbols();
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        range(0, 10)
                .forEach(i -> {
                    int randomIndex = random.nextInt(allowedCharacters.length());
                    char randomChar = allowedCharacters.charAt(randomIndex);
                    password.append(randomChar);
                });
        return password.toString();
    }

    public static String generateUsername(String firstName, String secondName) {
        return firstName + "." + secondName;
    }

    private static String getAllowedSymbols() {
        return UPPERCASE_LETTERS +
                LOWERCASE_LETTERS +
                DIGITS +
                SYMBOLS;
    }
}
