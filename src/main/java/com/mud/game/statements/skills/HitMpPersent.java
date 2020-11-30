package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.algorithm.HarmInfo;
import com.mud.game.combat.CombatSense;
import com.mud.game.handler.CombatHandler;
import com.mud.game.handler.NpcCombatHandler;
import com.mud.game.messages.SkillCastMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.SkillObjectManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import com.mud.game.structs.SkillCastInfo;
import org.json.JSONException;

/**
 * 造成最大内力值的10%的伤害
 */
public class HitMpPersent extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public HitMpPersent(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        //计算伤害
        HarmInfo harmInfo = computeFinalHarm(getCaller(), getTarget(), getSkillObject());
        //应用伤害
        GameCharacterManager.changeStatus(getTarget(), "hp", harmInfo.finalHarm * -1, getCaller());
        //构建战斗输出
        String combatCastStr = SkillObjectManager.getCastMessage(getCaller(), getTarget(), getSkillObject(), harmInfo);
        SkillCastInfo skillCastInfo = new SkillCastInfo(getCaller(), getTarget(), getSkillObject(), combatCastStr);
        CombatSense sense = null;
        if (getCaller() instanceof WorldNpcObject) {
            sense = NpcCombatHandler.getNpcCombatSense(getCaller().getId(), getTarget().getId());
        } else {
            sense = CombatHandler.getCombatSense(getCaller().getId());
        }
        if (sense == null) {
            //切磋场景
            sense = CombatHandler.getCombatSense(getCaller().getId() + getTarget().getId());
        }
        sense.msgContents(new SkillCastMessage(skillCastInfo));
    }

    private HarmInfo computeFinalHarm(CommonCharacter caller, CommonCharacter target, SkillObject skillObject) {
        HarmInfo harmInfo = new HarmInfo();
        harmInfo.precise = true;
        // 最大内力值的10%的伤害
        harmInfo.finalHarm = (float) (target.getMax_mp() * 0.1);
        harmInfo.critical = true;
        return harmInfo;
    }
}
