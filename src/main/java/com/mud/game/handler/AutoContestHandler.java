package com.mud.game.handler;

import com.mud.game.object.supertypeclass.CommonCharacter;

import java.util.HashMap;
import java.util.Map;

public class AutoContestHandler {
    public static Map<String, CommonCharacter> commonCharacterMap = new HashMap<>();

    public static void addCommonCharacter(String characterId, CommonCharacter commonCharacter) {
        commonCharacterMap.put(characterId, commonCharacter);
    }

    public static void removeCommonCharacter(String characterId) {
        commonCharacterMap.remove(characterId);
    }

    public static CommonCharacter getCommonCharacter(String characterId) {
        return commonCharacterMap.get(characterId);
    }
}
