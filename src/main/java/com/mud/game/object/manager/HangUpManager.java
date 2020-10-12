package com.mud.game.object.manager;

import com.mud.game.messages.MsgMessage;
import com.mud.game.messages.PlayerCharacterStateMessage;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldRoomObject;
import com.mud.game.structs.CharacterState;
import com.mud.game.utils.resultutils.GameWords;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.HangupObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.*;

public class HangUpManager {
    /**
     * 挖矿挂机奖励
     */
    public static List<HangupObject> STATE_MINING_LIST = new ArrayList<>();
    /**
     * 钓鱼挂机奖励
     */
    public static List<HangupObject> STATE_FISHING_LIST = new ArrayList<>();
    /**
     * 采药挂机奖励
     */
    public static List<HangupObject> STATE_COLLECT_LIST = new ArrayList<>();

    public static void iniRandomObjectList() {
        STATE_MINING_LIST = DbMapper.hangupObjectRepository.findHangupObjectByHangupType("mining");
        STATE_COLLECT_LIST = DbMapper.hangupObjectRepository.findHangupObjectByHangupType("collect");
        STATE_FISHING_LIST = DbMapper.hangupObjectRepository.findHangupObjectByHangupType("fishing");
    }

    /**
     * 玩家开始挂机
     *
     * @param playerCharacter 挂机主体
     * @param currentAction   当亲挂机状态
     * @param addProbability
     * @return Runnable 挂机执行主体
     */
    public static Runnable start(PlayerCharacter playerCharacter, CharacterState currentAction, double addProbability) {
        if (!commonHangUpCheck(playerCharacter, currentAction)) {
            return null;
        } else {
            playerCharacter.setState(currentAction);
            MongoMapper.playerCharacterRepository.save(playerCharacter);
            sendStartMessage(playerCharacter);
            List<HangupObject> hangupObjectList = new ArrayList<>();
            switch (currentAction) {
                case STATE_FISHING:
                    //钓鱼
                    hangupObjectList = STATE_FISHING_LIST;
                    break;
                case STATE_MINING:
                    //挖矿
                    hangupObjectList = STATE_MINING_LIST;
                    break;
                case STATE_COLLECT:
                    //采药
                    hangupObjectList = STATE_COLLECT_LIST;
                    break;
                default:
                    //TODO 其他挂机类型
            }
            if (hangupObjectList.size() > 0 && addProbability != 0) {
                for (HangupObject hangupObject : hangupObjectList) {
                    hangupObject.setOdds((float) (hangupObject.getOdds() + addProbability));
                }
            }
            List<HangupObject> finalHangupObjectList = hangupObjectList;
            Runnable runnable = new Runnable() {
                // run方法将周期性运行
                @Override
                public void run() {
                    if (playerHasEnoughTili(playerCharacter)) {
                        playerHangUp(playerCharacter, currentAction, finalHangupObjectList);
                    }
                }
            };
            return runnable;
        }
    }

    /**
     * 发送挂机开始时候的消息
     *
     * @param playerCharacter 玩家角色
     */
    public static void sendStartMessage(PlayerCharacter playerCharacter) {
        switch (playerCharacter.getState()) {
            case STATE_MINING:
                playerCharacter.msg(new MsgMessage(GameWords.START_MINING));
                break;
            case STATE_COLLECT:
                playerCharacter.msg(new MsgMessage(GameWords.START_COLLECT));
                break;
            case STATE_CURE:
                playerCharacter.msg(new MsgMessage(GameWords.START_CURE));
                break;
            case STATE_FISHING:
                playerCharacter.msg(new MsgMessage(GameWords.START_FISHING));
                break;
            case STATE_MEDITATE:
                playerCharacter.msg(new MsgMessage(GameWords.START_MEDITATE));
                break;
            case STATE_LEARN_SKILL:
                playerCharacter.msg(new MsgMessage(GameWords.START_LEARN_SKILL));
                break;
            default:
                break;
        }
        playerCharacter.msg(new PlayerCharacterStateMessage(playerCharacter.getState()));
    }

    /**
     * 玩家挂机的具体操作
     */
    private static void playerHangUp(PlayerCharacter playerCharacter, CharacterState currentAction, List<HangupObject> hangupObjectList) {
        try {
            if (currentAction.equals(CharacterState.STATE_CURE)) {//处理玩家疗伤
                onPlayerCure(playerCharacter);
            } else if (currentAction.equals(CharacterState.STATE_MEDITATE)) {
                onPlayerMeditate(playerCharacter);
            } else {//其他类型挂机逻辑
                // TODO:发送随机的句子
                sendRandomNotify(playerCharacter, currentAction);
                if (hangupObjectList.size() > 0) {
                    // getRandomObject(playerCharacter, currentAction, hangupObjectList);
                    getRandomObjectReward(playerCharacter, hangupObjectList);
                }
                int addedValue = addRandomPotential(playerCharacter);
                playerCharacter.msg(new ToastMessage(String.format(GameWords.PLAYER_GET_RANDOM_POTENTIAL, addedValue, addedValue)));
            }
            PlayerCharacterManager.showStatus(playerCharacter);
        } catch (Exception e) {
            System.out.println("在玩家挂机的时候发生错误，挂机人：" + playerCharacter.getName() + ";  挂机操作：" + currentAction);
        }
    }

    /**
     * 玩家疗伤时候的处理
     *
     * @param playerCharacter 玩家
     */
    private static void onPlayerCure(PlayerCharacter playerCharacter) {
        int recoveredHp = (int) (playerCharacter.getMax_hp() * 0.1);
        int recoveredMaxHp = (int) (playerCharacter.getLimit_hp() * 0.1);
        if (recoveredHp > 0) {
            GameCharacterManager.changeStatus(playerCharacter, "hp", recoveredHp);
            playerCharacter.msg(new ToastMessage(String.format(GameWords.PLAYER_RECOVER_HP, recoveredHp)));
        }
        if (recoveredMaxHp > 0) {
            GameCharacterManager.changeStatus(playerCharacter, "max_hp", recoveredMaxHp);
            playerCharacter.msg(new ToastMessage(String.format(GameWords.PLAYER_RECOVER_MAX_HP, recoveredMaxHp)));
        }
        playerCharacter = MongoMapper.playerCharacterRepository.findPlayerCharacterById(playerCharacter.getId());
        if (playerCharacter.getMax_hp() >= playerCharacter.getLimit_hp()) {
            playerCharacter.setMax_hp(playerCharacter.getLimit_hp());
        }
        if (playerCharacter.getHp() >= playerCharacter.getMax_hp()) {
            playerCharacter.setHp(playerCharacter.getMax_hp());
        }
        GameCharacterManager.saveCharacter(playerCharacter);
        if (playerCharacter.getHp() == playerCharacter.getMax_hp() &&
                playerCharacter.getMax_hp() == playerCharacter.getLimit_hp()) {
            PlayerScheduleManager.shutdownExecutorByCallerId(playerCharacter.getId());
            playerCharacter.msg(new MsgMessage(GameWords.PLAYER_CURE_END));
        }
    }

    /**
     * 玩家打坐时候的处理
     *
     * @param playerCharacter 玩家
     */
    private static void onPlayerMeditate(PlayerCharacter playerCharacter) {
        int recoveredMp = (int) (playerCharacter.getMax_mp() * 0.1);
        int recoveredMaxMp = (int) (playerCharacter.getLimit_mp() * 0.1);
        if (recoveredMp > 0) {
            GameCharacterManager.changeStatus(playerCharacter, "mp", recoveredMp);
            playerCharacter.msg(new ToastMessage(String.format(GameWords.PLAYER_RECOVER_MP, recoveredMp)));
        }
        if (recoveredMaxMp > 0) {
            GameCharacterManager.changeStatus(playerCharacter, "max_hp", recoveredMaxMp);
            playerCharacter.msg(new ToastMessage(String.format(GameWords.PLAYER_RECOVER_MAX_MP, recoveredMaxMp)));
        }
        if (playerCharacter.getMax_mp() >= playerCharacter.getLimit_mp()) {
            playerCharacter.setMax_mp(playerCharacter.getLimit_mp());
        }
        if (playerCharacter.getMp() >= playerCharacter.getMax_mp()) {
            playerCharacter.setMp(playerCharacter.getMax_mp());
        }
        GameCharacterManager.saveCharacter(playerCharacter);
        if (playerCharacter.getMp() == playerCharacter.getMax_mp() &&
                playerCharacter.getMax_mp() == playerCharacter.getLimit_mp()) {
            PlayerScheduleManager.shutdownExecutorByCallerId(playerCharacter.getId());
            playerCharacter.msg(new MsgMessage(GameWords.PLAYER_MEDITATE_END));
        }
    }

    /**
     * 发送随机的提示信息
     *
     * @param playerCharacter 挂机的主体
     * @param currentAction   当前挂机的状态
     */
    private static void sendRandomNotify(PlayerCharacter playerCharacter, CharacterState currentAction) {
        /*
         * TODO：随机的句子在数据库中配置，游戏启动的时候统一查询（放置在内存中）
         * */
    }

    /**
     * 随机获取物品
     *
     * @param playerCharacter 挂机的主体
     * @param currentAction   当前挂机的状态
     */
    private static void getRandomObject(PlayerCharacter playerCharacter, CharacterState currentAction, List<HangupObject> hangupObjectList) {
        /*
         * TODO：
         *   @ 根据当前挂机的行动，随机奖励挂机可以得到的物品
         *   @ 挂机奖励物品在数据库中查询（游戏服务器启动的时候放入内存），每件物品都有对应的奖励概率
         *   @ 挂机奖励的概率根据玩家的福缘值和对应生活技能（挖矿，采药，钓鱼）等级来决定，总体奖励的概率控制在0.2之内
         * */
        switch (currentAction) {
            case STATE_FISHING:
                //钓鱼
                getRandomObjectReward(playerCharacter, hangupObjectList);
                break;
            case STATE_MINING:
                //挖矿
                getRandomObjectReward(playerCharacter, hangupObjectList);
                break;
            case STATE_COLLECT:
                //采药
                getRandomObjectReward(playerCharacter, hangupObjectList);
                break;
            default:
                //TODO 其他挂机类型
        }

    }

    /**
     * 获得挂机奖励
     *
     * @param playerCharacter
     * @param hangupObjectList
     */
    private static void getRandomObjectReward(PlayerCharacter playerCharacter, List<HangupObject> hangupObjectList) {
        int randomInterval = randomInterval(1, 100);
        float num = 0;
        float index = 0;
        for (HangupObject hangupObject : hangupObjectList) {
            float odds = hangupObject.getOdds() * 100;
            index = index + odds;
            if (num < randomInterval && randomInterval <= index) {
                PlayerCharacterManager.receiveObjectToBagpack(playerCharacter, hangupObject.getTarget(), 1);
                return;
            }
            num = index;
        }
    }

    //生成区间内随机数
    private static int randomInterval(int levelMinLevel, int levelMaxLevel) {
        Random random = new Random();
        return random.nextInt(levelMaxLevel - levelMinLevel) + levelMinLevel;
    }

    /**
     * 检查玩家是否还有足够的体力
     *
     * @param playerCharacter 挂机主体
     * @return boolean 是否有足够体力
     */
    private static boolean playerHasEnoughTili(PlayerCharacter playerCharacter) {
        if (playerCharacter.getTili() <= 0) {
            playerCharacter.msg(new MsgMessage(GameWords.NO_ENOUGH_TILI));
            PlayerScheduleManager.shutdownExecutorByCallerId(playerCharacter.getId());
            return false;
        }
        return true;
    }

    /**
     * 通用的挂机前检查， 检查玩家是否能够开始挂机
     * 一、 玩家不能是死亡状态
     * 二、 玩家不能是正在干其他挂机事务
     * 三、 玩家所在的房间必须支持玩家进行此类操作
     *
     * @param playerCharacter 挂机的主体
     * @param currentAction   挂机动作
     * @return boolean 能否继续挂机
     */
    private static boolean commonHangUpCheck(PlayerCharacter playerCharacter, CharacterState currentAction) {
        // 玩家是否死亡
        if (playerCharacter.getHp() <= 0) {
            playerCharacter.msg(new MsgMessage(GameWords.PLAYER_DIED));
            return false;
        }

        // 玩家是否能进入疗伤状态
        if (CharacterState.STATE_CURE.equals(currentAction)) {
            if (playerCharacter.getHp() == playerCharacter.getMax_hp() &&
                    playerCharacter.getMax_hp() == playerCharacter.getLimit_hp()) {
                playerCharacter.msg(new MsgMessage(GameWords.NEED_NOT_CURE));
                return false;
            }
        }

        // 玩家能够进入打坐状态
        if (CharacterState.STATE_MEDITATE.equals(currentAction)) {
            if (playerCharacter.getMp() == playerCharacter.getMax_mp() &&
                    playerCharacter.getMax_mp() == playerCharacter.getLimit_mp()) {
                playerCharacter.msg(new MsgMessage(GameWords.NEED_NOT_MEDITATE));
                return false;
            }
        }

        // 玩家是不是正在干别的事
        if (playerCharacter.getState() != CharacterState.STATE_NORMAL) {
            if (playerCharacter.getState() != currentAction) {
                // 玩家在干别的事
                playerCharacter.msg(String.format(GameWords.PLAYER_DO_OTHER_THING,
                        PlayerCharacterState2DescriptionString(playerCharacter.getState()),
                        PlayerCharacterState2DescriptionString(currentAction)));
            } else {
                // 玩家已经再干这件事了
                playerCharacter.msg(String.format(GameWords.ALREADY_DOING,
                        PlayerCharacterState2DescriptionString(playerCharacter.getState())));
            }
            return false;
        }

        // 检查玩家是否有对应的工具
        if (!playerHasTool(playerCharacter, currentAction)) {
            return false;
        }

        // 玩家所在的房间支不支持玩家进行对应操作（疗伤与打坐除外）
        if (!CharacterState.STATE_CURE.equals(currentAction) && !CharacterState.STATE_MEDITATE.equals(currentAction)) {
            WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(playerCharacter.getLocation());
            if (!room.getHangUpCommand().equals(PlayerCharacterState2CommandString(currentAction))) {
                playerCharacter.msg(new ToastMessage(String.format(GameWords.HANGUP_PLACE_ERROR,
                        PlayerCharacterState2DescriptionString(currentAction))));
                return false;
            }
        }

        return true;
    }

    /**
     * 检查玩家是否能进入挂机状态
     *
     * @param playerCharacter 挂机主体
     * @param currentAction   当前的动作
     * @return boolean 能否挂机
     */
    private static boolean playerHasTool(PlayerCharacter playerCharacter, CharacterState currentAction) {
        // TODO：玩家装备检查

        return true;
    }

    /**
     * 为玩家增加随机的经验和潜能
     *
     * @param playerCharacter 挂机主体
     */
    private static int addRandomPotential(PlayerCharacter playerCharacter) {
        int potential = playerCharacter.getPotential();
        int exp = playerCharacter.getExp();
        int addedPotential = 500 + (int) (Math.random() * 100);
        potential += addedPotential;
        exp += addedPotential;
        playerCharacter.setPotential(potential);
        playerCharacter.setExp(exp);
        MongoMapper.playerCharacterRepository.save(playerCharacter);
        return addedPotential;
    }

    /**
     * 玩家状态转换为描述（中文）字符串
     *
     * @param state 玩家状态
     * @return String 对应的中文解释
     */
    private static String PlayerCharacterState2DescriptionString(CharacterState state) {

        switch (state) {
            case STATE_MINING:
                return "挖矿";
            case STATE_BUSY:
                return "忙乱中";
            case STATE_CURE:
                return "疗伤";
            case STATE_COLLECT:
                return "采药";
            case STATE_DRIVING:
                return "驾驶";
            case STATE_FISHING:
                return "钓鱼";
            case STATE_LEARN_SKILL:
                return "学习技能";
            case STATE_MEDITATE:
                return "打坐";
            case STATE_PRACTICE_SKILL:
                return "练习技能";
            case STATE_VERTIGO:
                return "眩晕中";
            default:
                return "？？";
        }
    }

    /**
     * 玩家状态转换为挂机命令
     *
     * @param state 玩家挂机状态
     * @return String 估计命令的key
     */
    private static String PlayerCharacterState2CommandString(CharacterState state) {
        switch (state) {
            case STATE_MINING:
                return "mining";
            case STATE_CURE:
                return "cure";
            case STATE_COLLECT:
                return "collect";
            case STATE_FISHING:
                return "fishing";
            case STATE_MEDITATE:
                return "meditate";
            default:
                return "？？";
        }
    }

    /**
     * 根据生活技能等级、装备品级计算触发奖励概率加成
     *
     * @param level
     * @param quality
     * @return
     */
    public static Float getAddProbability(int level, int quality) {
        Float levelNum = 0.001f;
        Float qualityNum = 0.01f;
        return (level * levelNum) + ((quality - 1) * qualityNum);
    }
}
