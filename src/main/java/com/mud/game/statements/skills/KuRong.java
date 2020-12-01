package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

/**
 * 增加防御、抗暴
 */
public class KuRong extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public KuRong(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
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
        int bDefence = Integer.parseInt(caller.getCustomerAttr().get("defence").get("value").toString());
        //增加一个防御buffer
        GameCharacterManager.addBuffer("枯荣", 5, 0, 1, true,
                "defence", new Double(bDefence * 0.5).intValue(), caller, skillObject, false, caller);

    }
}
