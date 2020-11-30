package com.mud.game.statements.skills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.combat.CombatSense;
import com.mud.game.combat.FighterManager;
import com.mud.game.handler.CombatHandler;
import com.mud.game.handler.NpcCombatHandler;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerScheduleManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import com.mud.game.structs.AttackState;
import org.json.JSONException;

import java.util.concurrent.ScheduledExecutorService;

import static com.mud.game.constant.Constant.CONTEST_MIN_HP_COEFFICIENT;

/**
 * 添加追击状态
 * <p>
 * 追击状态存在的情况下 每次攻击完毕都进行判定 判定成功就在进行一次额外的攻击
 * 至于判定成功率和持续时间 根据技能等级来
 * 预设先给个5秒 50%吧
 */
public class ZhuiJi extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public ZhuiJi(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        CombatSense sense = null;
        if (getCaller() instanceof WorldNpcObject) {
            sense = NpcCombatHandler.getNpcCombatSense(getCaller().getId(), getTarget().getId());
        } else {
            sense = CombatHandler.getCombatSense(getCaller().getId());
        }

        CommonCharacter caller = GameCharacterManager.getCharacterObject(getCaller().getId());
        CommonCharacter target = GameCharacterManager.getCharacterObject(getCaller().getTarget());
        if (!sense.isCombatFinished()) {
            double callerMinHp = sense.getMinHp();
            double targetMinHp = sense.getMinHp();
            if (sense.getMinHp() == -1) {
                callerMinHp = caller.getMax_hp() * CONTEST_MIN_HP_COEFFICIENT;
                targetMinHp = target.getMax_hp() * CONTEST_MIN_HP_COEFFICIENT;
            }
            if (!caller.autoCombatPause && caller.getHp() > callerMinHp) {
                if (!caller.isCanCombat()) {
                    caller.msg(new ToastMessage("你现在的状态，无法进行战斗！"));
                } else {
                    //如果目标已死亡，重新选定目标
                    if (target.getHp() <= targetMinHp) {
                        target = FighterManager.setRandomTarget(getCaller(), sense.getBlueTeam());
                    }
                    GameCharacterManager.castSkill(caller, target, GameCharacterManager.getDefaultSkill(caller));
                    if (sense.isCombatFinished()) {
                        sense.onCombatFinish();
                    }
                }
            }
        }
    }
}