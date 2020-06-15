package com.mud.game.handler;

import com.mud.game.combat.CombatSense;
import org.apache.commons.collections4.map.CompositeMap;

import java.util.HashMap;
import java.util.Map;

public class CombatHandler {

    public static Map<String, CombatSense> combatSenseMap = new HashMap<>();

    public static void addCombatSense(String characterId, CombatSense combatSense){
        combatSenseMap.put(characterId, combatSense);
    }

    public static void removeCombatSense(String characterId){
        combatSenseMap.remove(characterId);
    }

    public static CombatSense getCombatSense(String characterId){
        return combatSenseMap.get(characterId);
    }

}
