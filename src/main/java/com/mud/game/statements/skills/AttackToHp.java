package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

/**
 * 命中减少敌人伤害100%内力回复自身内力
 */
public class AttackToHp extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public AttackToHp(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
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
        //参数  attack_to_hp(1,5,0)
        float attackCoefficient = Float.parseFloat(args[0]);  //敌方伤害系数
        float duration = Float.parseFloat(args[1]);
        int coefficient = Integer.getInteger(args[2]);  //命中减少
        //命中减少
        GameCharacterManager.addBuffer("北冥真气", duration, 0, 1, false,
                "precise", coefficient, caller, skillObject, false, caller);
        //敌人伤害100%内力回复自身内力
        CommonCharacter commonCharacter = GameCharacterManager.getCharacterObject(getCaller().getId());
        int aAttack = Integer.parseInt(getTarget().getCustomerAttr().get("attack").get("value").toString());
        commonCharacter.setMp((commonCharacter.getMp() + aAttack * coefficient) > commonCharacter.getMax_mp() ? commonCharacter.getMax_mp() : (commonCharacter.getMp() + aAttack * coefficient));
        GameCharacterManager.saveCharacter(commonCharacter);
    }
}
