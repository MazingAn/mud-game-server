package com.mud.game.combat;

import com.mud.game.object.typeclass.SkillObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 战斗中暂存学习技能
 */
public class UseLearnedSkillMap {
    static Map<String, SkillObject> skillMap = new HashMap<String, SkillObject>();

    public static SkillObject getSkillObject(String callerId) {
        return skillMap.get(callerId);
    }

    public static void removeSkill(String callerId) {
        skillMap.remove(callerId);
    }

    public static void addSkill(String callerId, SkillObject skillObject) {
        skillMap.put(callerId, skillObject);
    }

}
