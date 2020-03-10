package com.mud.game.combat;

import com.mud.game.object.supertypeclass.CommonCharacter;

import java.util.ArrayList;

/**
 * 战斗场景类
 *
 * 战斗中提供蓝队blueTeam和红队readTeam两个队伍<br>
 * 负责管理战斗中的两支队伍的状态<br>
 * 后期可能的拓展：<br>
 * 战斗场景为战斗角色提供不同buffer等特色玩法的支持（如果没人问的话，最好不要说出来我们还预想了这类拓展接口）<br>
 *
 * @author 安明哲
 * @version 1.0
 * @since 1.0
 *
 * */
public class CombatSense {

    /** 红队战斗成员列表 */
    private ArrayList<CommonCharacter> redTeam;

    /** 蓝队战斗成员列表 */
    private ArrayList<CommonCharacter> blueTeam;

    /**
     * 构造函数
     *
     * @param redTeam  战斗一方的队伍（一个有角色对象组成的数组）
     * @param blueTeam 战斗一方的队伍（一个有角色对象组成的数组）
     *
     * */
    public CombatSense(ArrayList<CommonCharacter> redTeam, ArrayList<CommonCharacter> blueTeam) {
        this.redTeam = redTeam;
        this.blueTeam = blueTeam;
    }

    /**
     * 获得队伍中存活（还可以战斗）的角色的数量
     *
     * @param team 要检测的队伍
     * @param minHp 判断角色是否还能继续参加战斗的界限值  如果血量低于这个值 则不能被算作存活人员
     *
     * */
    public int getAliveNumberInTeam(ArrayList<CommonCharacter> team, int minHp) {
        int aliveNumber = 0;
        for(CommonCharacter character : team){
            if(character.getHp() <= minHp){
                aliveNumber ++;
            }
        }
        return aliveNumber;
    }


    public ArrayList<CommonCharacter> getRedTeam() {
        return redTeam;
    }

    public void setRedTeam(ArrayList<CommonCharacter> redTeam) {
        this.redTeam = redTeam;
    }

    public ArrayList<CommonCharacter> getBlueTeam() {
        return blueTeam;
    }

    public void setBlueTeam(ArrayList<CommonCharacter> blueTeam) {
        this.blueTeam = blueTeam;
    }
}
