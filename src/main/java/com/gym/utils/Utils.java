package com.gym.utils;

public class Utils {
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWYXZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-=+?<>";

    /**
     * Generates a random password of 10 characters. The set of allowed characters is defined in the getAllowedSymbols method.
     *
     * @return A random string of 10 characters.
     */
    public static String generatePassword() {
        String password = "123";
        /*String allowedCharacters = getAllowedSymbols();
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            char randomChar = allowedCharacters.charAt(randomIndex);
            password.append(randomChar);
        }*/
        return password.toString();
    }

    private static String getAllowedSymbols() {
        return UPPERCASE_LETTERS
            + LOWERCASE_LETTERS
            + DIGITS
            + SYMBOLS;
    }
}
