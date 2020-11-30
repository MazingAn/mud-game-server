package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

/**
 * 回血
 */
public class AddHpPersent extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public AddHpPersent(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
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
        //回血百分比
        float duration = Float.parseFloat(args[0]);
        caller = GameCharacterManager.getCharacterObject(caller.getId());
        int newHp = new Double(caller.getMax_hp() * duration).intValue() + caller.getHp();
        caller.msg(new ToastMessage(String.format("使用%s恢复了%s点气血", skillObject.getName(), newHp - caller.getHp())));
        caller.setHp(newHp > caller.getMax_hp() ? caller.getMax_hp() : newHp);
        GameCharacterManager.saveCharacter(caller);
    }
}
