package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

/**
 * 忙乱技能， 当玩家使用的时候，增加忙乱buff
 * 在忙乱状态下  玩家不可以进行攻击 //修改玩家的canCombat状态为false  忙乱过期后，修改canCombat状态为true
 * */
public class MangLuan extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public MangLuan(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
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
        //增加一个忙乱buffer
        GameCharacterManager.addBuffer("忙乱", duration, 0, 1, false,
                "canAttck", false, target, skillObject);
    }
}
