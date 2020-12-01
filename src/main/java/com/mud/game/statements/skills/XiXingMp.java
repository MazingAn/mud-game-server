package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.algorithm.AttackAlgorithm;
import com.mud.game.algorithm.HarmInfo;
import com.mud.game.combat.FighterManager;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

/**
 * 命中对方吸取伤害100%内力
 */
public class XiXingMp extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public XiXingMp(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        //基本参数
        CommonCharacter caller = getCaller();
        CommonCharacter target = getTarget();
        SkillObject skillObject = getSkillObject();
        String key = getKey();
        beforeAttack();
        //攻击
        HarmInfo harmInfo = AttackAlgorithm.computeFinalHarm(caller, target, skillObject);
        FighterManager.autoCombatAttack(caller, target, skillObject, harmInfo.finalHarm);
        if (harmInfo.precise) {
            //吸取
            caller = GameCharacterManager.getCharacterObject(caller.getId());
            caller.setMp(new Double(caller.getMp() + harmInfo.finalHarm > caller.getMax_mp() ? caller.getMax_mp() : caller.getMp() + harmInfo.finalHarm).intValue());
            GameCharacterManager.saveCharacter(caller);
        }
    }
}