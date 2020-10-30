package com.mud.game.handler;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 战利品掉落房间绑定
 */
public class TrophyHandler {
    public static Map<String, Set<String>> TrophyMap = new HashMap<>();

    public static void addTrophy(String key, Set<String> stringList) {
        if (stringList.size() == 0) {
            removeTrophy(key);
            return;
        }
        if (TrophyMap.get(key) == null || TrophyMap.get(key).isEmpty()) {
            TrophyMap.put(key, stringList);
        } else {
            TrophyMap.get(key).addAll(stringList);
        }
    }

    public static void removeTrophy(String key) {
        TrophyMap.remove(key);
    }

    public static void removeTrophy(String key, String dbref) {
        if (TrophyMap.get(key) != null) {
            TrophyMap.get(key).remove(dbref);
        }
    }

    public static Set<String> getTrophy(String key) {
        return TrophyMap.get(key);
    }
}
