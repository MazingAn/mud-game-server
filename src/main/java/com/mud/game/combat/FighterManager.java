package com.mud.game.combat;

import com.mud.game.handler.CombatHandler;
import com.mud.game.messages.JoinCombatMessage;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerScheduleManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.structs.CharacterState;
import com.mud.game.utils.collections.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.mud.game.server.ServerManager.gameSetting;


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
     * @param character 要进行自动攻击的角色
     */
    public static void startAutoCombat(CommonCharacter character) {
        // 设置对手，开始普通攻击
        String characterId = character.getId();
        CombatSense sense = CombatHandler.getCombatSense(characterId);
        ScheduledExecutorService service = PlayerScheduleManager.createOrGetExecutorServiceForCaller(characterId);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (!sense.isCombatFinished()) {
                    if (!character.autoCombatPause && character.getHp() > 0) {
                        CommonCharacter caller = GameCharacterManager.getCharacterObject(characterId);
                        CommonCharacter target = GameCharacterManager.getCharacterObject(character.getTarget());
                        if (!character.isCanCombat()) {
                            character.msg(new ToastMessage("你现在的状态，无法进行战斗！"));
                        } else {
                            //如果目标已死亡，重新选定目标
                            if (target.getHp() <= 0) {
                                target = FighterManager.setRandomTarget(character, sense.getBlueTeam());
                            }
                            GameCharacterManager.castSkill(caller, target, GameCharacterManager.getDefaultSkill(caller));
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
        int delay = (sense.getBlueTeam().contains(character)) ? 0 : (int) (gameSetting.getGlobalCD() * 1000 / 2);
        service.scheduleAtFixedRate(runnable, delay, (int) (gameSetting.getGlobalCD() * 1000), TimeUnit.MILLISECONDS);
    }

    public static void stopAutoCombat(CommonCharacter character) {
        ScheduledExecutorService service = PlayerScheduleManager.createOrGetExecutorServiceForCaller(character.getId());
        service.shutdown();
    }

}
