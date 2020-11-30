package com.mud.game.algorithm;

import com.mud.game.combat.CombatSense;
import com.mud.game.handler.AutoContestHandler;
import com.mud.game.handler.CombatHandler;
import com.mud.game.messages.SkillCastMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.SkillObjectManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.structs.SkillCastInfo;
import com.mud.game.utils.StateConstants;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.mud.game.server.ServerManager.gameSetting;
import static com.mud.game.utils.StateConstants.CHECK_PRECISE_STATE;
import static com.mud.game.utils.StateConstants.CRITICAL_SKILL_NAME;

/**
 * 战斗时刻的计算
 */
public class AttackAlgorithm {

    /**
     * 命中检测
     * 命中几率  =  (对方闪避 - 自身命中） / 对方闪避
     */
    public static boolean canPrecise(CommonCharacter a, CommonCharacter b) {
        //判断当前状态能否闪避
        if (!StateConstants.checkState(b, CHECK_PRECISE_STATE)) {
            return true;
        }
        int bDodge = Integer.parseInt(b.getCustomerAttr().get("dodge").get("value").toString());
        int aPrecise = Integer.parseInt(a.getCustomerAttr().get("precise").get("value").toString());
        return (bDodge - aPrecise) / (double) bDodge < Math.random();
    }

    /**
     * 暴击检测
     * 暴击几率 = 自身暴击几率
     */
    public static boolean canCritical(CommonCharacter a, CommonCharacter b, SkillObject skillObject) {
        //必暴击
        if (skillObject.getSkillFunction().contains("baoji")) {
            return true;
        }
        if (CRITICAL_SKILL_NAME.contains(skillObject.getName())) {
            return true;
        }

        double criticalRate = Double.parseDouble(a.getCustomerAttr().get("crit_rate").get("value").toString());
        double coefficient = 1;
        //减少暴击
        if (b.getBuffers().containsKey("九阳护体")) {
            coefficient = coefficient - 0.2;
        }
        //减少暴击
        if (b.getBuffers().containsKey("金刚伏魔圈")) {
            coefficient = coefficient - 0.2;
        }
        if (coefficient <= 0) {
            coefficient = 0;
        }
        coefficient = criticalRate * coefficient;
        return Math.random() < criticalRate;
    }


    /**
     * 基本伤害计算
     * 伤害 = 自身攻击 - 对方防御
     */
    public static int computeHarm(CommonCharacter a, CommonCharacter b) {
        int aAttack = Integer.parseInt(a.getCustomerAttr().get("attack").get("value").toString());
        int bDefence = Integer.parseInt(b.getCustomerAttr().get("defence").get("value").toString());
        if (aAttack - bDefence <= 0) return 1;
        return aAttack - bDefence;
    }

    /**
     * 计算暴击伤害
     * 暴击伤害 = 基本伤害 * 暴击伤害 * 暴击倍率 - 对方暴击抵抗
     */
    public static float computeCriticalHarm(CommonCharacter a, CommonCharacter b) {
        int baseHarm = computeHarm(a, b);
        float criticalScale = Float.parseFloat(a.getCustomerAttr().get("crit_rate").get("value").toString());
        float criticalHarmScale = Float.parseFloat(a.getCustomerAttr().get("crit_damage").get("value").toString());
        float targetResistCritical = Float.parseFloat(b.getCustomerAttr().get("resist_crit").get("value").toString());
        float finalCriticalHarm = baseHarm * criticalHarmScale * criticalScale - targetResistCritical;
        return finalCriticalHarm < baseHarm ? baseHarm : finalCriticalHarm;
    }

    /**
     * 最终伤害计算
     * 应用命中，暴击检测、暴击伤害和暴击抵抗
     */
    public static HarmInfo computeFinalHarm(CommonCharacter a, CommonCharacter b, SkillObject skillObject) {
        HarmInfo harmInfo = new HarmInfo();
        if (canPrecise(a, b)) {//可以命中
            harmInfo.precise = true;
            harmInfo.finalHarm = computeHarm(a, b);
            if (canCritical(a, b, skillObject)) {//触发暴击
                harmInfo.critical = true;
                harmInfo.finalHarm = computeCriticalHarm(a, b);
            }
        }
        harmInfo = adjustmentAttack(harmInfo, a, b, 1);
        return harmInfo;
    }

    private static HarmInfo adjustmentAttack(HarmInfo harmInfo, CommonCharacter a, CommonCharacter b, double coefficient) {
        //追魂状态伤害增益
        if (a.getBuffers().containsKey("追魂")) {
            coefficient = coefficient - 0.2;
        }
        //减伤
        if (b.getBuffers().containsKey("九阳护体")) {
            coefficient = coefficient - 0.2;
        }
        if (b.getBuffers().containsKey("金刚伏魔圈")) {
            coefficient = coefficient - 0.2;
        }
        if (coefficient <= 0) {
            coefficient = 0;
        }
        harmInfo.finalHarm = (float) (harmInfo.finalHarm * coefficient);
        return harmInfo;
    }

    /**
     * @param a           释放技能对象
     * @param b           技能作用对象
     * @param skillObject 表情
     * @param coefficient 伤害系数
     * @return
     */
    public static HarmInfo computeFinalHarm(CommonCharacter a, CommonCharacter b, SkillObject skillObject, double coefficient) {
        HarmInfo harmInfo = new HarmInfo();
        if (canPrecise(a, b)) {//可以命中
            harmInfo.precise = true;
            harmInfo.finalHarm = computeHarm(a, b);
            if (canCritical(a, b, skillObject)) {//触发暴击
                harmInfo.critical = true;
                harmInfo.finalHarm = computeCriticalHarm(a, b);
            }
        }
        harmInfo = AttackAlgorithm.adjustmentAttack(harmInfo, a, b, 1);
        return harmInfo;
    }


    /**
     * 连击
     */
    public static void lianji(CommonCharacter a, CommonCharacter b, SkillObject skillObject, int number) {
        String[] castMessages = skillObject.getMessage().split(";");
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        CombatSense sense = CombatHandler.getCombatSense(a.getId());
        Runnable runnable = new Runnable() {
            int i = 0;

            @Override
            public void run() {
                if (i < number && a.getHp() > 0 && b.getHp() > 0) {
                    //计算伤害
                    HarmInfo harmInfo = AttackAlgorithm.computeFinalHarm(a, b, skillObject);
                    //应用伤害
                    GameCharacterManager.changeStatus(b, "hp", harmInfo.finalHarm * -1, a);
                    GameCharacterManager.saveCharacter(b);
                    //构建战斗输出
                    if (i < castMessages.length) {
                        SkillCastInfo skillCastInfo = new SkillCastInfo(a, b, skillObject, castMessages[i]);
                        sense.msgContents(new SkillCastMessage(skillCastInfo));
                    } else {
                        SkillCastInfo skillCastInfo = new SkillCastInfo(a, b, skillObject, castMessages[0]);
                        sense.msgContents(new SkillCastMessage(skillCastInfo));
                    }
                    i++;
                } else {
                    service.shutdown();
                    a.autoCombatPause = false;
                    GameCharacterManager.saveCharacter(a);
                }
            }
        };
        service.scheduleAtFixedRate(runnable, 0, (int) gameSetting.getGlobalCD() * 500,
                TimeUnit.MILLISECONDS);
    }

    /**
     * 造成n倍伤害，是否无法格挡、闪避
     *
     * @param caller       技能释放者
     * @param target       技能对象
     * @param skillObject  技能
     * @param multiple     伤害倍数
     * @param isBlockDodge 是否无法格挡、闪避
     */
    public static void hitPersent(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, int multiple, boolean isBlockDodge) {
        CombatSense sense = CombatSense.getSence(caller, target);
        SkillCastInfo skillCastInfo = null;
        //判断能否进行攻击
        //计算伤害
        HarmInfo harmInfo = AttackAlgorithm.computeFinalHarm(caller, target, skillObject);
        //应用伤害
        GameCharacterManager.changeStatus(target, "hp", harmInfo.finalHarm * -1 * multiple, caller);
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
