package com.mud.game.handler;

import com.mud.game.combat.CombatSense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraduationHandler {
    /**
     * 出师数据记录
     */
    public static List<String> graduationList = new ArrayList<>();

    public static void addGraduationList(String characterId) {
        graduationList.add(characterId);
    }

    public static void removeGraduationList(String characterId) {
        graduationList.remove(characterId);
    }

    public static Boolean existsGraduationList(String characterId) {
        return graduationList.contains(characterId);
    }
}
