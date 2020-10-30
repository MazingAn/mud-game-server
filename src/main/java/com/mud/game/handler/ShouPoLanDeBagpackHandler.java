package com.mud.game.handler;

import java.util.*;

/**
 * 收破烂的背包
 */
public class ShouPoLanDeBagpackHandler {
    public static Map<String, Integer> ShouPoLanDeBagpack = new HashMap<>();

    public static void addShouPoLanDeBagpack(String key, int number) {
        if (ShouPoLanDeBagpack.size() >= 5) {
            ShouPoLanDeBagpack.remove(0);
        }
        ShouPoLanDeBagpack.put(key, number);
    }

    public static void removeTrophy(String key) {
        ShouPoLanDeBagpack.remove(key);
    }
}
