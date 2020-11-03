package com.mud.game.combat;

import com.mud.game.handler.AutoContestHandler;
import com.mud.game.handler.CombatHandler;
import com.mud.game.handler.GraduationHandler;
import com.mud.game.handler.NpcCombatHandler;
import com.mud.game.messages.CombatFinishMessage;
import com.mud.game.messages.MsgMessage;
import com.mud.game.messages.ObjectMoveOutMessage;
import com.mud.game.messages.RebornCommandsMessage;
import com.mud.game.object.manager.*;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.object.typeclass.WorldRoomObject;
import com.mud.game.structs.CharacterState;
import com.mud.game.structs.ObjectMoveInfo;
import com.mud.game.structs.SimpleCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.mud.game.constant.Constant.CONTEST_MIN_HP_COEFFICIENT;

/**
 * 战斗场景类
 * <p>
 * 战斗中提供蓝队blueTeam和红队readTeam两个队伍<br>
 * 负责管理战斗中的两支队伍的状态<br>
 * 后期可能的拓展：<br>
 * 战斗场景为战斗角色提供不同buffer等特色玩法的支持（如果没人问的话，最好不要说出来我们还预想了这类拓展接口）<br>
 *
 * @author 安明哲
 * @version 1.0
 * @since 1.0
 */
public class CombatSense {

    /**
     * 红队战斗成员列表
     */
    private ArrayList<CommonCharacter> redTeam;

    /**
     * 蓝队战斗成员列表
     */
    private ArrayList<CommonCharacter> blueTeam;

    /**
     * 获胜方颜色
     */
    private String winner;

    /**
     * 战斗结束的血量条件
     */
    private int minHp;

    /**
     * 构造函数
     *
     * @param redTeam  战斗一方的队伍（一个有角色对象组成的数组）
     * @param blueTeam 战斗一方的队伍（一个有角色对象组成的数组）
     */
    public CombatSense(ArrayList<CommonCharacter> redTeam,
                       ArrayList<CommonCharacter> blueTeam,
                       int minHp) {
        this.redTeam = redTeam;
        this.blueTeam = blueTeam;
        this.minHp = minHp;
    }

    /**
     * 获得队伍中存活（还可以战斗）的角色的数量
     *
     * @param team  要检测的队伍
     * @param minHp 判断角色是否还能继续参加战斗的界限值  如果血量低于这个值 则不能被算作存活人员
     * @return int 被检测队伍的存活（可继续战斗）人数
     */
    public int getAliveNumberInTeam(ArrayList<CommonCharacter> team, int minHp) {
        int aliveNumber = 0;
        double minHpNew = minHp;
        for (CommonCharacter character : team) {
            //内存读取对象信息或从库里查询
            character = GameCharacterManager.getCharacterObject(character.getId());
            if (minHp == -1) {
                minHpNew = character.getMax_hp() * CONTEST_MIN_HP_COEFFICIENT;
            }
            if (character.getHp() <= minHpNew) {
                aliveNumber++;
            }
        }
        return aliveNumber;
    }

    /**
     * 给每个战斗成员发送消息
     */
    public void msgContents(Object messageObject) {
        for (CommonCharacter character : redTeam) {
            character.msg(messageObject);
        }
        for (CommonCharacter character : blueTeam) {
            character.msg(messageObject);
        }
    }

    /**
     * 检查战斗场景的状态，判断战斗是否结束
     *
     * @return boolean 战斗是否可以结束
     */
    public boolean isCombatFinished() {
        if (getAliveNumberInTeam(redTeam, minHp) == redTeam.size()) {
            winner = "blue";
            return true;
        }
        if (getAliveNumberInTeam(blueTeam, minHp) == blueTeam.size()) {
            winner = "red";
            return true;
        }
        return false;
    }

    /**
     * 战斗结束的时候所做的处理
     */
    public void onCombatFinish() {
        // 结束战斗
        if (winner.equals("red")) {
            for (CommonCharacter character : redTeam) {
                PlayerScheduleManager.shutdownExecutorByCallerId(character.getId());
                checkDied(character, redTeam, getMinHp());
                checkGraduation(character, blueTeam);
                initializeNpc(character);
            }
            for (CommonCharacter character : blueTeam) {
                PlayerScheduleManager.shutdownExecutorByCallerId(character.getId());
                checkDied(character, redTeam, getMinHp());
                initializeNpc(character);
            }
        } else {
            for (CommonCharacter character : redTeam) {
                PlayerScheduleManager.shutdownExecutorByCallerId(character.getId());
                checkDied(character, redTeam, getMinHp());
                initializeNpc(character);
            }
            for (CommonCharacter character : blueTeam) {
                PlayerScheduleManager.shutdownExecutorByCallerId(character.getId());
                checkDied(character, redTeam, getMinHp());
                checkGraduation(character, redTeam);
                initializeNpc(character);
            }
        }
    }

    /**
     * 赢得队伍获取战利品
     *
     * @param sense 战斗中的战斗场景 参见 {@link CombatSense}
     */
    public void lootSpoils(CombatSense sense) {

    }

    /**
     * npc初始化
     * 玩家攻击npc战斗结束后判断该npc是否死亡以及是否还在战斗中，若否则初始化npc状态
     *
     * @param character npc对象
     */
    public void initializeNpc(CommonCharacter character) {
        character = GameCharacterManager.getCharacterObject(character.getId());
        if (character.getHp() > minHp && character instanceof WorldNpcObject && (NpcCombatHandler.getNpcCombatSense(character.getId()) == null || NpcCombatHandler.getNpcCombatSense(character.getId()).size() == 0)) {
//            if (character.getLimit_hp() == 0) character.setLimit_hp(200);
//            if (character.getLimit_mp() == 0) character.setLimit_mp(200);
//            character.setMax_hp(character.getLimit_hp());
//            character.setHp(character.getMax_hp());
//            character.setMax_mp(character.getLimit_mp());
//            character.setMp(character.getMp());

            //清空状态
            //  character.setBuffers(new HashMap<>());
            //GameCharacterManager.saveCharacter(character);
//            Runnable runnable = HangUpManager.start(caller, CharacterState.STATE_CURE,0);
//            if(runnable != null){
//                ScheduledExecutorService service = PlayerScheduleManager.createOrGetExecutorServiceForCaller(caller.getId());
//                service.scheduleAtFixedRate(runnable, 0, 3000, TimeUnit.MILLISECONDS);
//            }
            // 开始疗伤挂机
            Runnable runnable = onPlayerCure(character);
            // 疗伤
            WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(character.getLocation());
            //房间内提示
            WorldRoomObjectManager.broadcast(room, "{g" + character.getName() + "开始疗伤!{n", character.getId());
            //修改状态
            character.setState(CharacterState.STATE_CURE);
            GameCharacterManager.saveCharacter(character);
            if (runnable != null) {
                ScheduledExecutorService service = PlayerScheduleManager.createOrGetExecutorServiceForCaller(character.getId());
                service.scheduleAtFixedRate(runnable, 0, 10000, TimeUnit.MILLISECONDS);
            }

        }
    }

    private Runnable onPlayerCure(CommonCharacter character) {
        Runnable runnable = new Runnable() {
            // run方法将周期性运行
            @Override
            public void run() {
                HangUpManager.onPlayerCure(character);
            }
        };
        return runnable;
    }

    /**
     * 检测玩家是否死亡，如果死亡发送复活命令
     */
    private void checkDied(CommonCharacter character, ArrayList<CommonCharacter> team, int minHp) {
        AutoContestHandler.removeCommonCharacter(character.getId() + character.getTarget());
        AutoContestHandler.removeCommonCharacter(character.getTarget() + character.getId());
        double minHpNew = minHp;
        if (minHp == -1) {
            minHpNew = character.getMax_hp() * CONTEST_MIN_HP_COEFFICIENT;
        }
        if (character instanceof PlayerCharacter) {
            character = GameCharacterManager.getCharacterObject(character.getId());
            if (character.getHp() <= minHpNew) {
                if (character.getHp() <= 0) {
                    character.msg(new CombatFinishMessage("death"));
                    character.msg(new MsgMessage("你已经死了！"));
                    GameCharacterManager.die(character);
                } else {
                    character.msg(new CombatFinishMessage("fail"));
                    character.msg(new MsgMessage("你已经输了！"));
                }
                character.msg(new RebornCommandsMessage((PlayerCharacter) character));
            } else {
                character.msg(new CombatFinishMessage("victory"));
            }
            CombatHandler.removeCombatSense(character.getId());
            PlayerCharacterManager.showStatus((PlayerCharacter) character);
        } else {
            for (int i = 0; i < team.size(); i++) {
                NpcCombatHandler.removeNpcCombatSense(character.getId(), team.get(i).getId());
            }
        }
    }

    /**
     * 检测玩家是否出师
     */
    private void checkGraduation(CommonCharacter character, ArrayList<CommonCharacter> team) {
        //出师判断
        if (GraduationHandler.existsGraduationList(character.getId()) && character instanceof PlayerCharacter) {
            //判断敌对阵营里是否有师傅
            PlayerCharacter playerCharacter = MongoMapper.playerCharacterRepository.findPlayerCharacterById(character.getId());
            for (CommonCharacter commonCharacter : team) {
                if (commonCharacter.getDataKey().equals(playerCharacter.getTeacher())) {
                    playerCharacter.setTeacher("");
                    MongoMapper.playerCharacterRepository.save(playerCharacter);
                }
            }
            GraduationHandler.removeGraduationList(character.getId());
        }
    }

    /**
     * GETTER SETTER 方法
     */
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

    public int getMinHp() {
        return minHp;
    }

    public void setMinHp(int minHp) {
        this.minHp = minHp;
    }
}
