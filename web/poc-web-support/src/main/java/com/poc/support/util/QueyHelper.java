package com.poc.support.util;

public final class QueyHelper {
    private static final String LIKE_SYMBOL = "%";
    private QueyHelper() {
        throw new AssertionError();
    }

    public static String quoteContaining(String value) {
        return LIKE_SYMBOL + quoteStartWith(value);
    }

    public static String quoteStartWith(String value) {
        return value + LIKE_SYMBOL;
    }
}
