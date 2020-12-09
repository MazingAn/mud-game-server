package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.combat.SkillRecord;
import com.mud.game.combat.UseLearnedSkillMap;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

import java.util.List;

/**
 * 释放学会的绝招攻击敌人
 */
public class LearnRandomSkill extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public LearnRandomSkill(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        //基本参数
        CommonCharacter caller = getCaller();
        CommonCharacter target = getTarget();
        SkillObject skillObject = getSkillObject();

        //获取敌人上次释放技能
        List<SkillObject> skillObjectList = SkillRecord.getSkillObject(target.getId());
        if (skillObjectList != null && skillObjectList.size() != 0) {
            UseLearnedSkillMap.addSkill(caller.getId(), skillObjectList.get(skillObjectList.size() - 1));
        }
    }
}
