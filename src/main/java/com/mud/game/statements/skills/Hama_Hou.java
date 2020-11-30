package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

/**
 * 内力少于你的角色眩晕
 * <p>
 * 眩晕状态下 不可攻击不可防御不可逃跑不可移动所有操作不可
 */
public class Hama_Hou extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public Hama_Hou(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        //基本参数
        CommonCharacter caller = getCaller();
        CommonCharacter target = getTarget();
        beforeAttack();
        if (caller.getMp() > target.getMp()) {
            //增加一个眩晕buffer
            GameCharacterManager.addBuffer("眩晕", 3, 0, 1, false,
                    null, null, getTarget(), getSkillObject(), false, getCaller());
        }
    }
}
