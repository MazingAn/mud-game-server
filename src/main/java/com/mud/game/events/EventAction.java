package com.mud.game.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.messages.MsgMessage;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldRoomObject;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worlddata.db.models.*;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.yeauty.pojo.Session;

/**
 * 事件的触发类， 内部定义了所有游戏中的事件和触发机制
 * */
public class EventAction {

    /**
     * 玩家触发移动事件后对应的处理 （玩家会被移动到配置好的房间）
     * @param playerCharacter 触发事件的玩家
     * @param actionRecord 玩家触发移动事件的时候对应的配置记录 参考：{@link ActionMove}
     * @param session 玩家对应的session，事件执行过程中与客户端信息交互的通道
     * */
    public static void actionMove(PlayerCharacter playerCharacter, ActionMove actionRecord, Session session) {
        WorldRoomObject targetRoom = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(actionRecord.getRoomKey());
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(actionRecord.getDescription())));
        // 强制更新客户端地图
        PlayerCharacterManager.revealMap(playerCharacter, targetRoom , true);
        // 一定玩家到新的地图
        PlayerCharacterManager.moveTo(playerCharacter, actionRecord.getRoomKey(), session);
    }


    /**
     * 玩家触发攻击事件后对应的处理（会有一个NPC跳出来攻击玩家）
     * @param playerCharacter 触发事件的玩家
     * @param actionRecord 玩家触发攻击事件的时候对应的配置记录 参考：{@link ActionAttack}
     * @param session 玩家对应的session，事件执行过程中与客户端信息交互的通道
     * */
    public static void actionAttack(PlayerCharacter playerCharacter, ActionAttack actionRecord, Session session) {
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("触发事件：攻击")));
    }

    /**
     * 玩家触发接受事件后对应的处理（玩家会接到一个任务）
     * @param playerCharacter 触发事件的玩家
     * @param actionRecord 玩家触发接受任务事件的时候对应的配置记录 参考：{@link ActionAcceptQuest}
     * @param session 玩家对应的session，事件执行过程中与客户端信息交互的通道
     * */
    public static void actionAcceptQuest(PlayerCharacter playerCharacter, ActionAcceptQuest actionRecord, Session session)  {
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("触发事件：接受任务")));
    }

    /**
     * 玩家触发学习技能事件对应的处理（玩家会开始学习技能）
     * @param playerCharacter 触发事件的玩家
     * @param actionRecord 玩家触发事件的时候对应的配置记录 参考：{@link ActionLearnSkill}
     * @param session 玩家对应的session，事件执行过程中与客户端信息交互的通道
     * */
    public static void actionLearnSkill(PlayerCharacter playerCharacter, ActionLearnSkill actionRecord, Session session)  {
        /*
         * 触发
         * */
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("触发事件：学习技能")));
    }

    /**
     * 玩家触发关闭事件对应的处理（玩家已触发的某一个事件会被关闭）
     * @param playerCharacter 触发事件的玩家
     * @param actionRecord 玩家触发事件的时候对应的配置记录 参考：{@link ActionCloseEvent}
     * @param session 玩家对应的session，事件执行过程中与客户端信息交互的通道
     * */
    public static void actionCloseEvent(PlayerCharacter playerCharacter, ActionCloseEvent actionRecord, Session session)  {
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("触发事件：关闭事件")));
    }

    /**
     * 玩家触发对话事件对应的处理（玩家会进入一段对话）
     * @param playerCharacter 触发事件的玩家
     * @param actionRecord 玩家触发事件的时候对应的配置记录 参考：{@link ActionDialogue}
     * @param session 玩家对应的session，事件执行过程中与客户端信息交互的通道
     * */
    public static void actionDialogue(PlayerCharacter playerCharacter, ActionDialogue actionRecord, Session session)  {
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("触发事件：对话")));
    }

    /**
     * 玩家触发提交任务事件对应的处理（会自动帮玩家提交一个任务）
     * @param playerCharacter 触发事件的玩家
     * @param actionRecord 玩家触发事件的时候对应的配置记录 参考：{@link ActionTurnInQuest}
     * @param session 玩家对应的session，事件执行过程中与客户端信息交互的通道
     * */
    public static void actionTurnInQuest(PlayerCharacter playerCharacter, ActionTurnInQuest actionRecord, Session session)  {
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("触发事件：提交任务")));
    }

    /**
     * 玩家触发触发高度改变事件对应的处理（玩家在当前房间的高度或者视野会发生改变）
     * @param playerCharacter 触发事件的玩家
     * @param actionRecord 玩家触发事件的时候对应的配置记录 参考：{@link ActionChangeStep}
     * @param session 玩家对应的session，事件执行过程中与客户端信息交互的通道
     * */
    public static void actionChangeStep(PlayerCharacter playerCharacter, ActionChangeStep actionRecord, Session session)  {
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("触发事件：更改step")));
    }

    /**
     * 玩家触发触发属性改变事件对应的处理（玩家的属性会被修改）
     * @param playerCharacter 触发事件的玩家
     * @param actionRecord 玩家触发事件的时候对应的配置记录 参考：{@link ActionChangeAttr}
     * @param session 玩家对应的session，事件执行过程中与客户端信息交互的通道
     *
     * */
    public static void actionChangeAttr(PlayerCharacter playerCharacter, ActionChangeAttr actionRecord, Session session){
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("触发事件：更改Attr")));
    }

    /**
    *   私有方法：随机返回true false
     * @param odds 概率控制数值  介于0和1之间才有效  数值越大返回true的概率越大
    * */
    private boolean canAction(float odds) {
        //事件概率计算
        return Math.random() < odds;
    }



}
