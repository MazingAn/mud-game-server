package com.mud.game.handler;

import com.mud.game.events.EventAction;
import com.mud.game.events.EventActionType;
import com.mud.game.events.EventTriggerType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * "ACTION_ATTACK": "触发事件（出来一个NPC攻击玩家）",
 * "ACTION_ACCEPT_QUEST": "触发事件（玩家接受一个任务）",
 * "ACTION_LEARN_SKILL": "触发事件（玩家学会一个技能）",
 * "ACTION_CLOSE": "触发事件（关闭一个事件）",
 * "ACTION_DIALOGUE": "触发一段对话",
 * "ACTION_TURN_IN_QUEST": "触发事件（玩家提交一个任务)",
 * "ACTION_MOVE": "触发事件（玩家会被传送到某一个地点）",
 * "ACTION_CHANGE_STEP": "触发事件 改变玩家在当前房间的高度（视野）",
 * "ACTION_CLOSE_EVENT": "触发事件  关闭另一个事件（不推荐使用）",
 * "ACTION_CHANGE_ATTR": "触发事件 修改玩家属性"
 * */
public class EventActionTypeHandler {

    /**
     * 事件触发动作集合
     * 集合元素为字典，Key为集合KEY，Value为集合中文解释
     * */
    public static List<Map<String, String>> eventActionTypes = new ArrayList<>();

    /**
     * 初始化事件触发动作
     * */
    public static void initEventActionType(){
        Map<String,String> actionAttack = new HashMap<>();
        actionAttack.put(EventActionType.ACTION_ATTACK, "触发事件（出来一个NPC攻击玩家）");
        Map<String,String> actionAcceptQuest = new HashMap<>();
        actionAcceptQuest.put(EventActionType.ACTION_ACCEPT_QUEST, "触发事件（玩家接受一个任务）");
        Map<String,String> actionLearnSkill = new HashMap<>();
        actionLearnSkill.put(EventActionType.ACTION_LEARN_SKILL, "触发事件（玩家学会一个技能）");
        Map<String,String> actionDialogue = new HashMap<>();
        actionDialogue.put(EventActionType.ACTION_DIALOGUE, "触发一段对话");
        Map<String,String> actionTurnInQuest = new HashMap<>();
        actionTurnInQuest.put(EventActionType.ACTION_TURN_IN_QUEST, "触发事件（玩家提交一个任务)");
        Map<String,String> actionMove = new HashMap<>();
        actionMove.put(EventActionType.ACTION_MOVE, "触发事件（玩家会被传送到某一个地点）");
        Map<String,String> actionChangeStep = new HashMap<>();
        actionChangeStep.put(EventActionType.ACTION_CHANGE_STEP, "触发事件 改变玩家在当前房间的高度（视野）");
        Map<String,String> actionChangeAttr = new HashMap<>();
        actionChangeAttr.put(EventActionType.ACTION_CHANGE_ATTR, "触发事件 修改玩家属性");
        eventActionTypes.add(actionDialogue);
        eventActionTypes.add(actionMove);
        eventActionTypes.add(actionAcceptQuest);
        eventActionTypes.add(actionAttack);
        eventActionTypes.add(actionChangeAttr);
        eventActionTypes.add(actionChangeStep);
        eventActionTypes.add(actionLearnSkill);
        eventActionTypes.add(actionTurnInQuest);
    }
}
