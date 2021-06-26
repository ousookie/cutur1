package com.url.cutter.utilities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class UrlShortCutter {

    private static final String ALLOWED_BASE6_2ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final char[] ALLOWED_BASE62_ALPHABET_CHARS = ALLOWED_BASE6_2ALPHABET.toCharArray();
    private static final int BASE = ALLOWED_BASE62_ALPHABET_CHARS.length;

    private UrlShortCutter() {

    }

    public static int getBase() {
        return BASE;
    }

    /**
     * @param sourceValue: base10 integer to convert
     * @return indexes list of base62 alphabet chars
     */
    private static List<Integer> getBase62IndexesList(int sourceValue) {
        int number = sourceValue;
        List<Integer> alphabet62Indexes = new ArrayList<>();
        while (number != 0) {
            int remainder = number % UrlShortCutter.getBase();
            number = number / UrlShortCutter.getBase();
            alphabet62Indexes.add(remainder);
        }
        alphabet62Indexes.sort(Comparator.reverseOrder());
        return alphabet62Indexes;
    }

    /**
     * @param sourceValue: base10 integer to convert
     * @return result string which contain base62 alphabet chars by indexes
     */
    public static String getBase62String(int sourceValue) {
        List<Integer> alphabet62Indexes = getBase62IndexesList(sourceValue);
        StringBuilder buffer = new StringBuilder();
        for (int val : alphabet62Indexes) {
            buffer.append(ALLOWED_BASE62_ALPHABET_CHARS[val]);
        }
        return new String(buffer);
    }

}


