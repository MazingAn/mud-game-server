package com.mud.game.combat;

import com.mud.game.algorithm.AttackAlgorithm;
import com.mud.game.algorithm.HarmInfo;
import com.mud.game.handler.AutoContestHandler;
import com.mud.game.handler.CombatHandler;
import com.mud.game.handler.NpcCombatHandler;
import com.mud.game.handler.SkillCdHandler;
import com.mud.game.messages.JoinCombatMessage;
import com.mud.game.messages.SkillCastMessage;
import com.mud.game.messages.SkillCdMessage;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.HangUpManager;
import com.mud.game.object.manager.PlayerScheduleManager;
import com.mud.game.object.manager.SkillObjectManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.statements.skills.ZhuiJi;
import com.mud.game.structs.CharacterState;
import com.mud.game.structs.SkillCastInfo;
import com.mud.game.structs.SkillCdInfo;
import com.mud.game.utils.StateConstants;
import com.mud.game.utils.collections.ListUtils;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.mud.game.constant.Constant.CONTEST_MIN_HP_COEFFICIENT;
import static com.mud.game.server.ServerManager.gameSetting;
import static com.mud.game.utils.StateConstants.*;


/**
 * {@code FighterManager}
 * 当一个角色进入战斗的时候，FighterManager 实现了战斗中的具体操作 并把战斗结果发送给玩家
 */

public class FighterManager {

    /**
     * 把一个玩家放入战斗状态
     * 更改角色的 {@code state}属性为 {@code CharacterState.STATE_COMBAT}  玩家属性参见 {@link CommonCharacter} <br>
     * 通知客户端 玩家进入战斗 发送进入战斗的消息  消息结构 参见 {@link JoinCombatMessage}
     *
     * @param character 参与战斗的角色
     */
    public static void joinCombat(CommonCharacter character) {

        try {
            character.setState(CharacterState.STATE_COMBAT);
            character.msg(new JoinCombatMessage());
            GameCharacterManager.showCombatCommands(character);
        } catch (Exception e) {
            System.out.println("加入战斗失败");
        }
    }

    /**
     * 为队伍中的角色设置随机目标
     * <p>
     * 战斗开始初期两个队伍中的成员相互随机设定一个攻击目标
     *
     * @param character 要给设置目标的角色
     * @param targets   角色可选的目标列表
     */
    public static CommonCharacter setRandomTarget(CommonCharacter character, ArrayList<CommonCharacter> targets) {
        List<CommonCharacter> commonCharacterList = new ArrayList<>();
        for (CommonCharacter commonCharacter : targets) {
            commonCharacter = GameCharacterManager.getCharacterObject(commonCharacter.getId());
            if (commonCharacter.getHp() > 0) {
                commonCharacterList.add(commonCharacter);
            }
        }
        // 为角色随机设置对手
        CommonCharacter target = ListUtils.randomChoice(commonCharacterList);

        character.setTarget(target.getId());
        return target;
    }

    /**
     * 战斗开始后，角色默认使用自动攻击的方式展开战斗
     *
     * @param commonCharacter
     * @param character       要进行自动攻击的角色
     */
    public static void startAutoCombat(CommonCharacter character, CommonCharacter commonCharacter) {
        // 设置对手，开始普通攻击
        String characterId = character.getId();
        CombatSense sense = null;
        if (character instanceof WorldNpcObject) {
            sense = NpcCombatHandler.getNpcCombatSense(characterId, commonCharacter.getId());
        } else {
            sense = CombatHandler.getCombatSense(characterId);
        }
        ScheduledExecutorService service = PlayerScheduleManager.createOrGetExecutorServiceForCaller(characterId);
        CombatSense finalSense = sense;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                CommonCharacter caller = GameCharacterManager.getCharacterObject(characterId);
                CommonCharacter target = GameCharacterManager.getCharacterObject(character.getTarget());
                if (!finalSense.isCombatFinished()) {
                    double callerMinHp = finalSense.getMinHp();
                    double targetMinHp = finalSense.getMinHp();
                    if (finalSense.getMinHp() == -1) {
                        callerMinHp = caller.getMax_hp() * CONTEST_MIN_HP_COEFFICIENT;
                        targetMinHp = target.getMax_hp() * CONTEST_MIN_HP_COEFFICIENT;
                    }
                    //攻击之前的逻辑
                    beforeAttackLogic(caller, target);
                    if (!caller.autoCombatPause && caller.getHp() > callerMinHp && StateConstants.checkState(caller, CHECK_ATTACK_STATE)) {
                        if (!caller.isCanCombat()) {
                            caller.msg(new ToastMessage("你现在的状态，无法进行战斗！"));
                        } else {
                            //如果目标已死亡，重新选定目标
                            if (target.getHp() <= targetMinHp) {
                                target = FighterManager.setRandomTarget(character, finalSense.getBlueTeam());
                            }
                            GameCharacterManager.castSkill(caller, target, GameCharacterManager.getDefaultSkill(caller));
                            if (finalSense.isCombatFinished()) {
                                finalSense.onCombatFinish();
                            }
                            //攻击之后的逻辑
                            afterAttackLogic(caller, target);
                        }
                    }
                } else {
                    finalSense.onCombatFinish();
                }
            }
        };
        int delay = (sense.getBlueTeam().contains(character)) ? 0 : (int) (gameSetting.getGlobalCD() * 1000 / 2);
        service.scheduleAtFixedRate(runnable, delay, (int) (gameSetting.getGlobalCD() * 1000), TimeUnit.MILLISECONDS);
    }

    private static void beforeAttackLogic(CommonCharacter caller, CommonCharacter target) {
        //判断是否被降低攻速
        if (!StateConstants.checkState(caller, CHECK_ATTACK_SPEED_REDUCE_STATE)) {
            Robot r = null;
            try {
                r = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
            r.delay(1000);
        }
        //判断被攻击方是否能被攻击
        if (!StateConstants.checkState(caller, CHECK_ATTACK_BE_STATE)) {
            return;
        }
    }

    private static void afterAttackLogic(CommonCharacter caller, CommonCharacter target) {
        if (!StateConstants.checkState(caller, "追击")) {
            //百分之50几率追击
            if (HangUpManager.randomInterval(0, 1) == 1) {
                new ZhuiJi(caller, target, GameCharacterManager.getDefaultSkill(caller), "追击", null);
            }
        }
        if (!StateConstants.checkState(target, "反击") && StateConstants.checkState(target, CHECK_COUNTERATTACK_BE_STATE)) {
            //反击实现
            new ZhuiJi(target, caller, GameCharacterManager.getDefaultSkill(target), "反击", null);
        }
        if (!StateConstants.checkState(target, "九阳真炎")) {
            //受到攻击会反伤火焰伤害
            new ZhuiJi(target, caller, GameCharacterManager.getDefaultSkill(target), "九阳真炎", null);
        }
        List<String> STATE = new ArrayList<String>() {
            {
                add("不老长春");
            }
        };
        if (StateConstants.checkState(target, STATE) && Math.random() <= 0.2) {
            SkillObject skillObject = MongoMapper.skillObjectRepository.findSkillObjectByDataKeyAndOwner("skill_juezhao_bulaochangchun", target.getId());
            //增加一个不老长春buffer
            GameCharacterManager.addBuffer("不老长春", 10, 0, 1, true,
                    null, 2, target, skillObject, true, caller);
        }
        if (!StateConstants.checkState(caller, "化功")) {
            //化功实现 攻击造成伤害的同时减少敌人伤害200%内力
            int aAttack = Integer.parseInt(caller.getCustomerAttr().get("attack").get("value").toString());
            target = GameCharacterManager.getCharacterObject(target.getId());
            GameCharacterManager.changeStatus(target, "mp", new Double((aAttack * 2) > target.getMp() ? target.getMp() : (aAttack * 2)).intValue() * -1, caller);
        }
    }

    public static void stopAutoCombat(CommonCharacter character) {
        ScheduledExecutorService service = PlayerScheduleManager.createOrGetExecutorServiceForCaller(character.getId());
        service.shutdown();
    }

    /**
     * 战斗开始后，角色默认使用自动攻击的方式展开战斗
     *
     * @param playerCharacter 要进行自动攻击的角色
     * @param targetObject    要进行攻击的目标
     */
    public static void startAutoContest(CommonCharacter playerCharacter, CommonCharacter targetObject) {
        // 设置对手，开始普通攻击
        String characterId = playerCharacter.getId();
        CombatSense sense = CombatHandler.getCombatSense(characterId + targetObject.getId());
        ScheduledExecutorService service = PlayerScheduleManager.createOrGetExecutorServiceForCaller(characterId);

        //从内存中获取战斗人员信息
        Runnable runnable = new Runnable() {
            @Override
            public synchronized void run() {
                if (!sense.isCombatFinished()) {
                    CommonCharacter playerCharacterCommonCharacter = playerCharacter;
                    if (playerCharacter instanceof WorldNpcObject) {
                        playerCharacterCommonCharacter = AutoContestHandler.getCommonCharacter(playerCharacter.getId() + targetObject.getId());
                    }

                    CommonCharacter targetObjectCommonCharacter = targetObject;
                    if (targetObject instanceof WorldNpcObject) {
                        targetObjectCommonCharacter = AutoContestHandler.getCommonCharacter(targetObject.getId() + playerCharacter.getId());
                    }

                    if (!playerCharacterCommonCharacter.autoCombatPause && playerCharacterCommonCharacter.getHp() > 0) {
                        if (!playerCharacterCommonCharacter.isCanCombat()) {
                            playerCharacterCommonCharacter.msg(new ToastMessage("你现在的状态，无法进行战斗！"));
                        } else {
                            //如果目标已死亡，重新选定目标
                            if (targetObjectCommonCharacter.getHp() <= 0) {
                                targetObjectCommonCharacter = FighterManager.setRandomTarget(playerCharacterCommonCharacter, sense.getBlueTeam());
                            }
                            GameCharacterManager.castSkill(playerCharacterCommonCharacter, targetObjectCommonCharacter, GameCharacterManager.getDefaultSkill(playerCharacterCommonCharacter));
                            if (sense.isCombatFinished()) {
                                sense.onCombatFinish();
                            }
                        }
                    }


                } else {
                    sense.onCombatFinish();
                }
            }
        };
        int delay = (sense.getBlueTeam().contains(playerCharacter)) ? 0 : (int) (gameSetting.getGlobalCD() * 1000 / 2);
        service.scheduleAtFixedRate(runnable, delay, (int) (gameSetting.getGlobalCD() * 1000), TimeUnit.MILLISECONDS);
    }

    /**
     * 群攻
     *
     * @param caller
     * @param target
     * @param skillObject
     * @param addCoefficient 加成系数
     */
    public static void autoCombatAoeAttack(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, double addCoefficient) {
        // 设置对手，开始普通攻击
        String characterId = caller.getId();
        CombatSense sense = null;
        if (caller instanceof WorldNpcObject) {
            sense = NpcCombatHandler.getNpcCombatSense(characterId, target.getId());
        } else {
            sense = CombatHandler.getCombatSense(characterId);
        }
        CombatSense finalSense = sense;
        caller = GameCharacterManager.getCharacterObject(characterId);
        if (!finalSense.isCombatFinished()) {
            double callerMinHp = finalSense.getMinHp();
            double targetMinHp = finalSense.getMinHp();
            double coefficient = 1;
            if (!caller.autoCombatPause && caller.getHp() > callerMinHp && StateConstants.checkState(caller, CHECK_ATTACK_STATE)) {
                for (CommonCharacter commonCharacter : sense.getBlueTeam()) {
                    commonCharacter = GameCharacterManager.getCharacterObject(commonCharacter.getId());
                    if (finalSense.getMinHp() == -1) {
                        callerMinHp = caller.getMax_hp() * CONTEST_MIN_HP_COEFFICIENT;
                        targetMinHp = commonCharacter.getMax_hp() * CONTEST_MIN_HP_COEFFICIENT;
                    }
                    //如果目标已死亡，重新选定目标
                    if (commonCharacter.getHp() <= targetMinHp) {
                        commonCharacter = FighterManager.setRandomTarget(caller, finalSense.getBlueTeam());
                    } else {
                        //计算伤害
                        coefficient = coefficient + addCoefficient;
                        HarmInfo harmInfo = AttackAlgorithm.computeFinalHarm(caller, commonCharacter, skillObject, coefficient);
                        //应用伤害
                        GameCharacterManager.changeStatus(commonCharacter, "hp", harmInfo.finalHarm * -1, caller);
                        //判断是否吸血
                        if (!StateConstants.checkState(caller, CHECK_XUEMODAOFAXI_STATE)) {
                            double duration = 0.1;
                            //判断是否在 血海无边的状态下
                            if (StateConstants.checkState(caller, CHECK_XUEHAIWUBIAN_STATE)) {
                                duration = 0.15;
                            }
                            GameCharacterManager.changeStatus(caller, "hp", new Double(harmInfo.finalHarm * duration).intValue(), caller);
                        }
                        //构建战斗输出
                        String combatCastStr = SkillObjectManager.getCastMessage(caller, commonCharacter, skillObject, harmInfo);
                        SkillCastInfo skillCastInfo = new SkillCastInfo(caller, commonCharacter, skillObject, combatCastStr);
                        sense.msgContents(new SkillCastMessage(skillCastInfo));
                    }
                    if (finalSense.isCombatFinished()) {
                        finalSense.onCombatFinish();
                    }
                }

            }
        } else {
            finalSense.onCombatFinish();
        }
        //设置技能cd
        SkillCdHandler.addSkillCd(caller.getId() + skillObject.getDataKey(), new Date());
        //返回技能冷却时间
        if (skillObject.getCd() != 0) {
            float skillCd = Float.parseFloat(caller.getCustomerAttr().get("skill_cd").get("value").toString());
            sense.msgContents(new SkillCdMessage(new SkillCdInfo(skillObject.getCd() * skillCd, skillObject.getId(), skillObject.getDataKey())));
        }
    }

    /**
     * 单体攻击
     *
     * @param caller
     * @param target
     * @param skillObject
     * @param coefficient
     */
    public static void autoCombatAttack(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, float coefficient) {
        target = GameCharacterManager.getCharacterObject(target.getId());
        //构建战斗输出
        HarmInfo harmInfo = AttackAlgorithm.computeFinalHarm(caller, target, skillObject);
        harmInfo.finalHarm = coefficient;
        //应用伤害
        GameCharacterManager.changeStatus(target, "hp", new Double(harmInfo.finalHarm * -1).intValue(), caller);
        //判断是否吸血
        if (!StateConstants.checkState(caller, CHECK_XUEMODAOFAXI_STATE)) {
            caller = GameCharacterManager.getCharacterObject(caller.getId());
            double duration = 0.1;
            //判断是否在 血海无边的状态下
            if (StateConstants.checkState(caller, CHECK_XUEHAIWUBIAN_STATE)) {
                duration = 0.15;
            }
            GameCharacterManager.changeStatus(caller, "hp", new Double(harmInfo.finalHarm * duration).intValue(), caller);
        }
        //构建战斗输出
        String combatCastStr = SkillObjectManager.getCastMessage(caller, target, skillObject, harmInfo);
        SkillCastInfo skillCastInfo = new SkillCastInfo(caller, target, skillObject, combatCastStr);
        CombatSense sense = null;
        if (caller instanceof WorldNpcObject) {
            sense = NpcCombatHandler.getNpcCombatSense(caller.getId(), target.getId());
        } else {
            sense = CombatHandler.getCombatSense(caller.getId());
        }
        if (sense == null) {
            //切磋场景
            sense = CombatHandler.getCombatSense(caller.getId() + target.getId());
        }
        sense.msgContents(new SkillCastMessage(skillCastInfo));
        //设置技能cd
        SkillCdHandler.addSkillCd(caller.getId() + skillObject.getDataKey(), new Date());
        //返回技能冷却时间
        if (skillObject.getCd() != 0) {
            float skillCd = Float.parseFloat(caller.getCustomerAttr().get("skill_cd").get("value").toString());
            sense.msgContents(new SkillCdMessage(new SkillCdInfo(skillObject.getCd() * skillCd, skillObject.getId(), skillObject.getDataKey())));
        }
    }
}
