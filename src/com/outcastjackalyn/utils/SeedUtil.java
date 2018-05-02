package com.outcastjackalyn.utils;

public class SeedUtil {

    public static int getDigit(String seed, int index) {
        return Character.getNumericValue(seed.charAt(index));
    }

    public static int getDigitFromEnd(String seed, int index) {
        return Character.getNumericValue(seed.charAt(index > seed.length() ? 0 : seed.length() - index - 1));
    }

    public static int getLength(String seed) {
        return seed.length();
    }

    public static boolean contains(String seed, String substring) {
        return seed.contains(substring);
    }

    public static boolean containsStraight(String seed, int startValue, int increment, int length) {
        String substring = "";
        int j = startValue;
        for(int i = 0; i < length; i++) {
            substring += j;
            j+= increment;
        }
        return seed.contains(substring);
    }



}
