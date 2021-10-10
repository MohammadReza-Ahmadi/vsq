package com.vosouq.util;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Random Generator Utility that provides different types of Enum, password, OTP, String and int
 *
 * @author Siavash
 */

@SuppressWarnings("unused")
public class RandomGeneratorUtility {

    private static final String CHAR_VOWELS = "aeiou";
    private static final String CHAR_CONSONANTS = "bcdfghjklmnpqrstvwxyz";
    private static final String CHAR_LOWERCASE = CHAR_VOWELS + CHAR_CONSONANTS;
    private static final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
    private static final String DIGIT = "0123456789";
    private static final String OTHER_PUNCTUATION = "!@#&()–[{}]:;',?/*";
    private static final String OTHER_SYMBOL = "~$^+=<>";
    private static final String OTHER_SPECIAL = OTHER_PUNCTUATION + OTHER_SYMBOL;
    private static final int PASSWORD_LENGTH = 20;
    private static final int SHORT_STRING_LENGTH = 5;
    private static final int OTP_LENGTH = 6;

    private static final String PASSWORD_ALLOW =
            CHAR_LOWERCASE + CHAR_UPPERCASE + DIGIT + OTHER_SPECIAL;

    private static final SecureRandom random = new SecureRandom();

    public static <T extends Enum<T>> T randomEnumOf(Class<T> enumClass) {
        T[] enumConstants = enumClass.getEnumConstants();
        return enumConstants[random.nextInt(enumConstants.length)];
    }

    public static Timestamp generateTimestamp(){
        return new Timestamp(System.currentTimeMillis() - random.nextInt(1000 * 60 * 60 * 24));
    }

    public static String generateOtp() {
        return generateRandomString(DIGIT, OTP_LENGTH);
    }

    public static String generateSynthesizedName(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        stringBuilder.append(generateRandomString(CHAR_VOWELS, 1).toUpperCase());
        for (int i = 0; i < length - 1; i++) {
            if (i % 2 == 0) {
                stringBuilder.append(generateRandomString(CHAR_CONSONANTS, 1));
            } else {
                stringBuilder.append(generateRandomString(CHAR_VOWELS, 1));
            }
        }
        return stringBuilder.toString();
    }

    public static String generateRandomString(int length) {
        return generateRandomString(DIGIT + CHAR_LOWERCASE + CHAR_UPPERCASE, length);
    }

    public static String generateShorRandomString() {
        StringBuilder result = new StringBuilder(SHORT_STRING_LENGTH);
        // at least 1 lowercase
        String lowercase = generateRandomString(CHAR_LOWERCASE, 1);
        result.append(lowercase);

        // at least 1 digit
        String digit = generateRandomString(DIGIT, 1);
        result.append(digit);

        // fill the rest
        String other = generateRandomString(CHAR_LOWERCASE + DIGIT, 3);
        result.append(other);

        return shuffleString(result.toString());
    }

    public static String generateStrongPassword() {

        StringBuilder result = new StringBuilder(PASSWORD_LENGTH);

        // at least 2 chars (lowercase)
        String lowerCase = generateRandomString(CHAR_LOWERCASE, 2);
        result.append(lowerCase);

        // at least 2 chars (uppercase)
        String uppercase = generateRandomString(CHAR_UPPERCASE, 2);
        result.append(uppercase);

        // at least 2 digits
        String digit = generateRandomString(DIGIT, 2);
        result.append(digit);

        // at least 2 special characters (punctuation + symbols)
        String specialChar = generateRandomString(OTHER_SPECIAL, 2);
        result.append(specialChar);

        // remaining, just random
        String other = generateRandomString(PASSWORD_ALLOW, PASSWORD_LENGTH - 8);
        result.append(other);

        String password = result.toString();

        return shuffleString(password);
    }

    public static String generateNumberString(int size) {
        return generateRandomString(DIGIT, size);
    }

    public static int generateRandomNumber(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static boolean generateRandomBoolean() {
        return random.nextBoolean();
    }

    // generate a random char[], based on `input`
    private static String generateRandomString(String input, int size) {

        if (input == null || input.length() <= 0)
            throw new IllegalArgumentException("Invalid input.");
        if (size < 1) throw new IllegalArgumentException("Invalid size.");

        StringBuilder result = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            // produce a random order
            int index = random.nextInt(input.length());
            result.append(input.charAt(index));
        }
        return result.toString();
    }

    // for final password, shuffle all the characters
    private static String shuffleString(String input) {
        List<String> result = Arrays.asList(input.split(""));
        Collections.shuffle(result);
        return String.join("", result);
    }

}