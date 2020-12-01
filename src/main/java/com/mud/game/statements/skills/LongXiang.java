package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

/**
 * 攻击增加、暴击增加
 */
public class LongXiang extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public LongXiang(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        //基本参数
        CommonCharacter caller = getCaller();
        SkillObject skillObject = getSkillObject();
        String key = getKey();
        beforeAttack();
        //获得属性
        int aAttack = Integer.parseInt(caller.getCustomerAttr().get("attack").get("value").toString());
        double criticalRate = Double.parseDouble(caller.getCustomerAttr().get("crit_rate").get("value").toString());
        //增加一个攻击buffer
        GameCharacterManager.addBuffer("龙相", 5, 0, 1, true,
                "attack", new Double(aAttack * 0.2).intValue(), caller, skillObject, false, caller);
        //增加一个暴击buffer
        GameCharacterManager.addBuffer("龙相", 5, 0, 1, true,
                "crit_rate", 0.1, caller, skillObject, false, caller);

    }
}
