package com.mud.game.combat;

import com.mud.game.object.supertypeclass.CommonCharacter;

/**
 * 接口 {@code CombatInterface} 战斗接口
 *
 *   <p>
 *  战斗的接口提供了一个基本的模式：<br>
 *  1.在战斗开始之前要加载战斗场景<br>
 *  2.并在战斗场景中拉入玩家（被分为不同的两支队伍，以便与后期拓展）<br>
 *  3.开始战斗<br>
 *  4.判断战斗是否能够结束<br>
 *  5.战斗之后的操作<br>
 *  6.奖励获胜方<br>
 *   </p>
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
     *
     * */
    default void init(CombatSense sense){
        // 修改红队成员身份
        for(CommonCharacter character : sense.getRedTeam()){
            FighterManager.joinCombat(character);
        }

        // 修改蓝队成员身份
        for(CommonCharacter character : sense.getBlueTeam()){
            FighterManager.joinCombat(character);
        }
    }

    /**
     * 角色开始自动攻击,提供默认实现
     * 当玩家战斗的时候，角色可以自动进行攻击<br>
     *
     * @param sense 战斗中的战斗场景 参见 {@link CombatSense}
     *
     * */
    default void startCombat(CombatSense sense){
        // 给红队随机设置蓝队的对手
        for(CommonCharacter character : sense.getRedTeam()){
            FighterManager.setRandomTarget(character, sense.getBlueTeam());
            FighterManager.startAutoCombat(character);
        }
        // 给蓝队随机设置红队的对手
        for(CommonCharacter character : sense.getBlueTeam()){
            FighterManager.setRandomTarget(character, sense.getRedTeam());
            FighterManager.startAutoCombat(character);
        }
    }


    /**
     * 检查战斗场景的状态，判断战斗是否结束
     * @param  minHp  用来检测角色是否被打败的标准(当角色的血量低于这个值，则玩家判定失败)
     *                战斗的是否可以结束，则是队伍里所有人的血量低于这个值 则战斗可以结束
     * @param sense 战斗场景
     * @return boolean 战斗是否可以结束
     * */
    default boolean isCombatFinished(CombatSense sense, int minHp){
        return sense.getAliveNumberInTeam(sense.getRedTeam(), minHp) == 0 ||
                sense.getAliveNumberInTeam(sense.getBlueTeam(), minHp) == 0;
    }


    /**
     *  战斗结束的时候所做的处理
     * @param sense 战斗中的战斗场景 参见 {@link CombatSense}
     * */
    default void onCombatFinish(CombatSense sense){

        // 奖励成功的队伍

    }

    /**
     * 赢得队伍获取战利品
     * @param sense 战斗中的战斗场景 参见 {@link CombatSense}
     * */
    default void lootSpoils(CombatSense sense) {

    }

}
