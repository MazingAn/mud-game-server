package com.mud.game.combat;

import com.mud.game.messages.CombatFinishMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerScheduleManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;

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

    /** 获胜方颜色 */
    private String winner;

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
     * @return int 被检测队伍的存活（可继续战斗）人数
     * */
    public int getAliveNumberInTeam(ArrayList<CommonCharacter> team, int minHp) {
        int aliveNumber = 0;
        for(CommonCharacter character : team){
            character = GameCharacterManager.getCharacterObject(character.getId());
            if(character.getHp() <= minHp){
                aliveNumber ++;
            }
        }
        return aliveNumber;
    }

    /** 给每个战斗成员发送消息 */
    public void msgContents(Object messageObject){
        for(CommonCharacter character : redTeam){
            character.msg(messageObject);
        }
        for(CommonCharacter character : blueTeam){
            character.msg(messageObject);
        }
    }

    /**
     * 检查战斗场景的状态，判断战斗是否结束
     * @param  minHp  用来检测角色是否被打败的标准(当角色的血量低于这个值，则玩家判定失败)
     *                战斗的是否可以结束，则是队伍里所有人的血量低于这个值 则战斗可以结束
     * @return boolean 战斗是否可以结束
     * */
    public boolean isCombatFinished(int minHp){
        if(getAliveNumberInTeam(redTeam, minHp) != 0){
            winner = "blue";
            return true;
        }
        if(getAliveNumberInTeam(blueTeam, minHp) != 0){
            winner = "red";
            return true;
        }
        return false;
    }

    /**
     *  战斗结束的时候所做的处理
     * */
    public void onCombatFinish(){
        // 结束战斗
        if(winner.equals("red")){
            for(CommonCharacter character: redTeam){
                character.msg(new CombatFinishMessage(true));
                PlayerScheduleManager.shutdownExecutorByCallerId(character.getId());
                checkDied(character);
            }
            for(CommonCharacter character: blueTeam){
                character.msg(new CombatFinishMessage(false));
                PlayerScheduleManager.shutdownExecutorByCallerId(character.getId());
                checkDied(character);
            }
        }else{
            for(CommonCharacter character: redTeam){
                character.msg(new CombatFinishMessage(false));
                PlayerScheduleManager.shutdownExecutorByCallerId(character.getId());
                checkDied(character);
            }
            for(CommonCharacter character: blueTeam){
                character.msg(new CombatFinishMessage(true));
                PlayerScheduleManager.shutdownExecutorByCallerId(character.getId());
                checkDied(character);
            }
        }
    }

    /**
     * 赢得队伍获取战利品
     * @param sense 战斗中的战斗场景 参见 {@link CombatSense}
     * */
    public void lootSpoils(CombatSense sense) {

    }


    /**
     * 检测玩家是否死亡，如果死亡发送复活命令
     * */
    private void checkDied(CommonCharacter character){
        if(character.getHp() <= 0){
            GameCharacterManager.die(character);
        }
    }

    /** GETTER SETTER 方法 */
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

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
