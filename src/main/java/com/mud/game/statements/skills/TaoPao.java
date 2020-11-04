package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.combat.CombatSense;
import com.mud.game.handler.CombatHandler;
import com.mud.game.handler.GraduationHandler;
import com.mud.game.handler.NpcCombatHandler;
import com.mud.game.messages.CombatFinishMessage;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.HangUpManager;
import com.mud.game.object.manager.PlayerScheduleManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import com.mud.game.worlddata.db.models.WorldNpcWanderRoom;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TaoPao extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public TaoPao(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        //基本参数
        CommonCharacter caller = getCaller();
        CommonCharacter target = getTarget();
        // 关闭战斗场景
        if (!getEscapeProbability(caller, target)) {
            caller.msg(new ToastMessage("逃跑失败！"));
            return;
        }
        //TODO 判断敌人是否需要关闭  可能同时攻击npc以及玩家
        PlayerScheduleManager.shutdownExecutorByCallerId(caller.getId());
        CombatHandler.removeCombatSense(caller.getId());
        if (target instanceof WorldNpcObject) {
            NpcCombatHandler.removeNpcCombatSense(target.getId(), caller.getId());
            GraduationHandler.addGraduationList(target.getId() + caller.getId());
            CombatHandler.removeCombatSense(target.getId() + caller.getId());
            CombatSense.initializeNpc(target);
        } else {
            CombatSense combatSense = CombatHandler.getCombatSense(target.getId());
            if (checkRedTem(combatSense.getRedTeam(), caller)) {
                removeCombatSense(combatSense, caller, target, true);
            } else {
                removeCombatSense(combatSense, caller, target, false);
            }
        }
        caller.msg(new CombatFinishMessage("runAway"));
        caller.msg(new ToastMessage("逃跑成功！"));
    }

    private void removeCombatSense(CombatSense combatSense, CommonCharacter caller, CommonCharacter target, boolean isRed) {
        ArrayList<CommonCharacter> team = isRed ? combatSense.getRedTeam() : combatSense.getBlueTeam();
        if (team.size() <= 1) {
            PlayerScheduleManager.shutdownExecutorByCallerId(target.getId());
            CombatHandler.removeCombatSense(target.getId());
        } else {
            Iterator<CommonCharacter> iterator = team.iterator();
            while (iterator.hasNext()) {
                CommonCharacter u = iterator.next();
                if (u.getId().equals(caller.getId())) {
                    iterator.remove();
                }
            }
            CombatHandler.addCombatSense(target.getId(), combatSense);
        }
    }

    private boolean checkRedTem(ArrayList<CommonCharacter> redTeam, CommonCharacter caller) {
        Iterator<CommonCharacter> iterator = redTeam.iterator();
        while (iterator.hasNext()) {
            CommonCharacter u = iterator.next();
            if (u.getId().equals(caller.getId())) {
                return true;
            }
        }
        return false;
    }

    private boolean getEscapeProbability(CommonCharacter caller, CommonCharacter target) {
        //  逃跑是否成功  身法高 100%  身法相同 50%  身法低10%
        if (caller.getAfter_body() > target.getAfter_body()) {
            return true;
        } else if (caller.getAfter_body() == target.getAfter_body()) {
            return HangUpManager.randomInterval(0, 2) == 0;
        } else {
            return HangUpManager.randomInterval(0, 11) == 0;
        }
    }
}
