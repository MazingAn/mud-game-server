package com.mud.game.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.handler.StatusHandler;
import com.mud.game.messages.MsgMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.manager.PlayerScheduleManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldRoomObject;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.resultutils.GameWords;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.*;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 事件的触发类， 内部定义了所有游戏中的事件和触发机制
 * */
public class EventAction {

    /**
     * 玩家触发移动事件后对应的处理 （玩家会被移动到配置好的房间）
     * @param playerCharacter 触发事件的玩家
     * @param actionRecord 玩家触发移动事件的时候对应的配置记录 参考：{@link ActionMove}
     * */
    public static void actionMove(PlayerCharacter playerCharacter, ActionMove actionRecord) {
        WorldRoomObject targetRoom = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(actionRecord.getRoomKey());
        playerCharacter.msg(new MsgMessage(actionRecord.getDescription()));
        // 强制更新客户端地图
        PlayerCharacterManager.revealMap(playerCharacter, targetRoom , true);
        // 一定玩家到新的地图
        PlayerCharacterManager.moveTo(playerCharacter, actionRecord.getRoomKey());
    }

    /**
     * 玩家触发攻击事件后对应的处理（会有一个NPC跳出来攻击玩家）
     * @param playerCharacter 触发事件的玩家
     * @param actionRecord 玩家触发攻击事件的时候对应的配置记录 参考：{@link ActionAttack}
     * */
    public static void actionAttack(PlayerCharacter playerCharacter, ActionAttack actionRecord) {
        playerCharacter.msg(new MsgMessage("触发事件：攻击"));
    }

    /**
     * 玩家触发接受事件后对应的处理（玩家会接到一个任务）
     * @param playerCharacter 触发事件的玩家
     * @param actionRecord 玩家触发接受任务事件的时候对应的配置记录 参考：{@link ActionAcceptQuest}
     * */
    public static void actionAcceptQuest(PlayerCharacter playerCharacter, ActionAcceptQuest actionRecord)  {
        PlayerCharacterManager.acceptQuest(playerCharacter, actionRecord.getQuestKey());
    }

    /**
     * 玩家触发学习技能事件对应的处理（玩家会开始学习技能）
     * @param playerCharacter 触发事件的玩家
     * @param actionRecord 玩家触发事件的时候对应的配置记录 参考：{@link ActionLearnSkill}
     * */
    public static void actionLearnSkill(PlayerCharacter playerCharacter, ActionLearnSkill actionRecord)  {
        String skillKey = actionRecord.getSkillKey();
        int maxLevel = actionRecord.getMaxLevel();
        Runnable runnable = PlayerCharacterManager.learnSkillByEvent(playerCharacter, skillKey, actionRecord);
        if(runnable != null) {
            ScheduledExecutorService service = PlayerScheduleManager.createOrGetExecutorServiceForCaller(playerCharacter.getId());
            service.scheduleAtFixedRate(runnable, 0, 2, TimeUnit.SECONDS);
        }
    }

    /**
     * 玩家触发关闭事件对应的处理（玩家已触发的某一个事件会被关闭）
     * @param playerCharacter 触发事件的玩家
     * @param actionRecord 玩家触发事件的时候对应的配置记录 参考：{@link ActionCloseEvent}
     * */
    public static void actionCloseEvent(PlayerCharacter playerCharacter, ActionCloseEvent actionRecord)  {
        playerCharacter.msg(new MsgMessage("触发事件：关闭事件"));
    }

    /**
     * 玩家触发对话事件对应的处理（玩家会进入一段对话）
     * @param playerCharacter 触发事件的玩家
     * @param actionRecord 玩家触发事件的时候对应的配置记录 参考：{@link ActionDialogue}
     * */
    public static void actionDialogue(PlayerCharacter playerCharacter, ActionDialogue actionRecord)  {
        playerCharacter.msg(new MsgMessage("触发事件：对话"));
    }

    /**
     * 玩家触发提交任务事件对应的处理（会自动帮玩家提交一个任务）
     * @param playerCharacter 触发事件的玩家
     * @param actionRecord 玩家触发事件的时候对应的配置记录 参考：{@link ActionTurnInQuest}
     * */
    public static void actionTurnInQuest(PlayerCharacter playerCharacter, ActionTurnInQuest actionRecord)  {
        PlayerCharacterManager.turnInQuest(playerCharacter, actionRecord.getQuestKey());
        Quest quest = DbMapper.questRepository.findQuestByDataKey(actionRecord.getQuestKey());
        playerCharacter.msg(new MsgMessage(String.format("任务 {c%s{n 已成功提交", quest.getName())));
    }

    /**
     * 玩家触发触发高度改变事件对应的处理（玩家在当前房间的高度或者视野会发生改变）
     * @param playerCharacter 触发事件的玩家
     * @param actionRecord 玩家触发事件的时候对应的配置记录 参考：{@link ActionChangeStep}
     * */
    public static void actionChangeStep(PlayerCharacter playerCharacter, ActionChangeStep actionRecord)  {
        playerCharacter.setRoomStep(actionRecord.getStep());
        GameCharacterManager.saveCharacter(playerCharacter);
        playerCharacter.msg(new MsgMessage(GameWords.ROOM_STEP_CHANGED));
        PlayerCharacterManager.lookAround(playerCharacter);
    }

    /**
     * 玩家触发触发属性改变事件对应的处理（玩家的属性会被修改）
     * @param playerCharacter 触发事件的玩家
     * @param actionRecord 玩家触发事件的时候对应的配置记录 参考：{@link ActionChangeAttr}
     *
     * */
    public static void actionChangeAttr(PlayerCharacter playerCharacter, ActionChangeAttr actionRecord){
        String attrName = actionRecord.getAttrName();
        String changeValue = actionRecord.getChangedValue();
        GameCharacterManager.changeStatus(playerCharacter, attrName, changeValue);
        GameCharacterManager.saveCharacter(playerCharacter);
        PlayerCharacterManager.showStatus(playerCharacter);
        String descVerb =  (Double.parseDouble(changeValue) < 0) ? "减少了" : "增加了";
        playerCharacter.msg(new MsgMessage(actionRecord.getDescription()));
        playerCharacter.msg(new MsgMessage(
                "你的"+ StatusHandler.attrMapping.get(attrName) +  descVerb +
                        Math.abs((int)(Double.parseDouble(changeValue))) + "点")
        );
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
