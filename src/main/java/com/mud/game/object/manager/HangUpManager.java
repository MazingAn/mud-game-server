package com.mud.game.object.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.messages.MsgMessage;
import com.mud.game.messages.PlayerCharacterStateMessage;
import com.mud.game.messages.ToastMessage;
import com.mud.game.net.session.GameSessionService;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldRoomObject;
import com.mud.game.structs.CharacterState;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.resultutils.GameWords;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.yeauty.pojo.Session;

public class HangUpManager {

    public static Runnable start(PlayerCharacter playerCharacter, CharacterState currentAction, Session session)  {
        /*
        * @ 玩家挖矿/钓鱼/采药三种挂机
        * @ 挖矿有一定概率获得宝石，也会随机获得潜能和经验
        * */

        if(!commonHangUpCheck(playerCharacter, currentAction, session)){
            return null;
        }else{
            playerCharacter.setState(currentAction);
            MongoMapper.playerCharacterRepository.save(playerCharacter);
            session.sendText(JsonResponse.JsonStringResponse(new PlayerCharacterStateMessage(playerCharacter.getState())));// 挖矿进行时的runnable
            Runnable runnable = new Runnable() {
                // run方法将周期性运行
                @Override
                public void run() {
                    if(playerCharacter.getTili() <= 0){ //没有体力则不能继续挂机
                        playerHasNoTili(playerCharacter, session);
                    }else{
                        playerHangUp(playerCharacter, currentAction);
                    }
                }
            };
            return runnable;
        }
    }

    private static void playerHangUp(PlayerCharacter playerCharacter, CharacterState currentAction) {
        try{
            Session updatedSession = GameSessionService.getSessionByCallerId(playerCharacter.getId());
            // TODO:发送随机的句子
            //sendRandomNotify(currentAction, updatedSession);
            // TODO:获得随机的物品
            //getRandomObject(playerCharacter, currentAction, updatedSession);
            // 增加随机的经验和潜能
            int addedValue = addRandomPotential(playerCharacter);
            updatedSession.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.PLAYER_GET_RANDOM_POTENTIAL, addedValue, addedValue))));
            PlayerCharacterManager.showStatus(playerCharacter);
        }catch (Exception e){
            System.out.println("在玩家挂机的时候发生错误，挂机人：" + playerCharacter.getName() + ";  挂机操作：" + currentAction);
        }

    }

    private static void sendRandomNotify(CharacterState currentAction, Session session){
        /*
        * TODO：随机的句子在数据库中配置，游戏启动的时候统一查询（放置在内存中）
        * */
    }

    private static void getRandomObject(PlayerCharacter playerCharacter, CharacterState currentAction, Session session){
        /*
        * TODO：
        *   @ 根据当前挂机的行动，随机奖励挂机可以得到的物品
        *   @ 挂机奖励物品在数据库中查询（游戏服务器启动的时候放入内存），每件物品都有对应的奖励概率
        *   @ 挂机奖励的概率根据玩家的福缘值和对应生活技能（挖矿，采药，钓鱼）等级来决定，总体奖励的概率控制在0.2之内
        * */
    }

    private static void playerHasNoTili(PlayerCharacter playerCharacter, Session session)  {
        /*
        * @ 当玩家没有体力的时候 停止挂机 并发送提示信息
        * */
        Session updatedSession = GameSessionService.getSessionByCallerId(playerCharacter.getId());
        if (updatedSession != null){
            session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(GameWords.NO_ENOUGH_TILI)));
            PlayerScheduleManager.shutdownExecutorByCallerId(playerCharacter.getId());
        }
    }

    private static boolean commonHangUpCheck(PlayerCharacter playerCharacter, CharacterState currentAction, Session session)  {
        /*
         * 通用的挂机前检查， 检查玩家是否能够开始挂机
         * @ 一、 玩家不能是死亡状态
         * @ 二、 玩家不能是正在干其他挂机事务
         * @ 三、 玩家所在的房间必须支持玩家进行此类操作
         * */

        // 玩家是否死亡
        if(playerCharacter.getHp() <= 0){
            session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(GameWords.PLAYER_DIED)));
            return false;
        }

        // 玩家是不是正在干别的事
        if(playerCharacter.getState() != CharacterState.STATE_NORMAL){
            if(playerCharacter.getState() != currentAction){
                // 玩家在干别的事
                session.sendText(JsonResponse.JsonStringResponse( String.format(GameWords.PLAYER_DO_OTHER_THING,
                        PlayerCharacterState2DescriptionString(playerCharacter.getState()),
                        PlayerCharacterState2DescriptionString(currentAction))));
            }else{
                // 玩家已经再干这件事了
                session.sendText(JsonResponse.JsonStringResponse( String.format(GameWords.ALREADY_DOING,
                        PlayerCharacterState2DescriptionString(playerCharacter.getState()))));
            }
            return false;
        }

        // 检查玩家是否有对应的工具
        if(!playerHasTool(playerCharacter, currentAction, session)) {
            return false;
        }

        // 玩家所在的房间支不支持玩家进行对应操作
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(playerCharacter.getLocation());
        if(!room.getHangUpCommand().equals(PlayerCharacterState2CommandString(currentAction))){
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.HANGUP_PLACE_ERROR,
                    PlayerCharacterState2DescriptionString(currentAction)))));
            return false;
        }

        return true;
    }

    private static boolean playerHasTool(PlayerCharacter playerCharacter, CharacterState currentAction, Session session) {
        /*
        * 检查玩家是否能装备了挂机所需要的工具
        * */
        // TODO：玩家装备检查
        return true;
    }

    private static int addRandomPotential(PlayerCharacter playerCharacter){
        /*
        * 为玩家增加随机的经验和潜能
        * */
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

    private static String PlayerCharacterState2DescriptionString(CharacterState state) {
        /*
        * 玩家状态转换为描述（中文）字符串
        * */
        switch (state){
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

    private static String PlayerCharacterState2CommandString(CharacterState state) {
        /*
         * 玩家状态转换为描述（中文）字符串
         * */
        switch (state){
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

}
