package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

/**
 * 破甲  减少防御buffer
 */
public class PoJia extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public PoJia(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        //防御初始值
        int bDefence = Integer.parseInt(getTarget().getCustomerAttr().get("defence").get("value").toString());
        int i = new Double(bDefence * 0.2).intValue();
        //添加破甲效果
        GameCharacterManager.addBuffer("破甲", 10, 0, 1, false,
                "defence", i, getTarget(), getSkillObject(), false, getCaller());

    }
}
