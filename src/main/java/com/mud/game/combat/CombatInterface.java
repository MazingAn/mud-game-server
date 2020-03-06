package com.mud.game.combat;

import com.mud.game.object.supertypeclass.CommonCharacter;

import java.util.ArrayList;

public interface CombatInterface {

    /*
    * 战斗接口，包含创建战斗，实时战斗，战斗结束评判和战利品划分
    * */

    default void init(CombatSense sense){
        /*
        * 初始化战斗
        * 创建一个战斗场景，把所有参与战斗的玩家加入场景
        * */

        // 修改红队成员身份
        for(CommonCharacter character : sense.getRedTeam()){
            FighterManager.joinCombat(character);
        }

        // 修改蓝队成员身份
        for(CommonCharacter character : sense.getBlueTeam()){
            FighterManager.joinCombat(character);
        }
    }

    default void startCombat(CombatSense sense){
        /*
        * 当玩家战斗的时候，角色开始自动攻击
        * */
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

    default boolean isCombatFinished(CombatSense sense, int minHp){
        /*
        * 检查战斗场景的状态，判断战斗是否结束,minHp是用来检测角色是否被打败的标准
        * */
        return sense.getAliveNumberInTeam(sense.getRedTeam(), minHp) == 0 ||
                sense.getAliveNumberInTeam(sense.getBlueTeam(), minHp) == 0;
    }

    default void onCombatFinish(CombatSense sense){
        /*
        *  战斗结束的时候所做的处理
        * */
        // 奖励成功的队伍

    }

    default void lootSpoils(CombatSense sense) {
        /*
        * 赢得队伍获取战利品
        * */
    }

}
