package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.handler.CombatHandler;
import com.mud.game.handler.GraduationHandler;
import com.mud.game.handler.NpcCombatHandler;
import com.mud.game.messages.CombatFinishMessage;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.PlayerScheduleManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import org.json.JSONException;

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
        //TODO 逃跑是否成功
        //TODO 判断敌人是否需要关闭
        PlayerScheduleManager.shutdownExecutorByCallerId(caller.getId());
        CombatHandler.removeCombatSense(caller.getId());

        PlayerScheduleManager.shutdownExecutorByCallerId(target.getId());
        CombatHandler.removeCombatSense(target.getId());
        if (target instanceof WorldNpcObject) {
            NpcCombatHandler.removeNpcCombatSense(target.getId(), caller.getId());
            GraduationHandler.addGraduationList(target.getId() + caller.getId());
            CombatHandler.removeCombatSense(target.getId() + caller.getId());
        }
        caller.msg(new CombatFinishMessage("fail"));
        target.msg(new CombatFinishMessage("victory"));
        caller.msg(new ToastMessage("逃跑！"));
    }
}
