package com.mud.game.combat;

import com.mud.game.object.typeclass.SkillObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对象在战斗中释放的技能记录
 */
public class SkillRecord {
    static Map<String, List<SkillObject>> skillRecordMap = new HashMap<String, List<SkillObject>>();

    public static List<SkillObject> getSkillObject(String callerId) {
        return skillRecordMap.get(callerId);
    }

    public static void removeRecord(String callerId) {
        skillRecordMap.remove(callerId);
    }

    public static void addRecord(String callerId, SkillObject skillObject) {
        List<SkillObject> skillRecord = getSkillObject(callerId);
        if (skillRecord == null) {
            skillRecord = new ArrayList<SkillObject>();
        }
        skillRecord.add(skillObject);
        skillRecordMap.put(callerId, skillRecord);
    }
}
