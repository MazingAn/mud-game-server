package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

/**
 * 闪避增加50%，持续5秒
 */
public class ShenXing extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public ShenXing(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        //基本参数
        CommonCharacter caller = getCaller();
        CommonCharacter target = getTarget();
        SkillObject skillObject = getSkillObject();
        String key = getKey();
        String[] args = getArgs();
        beforeAttack();
        //增加一个神行buffer
        int bDodge = Integer.parseInt(target.getCustomerAttr().get("dodge").get("value").toString());
        if (bDodge > 10) {
            bDodge = new Double(bDodge * 0.2).intValue();
            GameCharacterManager.addBuffer("神行", 5, 0, 1, true,
                    "dodge", bDodge, caller, skillObject, false, caller);
        } else if (bDodge > 1) {
            GameCharacterManager.addBuffer("神行", 5, 0, 1, true,
                    "dodge", 1, caller, skillObject, false, caller);
        }
    }
}
