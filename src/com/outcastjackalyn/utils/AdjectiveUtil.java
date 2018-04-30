package com.outcastjackalyn.utils;

public class AdjectiveUtil {

    public static String[] adjectives = {"big", "small", "fancy", "dirty", "circular", "bland"};
    public static String[] colors = {"crimson", "chartreuse", "cream", "magnolia", "lilac", "lavender", "turquoise", "metallic"};
    public static String[] codes = {"RXF", "PPL", "WEZ", "ASP", "4SP", "BCY", "MTJ", "GVY"};

    public static String updateText(String str, int seed, String inventoryDescription) {
        str.replaceAll("$pos$", inventoryDescription);
        str.replaceAll("$adj$", adjectives[seed]);
        str.replaceAll("$code$", codes[seed]);
        str.replaceAll("$color$", colors[seed]);
        return str;
    }
}
