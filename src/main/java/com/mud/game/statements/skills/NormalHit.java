package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.algorithm.AttackAlgorithm;
import com.mud.game.algorithm.HarmInfo;
import com.mud.game.combat.CombatSense;
import com.mud.game.handler.CombatHandler;
import com.mud.game.messages.SkillCastMessage;
import com.mud.game.object.manager.SkillObjectManager;
import com.mud.game.structs.SkillCastInfo;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

/**
 * 基本攻击技能
 * <p>
 * 定义： hit()
 * 效果： 根据自身攻击速度攻击敌人（造成自身伤害-敌方防御的伤害）
 */
public class NormalHit extends BaseAttackSkillStatement {

    public NormalHit(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
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
        CombatSense sense = CombatHandler.getCombatSense(caller.getId());
        SkillCastInfo skillCastInfo = null;
        //判断能否进行攻击
        if (!caller.isCanAttck()) {
            skillCastInfo = new SkillCastInfo(caller, target,caller.getName()+"目前状态不能攻击！");
        } else {
            //计算伤害
            HarmInfo harmInfo = AttackAlgorithm.computeFinalHarm(caller, target);
            //应用伤害
            GameCharacterManager.changeStatus(target, "hp", harmInfo.finalHarm * -1);
            //构建战斗输出
            String combatCastStr = SkillObjectManager.getCastMessage(caller, target, skillObject, harmInfo);
            skillCastInfo = new SkillCastInfo(caller, target, skillObject, combatCastStr);
            //更新同步数据
            GameCharacterManager.saveCharacter(target);
        }
        sense.msgContents(new SkillCastMessage(skillCastInfo));
    }

}
