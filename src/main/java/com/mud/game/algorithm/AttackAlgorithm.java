package com.mud.game.algorithm;

import com.mud.game.combat.CombatSense;
import com.mud.game.combat.FighterManager;
import com.mud.game.handler.CombatHandler;
import com.mud.game.messages.SkillCastMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerScheduleManager;
import com.mud.game.object.manager.SkillObjectManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.structs.SkillCastInfo;
import com.mud.game.utils.modelsutils.Mark;
import com.mud.game.worlddata.db.models.CharacterModel;
import io.netty.util.concurrent.SingleThreadEventExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.mud.game.server.ServerManager.gameSetting;

/**战斗时刻的计算*/
public class AttackAlgorithm {

    /**
     * 命中检测
     * 命中几率  =  (对方闪避 - 自身命中） / 对方闪避
     * */
    public static boolean canPrecise(CommonCharacter a, CommonCharacter b){
        int bDodge = Integer.parseInt(b.getCustomerAttr().get("dodge").get("value").toString());
        int aPrecise = Integer.parseInt(a.getCustomerAttr().get("precise").get("value").toString());
        return (bDodge-aPrecise) / (double)bDodge < Math.random();
    }

    /**
     * 暴击检测
     * 暴击几率 = 自身暴击几率
     * */
    public static boolean canCritical(CommonCharacter a){
        double criticalRate = Double.parseDouble(a.getCustomerAttr().get("crit_rate").get("value").toString());
        return Math.random() < criticalRate;
    }

    /**
     * 基本伤害计算
     * 伤害 = 自身攻击 - 对方防御
     * */
    public static int computeHarm(CommonCharacter a, CommonCharacter b){
        int aAttack = Integer.parseInt(a.getCustomerAttr().get("attack").get("value").toString());
        int bDefence = Integer.parseInt(b.getCustomerAttr().get("defence").get("value").toString());
        if(aAttack - bDefence <= 0) return 1;
        return aAttack - bDefence;
    }

    /**
     * 计算暴击伤害
     * 暴击伤害 = 基本伤害 * 暴击伤害 * 暴击倍率 - 对方暴击抵抗
     * */
    public static float computeCriticalHarm(CommonCharacter a, CommonCharacter b){
        int baseHarm = computeHarm(a,b);
        float criticalScale = Float.parseFloat(a.getCustomerAttr().get("crit_rate").get("value").toString());
        float criticalHarmScale = Float.parseFloat(a.getCustomerAttr().get("crit_damage").get("value").toString());
        float targetResistCritical = Float.parseFloat(b.getCustomerAttr().get("resist_crit").get("value").toString());
        float finalCriticalHarm = baseHarm * criticalHarmScale * criticalScale - targetResistCritical;
        return finalCriticalHarm < baseHarm ? baseHarm : finalCriticalHarm;
    }

    /**
     * 最终伤害计算
     * 应用命中，暴击检测、暴击伤害和暴击抵抗
     * */
    public static HarmInfo computeFinalHarm(CommonCharacter a, CommonCharacter b){
        HarmInfo harmInfo = new HarmInfo();
        if(canPrecise(a, b)){//可以命中
            harmInfo.precise = true;
            harmInfo.finalHarm = computeHarm(a,b);
            if(canCritical(a)){//触发暴击
                harmInfo.critical = true;
                harmInfo.finalHarm = computeCriticalHarm(a,b);
            }
        }
        return harmInfo;
    }

    /** 连击 */
    public static void lianji(CommonCharacter a, CommonCharacter b, SkillObject skillObject, int number){
        String[] castMessages = skillObject.getMessage().split(";");
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        CombatSense sense = CombatHandler.getCombatSense(a.getId());
        Runnable runnable = new Runnable() {
            int i = 0;
            @Override
            public void run() {
                if(i < number && a.getHp() > 0 && b.getHp() > 0){
                    //计算伤害
                    HarmInfo harmInfo = AttackAlgorithm.computeFinalHarm(a, b);
                    //应用伤害
                    GameCharacterManager.changeStatus(b, "hp", harmInfo.finalHarm * -1);
                    GameCharacterManager.saveCharacter(b);
                    //构建战斗输出
                    if(i < castMessages.length){
                        SkillCastInfo skillCastInfo  = new SkillCastInfo(a, b, skillObject, castMessages[i]);
                        sense.msgContents(new SkillCastMessage(skillCastInfo));
                    }else{
                        SkillCastInfo skillCastInfo  = new SkillCastInfo(a, b, skillObject, castMessages[0]);
                        sense.msgContents(new SkillCastMessage(skillCastInfo));
                    }
                    i++;
                }else{
                    service.shutdown();
                    a.autoCombatPause = false;
                    GameCharacterManager.saveCharacter(a);
                }
            }
        };
        service.scheduleAtFixedRate(runnable, 0, (int)gameSetting.getGlobalCD() * 500,
                TimeUnit.MILLISECONDS);
    }

}
