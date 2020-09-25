package com.mud.game.combat;

import com.mud.game.messages.CombatInfoMessage;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.CharacterCombatStatus;
import com.mud.game.structs.CombatInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 接口 {@code CombatInterface} 战斗接口
 *
 * <p>
 * 战斗的接口提供了一个基本的模式：<br>
 * 1.在战斗开始之前要加载战斗场景<br>
 * 2.并在战斗场景中拉入玩家（被分为不同的两支队伍，以便与后期拓展）<br>
 * 3.开始战斗<br>
 * 4.判断战斗是否能够结束<br>
 * 5.战斗之后的操作<br>
 * 6.奖励获胜方<br>
 * </p>
 *
 * @author <a href="tel://17691116067">安明哲</a>
 * @version 1.0
 * @since 1.0
 */

public interface CombatInterface {

    /**
     * 初始化战斗,提供默认实现.
     * 传入一个战斗场景，让场景中的角色加入战斗 <br>
     * 加入战斗的操作请参见：{@link FighterManager}
     *
     * @param sense 战斗中的战斗场景，需要在战斗开始前创建 参见{@link CombatSense}
     */
    default void init(CombatSense sense) {
        // 修改红队成员身份
        for (CommonCharacter character : sense.getRedTeam()) {
            FighterManager.joinCombat(character);
        }

        // 修改蓝队成员身份
        for (CommonCharacter character : sense.getBlueTeam()) {
            FighterManager.joinCombat(character);
        }
    }

    /**
     * 角色开始自动攻击,提供默认实现
     * 当玩家战斗的时候，角色可以自动进行攻击<br>
     *
     * @param sense 战斗中的战斗场景 参见 {@link CombatSense}
     */
    default void startCombat(CombatSense sense) {
        // 获取战斗信息
        List<CharacterCombatStatus> characters = new ArrayList<>();
        for (CommonCharacter character : sense.getRedTeam()) {
            characters.add(new CharacterCombatStatus(character));
        }
        for (CommonCharacter character : sense.getBlueTeam()) {
            characters.add(new CharacterCombatStatus(character));
        }
        //  发送战斗信息
        sense.msgContents(new CombatInfoMessage(new CombatInfo(characters, "", 0)));

        // 给红队随机设置蓝队的对手
        for (CommonCharacter character : sense.getRedTeam()) {
            FighterManager.setRandomTarget(character, sense.getBlueTeam());
            FighterManager.startAutoCombat(character);
        }
        // 给蓝队随机设置红队的对手
        for (CommonCharacter character : sense.getBlueTeam()) {
            FighterManager.setRandomTarget(character, sense.getRedTeam());
            FighterManager.startAutoCombat(character);
        }
    }

    /**
     * 切磋
     * 角色开始自动攻击,提供默认实现
     * 当玩家战斗的时候，角色可以自动进行攻击<br>
     *
     * @param combatSense 战斗中的战斗场景 参见 {@link CombatSense}
     */
    default void startContest(PlayerCharacter playerCharacter, CommonCharacter targetObject, CombatSense combatSense) {
        // 获取战斗信息
        List<CharacterCombatStatus> characters = new ArrayList<>();
        characters.add(new CharacterCombatStatus(playerCharacter));
        characters.add(new CharacterCombatStatus(targetObject));
        //  发送战斗信息
        combatSense.msgContents(new CombatInfoMessage(new CombatInfo(characters, "", 0)));
        // 设置对手
        playerCharacter.setTarget(targetObject.getId());
        targetObject.setTarget(playerCharacter.getId());
        // 角色默认使用自动攻击的方式展开战斗
        FighterManager.startAutoContest(playerCharacter, targetObject);
        FighterManager.startAutoContest(targetObject, playerCharacter);
    }
}
