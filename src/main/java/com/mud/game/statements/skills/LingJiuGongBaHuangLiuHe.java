package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

/**
 * 战斗属性增加30%，持续10秒
 */
public class LingJiuGongBaHuangLiuHe extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public LingJiuGongBaHuangLiuHe(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        //基本参数
        CommonCharacter caller = getCaller();
        CommonCharacter target = getTarget();
        SkillObject skillObject = getSkillObject();
        double value = 0.3;
        //增加一个防御buffer
        int bDefence = Integer.parseInt(caller.getCustomerAttr().get("defence").get("value").toString());
        GameCharacterManager.addBuffer("八荒六合", 5, 0, 1, true,
                "defence", new Double(bDefence * value).intValue(), caller, skillObject, false, caller);
        //增加一个攻击buffer
        int attack = Integer.parseInt(caller.getCustomerAttr().get("attack").get("value").toString());
        GameCharacterManager.addBuffer("八荒六合", 5, 0, 1, true,
                "attack", new Double(attack * value).intValue(), caller, skillObject, false, caller);
        //增加一个命中buffer
        int precise = Integer.parseInt(caller.getCustomerAttr().get("precise").get("value").toString());
        GameCharacterManager.addBuffer("八荒六合", 5, 0, 1, true,
                "precise", new Double(precise * value).intValue(), caller, skillObject, false, caller);
        //增加一个躲闪buffer
        int dodge = Integer.parseInt(caller.getCustomerAttr().get("dodge").get("value").toString());
        GameCharacterManager.addBuffer("八荒六合", 5, 0, 1, true,
                "precise", new Double(dodge * value).intValue(), caller, skillObject, false, caller);
        //增加一个招架buffer
        int parry = Integer.parseInt(caller.getCustomerAttr().get("parry").get("value").toString());
        GameCharacterManager.addBuffer("八荒六合", 5, 0, 1, true,
                "parry", new Double(parry * value).intValue(), caller, skillObject, false, caller);
    }
}
