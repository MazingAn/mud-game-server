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
public class XuanYun extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public XuanYun(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
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
        float duration = Float.parseFloat(args[0]);
        beforeAttack();
        if (caller.getMp() > target.getMp()) {
            //防御初始值
            //int bDefence = Integer.parseInt(getTarget().getCustomerAttr().get("defence").get("value").toString());
            //增加一个眩晕buffer
            GameCharacterManager.addBuffer("眩晕", duration, 0, 1, false,
                    null, null, getTarget(), getSkillObject(), false, getCaller());
        }
    }
}
