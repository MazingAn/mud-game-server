package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.algorithm.AttackAlgorithm;
import com.mud.game.algorithm.HarmInfo;
import com.mud.game.combat.CombatSense;
import com.mud.game.commands.BaseCommand;
import com.mud.game.handler.AutoContestHandler;
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
import org.json.JSONObject;
import org.yeauty.pojo.Session;

/**
 * 无视忙乱
 */
public class WuShiMangLuan extends BaseAttackSkillStatement {

    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public WuShiMangLuan(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
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
        CombatSense sense = null;
        if (caller instanceof WorldNpcObject) {
            sense = NpcCombatHandler.getNpcCombatSense(caller.getId(), target.getId());
        } else {
            sense = CombatHandler.getCombatSense(caller.getId());
        }
        SkillCastInfo skillCastInfo = null;
        //判断能否进行攻击
        //计算伤害
        HarmInfo harmInfo = AttackAlgorithm.computeFinalHarm(caller, target,skillObject);
        //应用伤害
        GameCharacterManager.changeStatus(target, "hp", harmInfo.finalHarm * -1, caller);
        //构建战斗输出
        String combatCastStr = SkillObjectManager.getCastMessage(caller, target, skillObject, harmInfo);
        skillCastInfo = new SkillCastInfo(caller, target, skillObject, combatCastStr);
        //更新同步数据
        //GameCharacterManager.saveCharacter(target);
        CommonCharacter commonCharacter = AutoContestHandler.getCommonCharacter(caller.getId() + target.getId());
        if (commonCharacter == null) {
            commonCharacter = AutoContestHandler.getCommonCharacter(target.getId() + caller.getId());
        }
        if (commonCharacter == null) {
            GameCharacterManager.saveCharacter(target);
        }
        sense.msgContents(new SkillCastMessage(skillCastInfo));
    }
}
