package com.outcastjackalyn.utils;

import com.outcastjackalyn.scenes.LockState;

import java.util.Random;

public class AdjectiveUtil {

    public static String[] adjectives = {"big", "small", "fancy", "dirty", "circular", "bland", "pleasant smelling", "crumbling", "surprisingly intact", "dank"};
    public static String[] colors = {"crimson", "chartreuse", "cream", "magnolia", "lilac", "lavender", "turquoise", "metallic", "cerulean", "checkered"};
    public static String[] codes = {"RXF", "PPL", "WEZ", "ASP", "4SP", "BCY", "MTJ", "GVY", "KFT", "A5P"};
    public static String[] books = {"Adventure Novel Uno"};

    public static String updateText(String str, String seed) {
        int i = SeedUtil.getDigit(seed, new Random().nextInt(seed.length()));
        str = str.replaceAll("#adj#", adjectives[i]);
        i = SeedUtil.getDigit(seed, new Random().nextInt(seed.length()));
        str = str.replaceAll("#code#", codes[i]);
        i = SeedUtil.getDigit(seed, new Random().nextInt(seed.length()));
        str = str.replaceAll("#color#", colors[i]);
        //i = SeedUtil.getDigit(seed, new Random().nextInt(seed.length()));
        //str = str.replaceAll("#book#", books[i]);
        str = str.replaceAll("#book#", books[0]);
        return str;
    }

    public static String updateText(String str, String seed, String inventoryDescription) {
        str = str.replaceAll("#pos#", inventoryDescription);
        str = updateText(str, seed);
        return str;
    }

  /*  public static String updateText(String str, int seed, LockState lockState) {
        str = str.replaceAll("#lock#", lockState.name().toLowerCase());
        str = updateText(str, seed);
        return str;
    }*/
}
