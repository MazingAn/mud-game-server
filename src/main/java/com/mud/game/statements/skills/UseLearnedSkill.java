package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.combat.SkillRecord;
import com.mud.game.combat.UseLearnedSkillMap;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

import java.util.List;

/**
 * 学会敌人上一个攻击自己的绝招
 * <p>
 * 复制别人的绝招只能有60%的技能等级
 */
public class UseLearnedSkill extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public UseLearnedSkill(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        //基本参数
        CommonCharacter caller = getCaller();
        CommonCharacter target = getTarget();
        SkillObject skillObject = getSkillObject();

        //获取敌人上次释放技能
        SkillObject skill = UseLearnedSkillMap.getSkillObject(caller.getId());
        if (skill != null) {
            GameCharacterManager.castSkill(caller, target, skill);
            caller.msg(new ToastMessage("你使用了" + skill.getName()));
        }
    }
}
