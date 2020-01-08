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

public class EventAction {
    public static void actionMove(PlayerCharacter playerCharacter, ActionMove actionRecord, Session session) throws JsonProcessingException, JSONException {
        /*
        * 移动事件触发
        * */
        WorldRoomObject targetRoom = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(actionRecord.getRoomKey());
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(actionRecord.getDescription())));
        // 强制更新客户端地图
        PlayerCharacterManager.revealMap(playerCharacter, targetRoom , session, true);
        // 一定玩家到新的地图
        PlayerCharacterManager.moveTo(playerCharacter, actionRecord.getRoomKey(), session);
    }

    public static void actionAttack(PlayerCharacter playerCharacter, ActionAttack actionRecord, Session session) throws JsonProcessingException, JSONException {
        /*
        * 攻击事件触发
        * */
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("触发事件：攻击")));
    }

    public static void actionAcceptQuest(PlayerCharacter playerCharacter, ActionAcceptQuest actionRecord, Session session) throws JsonProcessingException {
        /*
        * 接受任务事件触发
        * */
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("触发事件：接受任务")));
    }

    public static void actionLearnSkill(PlayerCharacter playerCharacter, ActionLearnSkill actionRecord, Session session) throws JsonProcessingException {
        /*
         * 学习技能事件触发
         * */
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("触发事件：学习技能")));
    }

    public static void actionCloseEvent(PlayerCharacter playerCharacter, ActionCloseEvent actionRecord, Session session) throws JsonProcessingException {
        /*
         * 关闭事件事件触发
         * */
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("触发事件：关闭事件")));
    }

    public static void actionDialogue(PlayerCharacter playerCharacter, ActionDialogue actionRecord, Session session) throws JsonProcessingException {
        /*
         * 触发对话事件
         * */
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("触发事件：对话")));
    }

    public static void actionTurnInQuest(PlayerCharacter playerCharacter, ActionTurnInQuest actionRecord, Session session) throws JsonProcessingException {
        /*
         * 提交任务事件触发
         * */
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("触发事件：提交任务")));
    }

    public static void actionChangeStep(PlayerCharacter playerCharacter, ActionChangeStep actionRecord, Session session) throws JsonProcessingException {
        /*
         * 更该玩家step事件触发
         * */
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("触发事件：更改step")));
    }

    public static void actionChangeAttr(PlayerCharacter playerCharacter, ActionChangeAttr actionRecord, Session session) throws JsonProcessingException {
        /*
         * 修改玩家属性事件触发
         * */
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("触发事件：更改Attr")));
    }

    private boolean canAction(float odds) {
        //事件概率计算
        return Math.random() < odds;
    }



}
