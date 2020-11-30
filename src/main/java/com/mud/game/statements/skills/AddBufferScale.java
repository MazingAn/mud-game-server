package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

/**
 * 暴击率增加50%，持续10秒
 * <p>
 * add_buffer_scale("长河落日","crit_rate",1.5,10,0,0)
 */
public class AddBufferScale extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public AddBufferScale(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        CommonCharacter caller = getCaller();
        CommonCharacter target = getTarget();
        SkillObject skillObject = getSkillObject();
        String[] args = getArgs();
        String name = args[0];
        String duration = args[1];
        double coefficient = Double.parseDouble(args[2]);
        float time = Float.parseFloat(args[3]);
        //增加一个buffer
        double criticalRate = Double.parseDouble(caller.getCustomerAttr().get("crit_rate").get("value").toString());
        GameCharacterManager.addBuffer(name, time, 0, 1, true,
                duration, coefficient * criticalRate, target, skillObject, false, caller);
    }
}
