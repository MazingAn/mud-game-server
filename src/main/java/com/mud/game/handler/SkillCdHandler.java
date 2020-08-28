package com.mud.game.handler;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SkillCdHandler {
    public static Map<String, Date> skillCdMap = new HashMap<>();

    public static void addSkillCd(String key, Date date) {
        skillCdMap.put(key, date);
    }

    public static void removeSkillCd(String key) {
        skillCdMap.remove(key);
    }

    public static Date getSkillCd(String key) {
        return skillCdMap.get(key);
    }
}
