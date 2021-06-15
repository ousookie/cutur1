package test.test.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class UrlCutter {
    private static final String ALLOWED_BASE6_2ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final char[] ALLOWED_BASE62_ALPHABET_CHARS = ALLOWED_BASE6_2ALPHABET.toCharArray();
    private static final int BASE = ALLOWED_BASE62_ALPHABET_CHARS.length;

    public static String getAllowedBase62AlphabetString() {
        return ALLOWED_BASE6_2ALPHABET;
    }

    public static char[] getAllowedBase62AlphabetChars() {
        return ALLOWED_BASE62_ALPHABET_CHARS;
    }

    public static int getBase() {
        return BASE;
    }

    private static List<Integer> getBase62IndexesList(int sourceValue) {
        int number = sourceValue;
        List<Integer> alphabet62Indexes = new ArrayList<>();
        while (number != 0) {
            int remainder = number % UrlCutter.getBase();
            number = number / UrlCutter.getBase();
            alphabet62Indexes.add(remainder);
        }
        alphabet62Indexes.sort(Comparator.reverseOrder());
        return alphabet62Indexes;
    }

    public static String getBase62String(int sourceValue) {
        List<Integer> alphabet62Indexes = getBase62IndexesList(sourceValue);
        StringBuilder buffer = new StringBuilder();
        for (int val : alphabet62Indexes) {
            buffer.append(ALLOWED_BASE62_ALPHABET_CHARS[val]);
        }
        return new String(buffer);
    }
}


