package com.mud.game.handler;

import com.mud.game.combat.CombatSense;

import java.util.HashMap;
import java.util.Map;

public class NpcCombatHandler {
    /**
     * npc 战斗数据
     * npc和多个玩家战斗分别属于单独场景
     */
    public static Map<String, HashMap<String, CombatSense>> npcCombatSenseMap = new HashMap<>();

    public static void addNpcCombatSense(String characterId, String targetId, CombatSense combatSense) {
        npcCombatSenseMap.put(characterId, npcCombatSenseMap.get(characterId) == null ? new HashMap<String, CombatSense>() {{
            put(targetId, combatSense);
        }} : npcCombatSenseMapPut(targetId, combatSense));
    }

    private static HashMap<String, CombatSense> npcCombatSenseMapPut(String characterId, CombatSense combatSense) {
        HashMap<String, CombatSense> map = npcCombatSenseMap.get(characterId);
        if (null == map) {
            map = new HashMap<>();
        }
        map.put(characterId, combatSense);
        return map;
    }


    public static void removeNpcCombatSense(String characterId) {
        npcCombatSenseMap.remove(characterId);
    }

    public static void removeNpcCombatSense(String characterId, String targetId) {
        if (null != npcCombatSenseMap.get(characterId)) {
            npcCombatSenseMap.get(characterId).remove(targetId);
            if (null == npcCombatSenseMap.get(characterId) || npcCombatSenseMap.get(characterId).size() == 0) {
                npcCombatSenseMap.remove(characterId);
            }
        }
    }

    public static CombatSense getNpcCombatSense(String characterId, String targetId) {
        return npcCombatSenseMap.get(characterId) == null ? null : npcCombatSenseMap.get(characterId).get(targetId);
    }

    public static HashMap<String, CombatSense> getNpcCombatSense(String characterId) {
        return npcCombatSenseMap.get(characterId);
    }


    public static boolean containsKey(String characterId) {
        return npcCombatSenseMap.containsKey(characterId);
    }
}
