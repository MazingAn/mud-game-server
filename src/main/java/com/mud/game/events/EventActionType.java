package com.mud.game.events;


/**
 * <pre>
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
 * </pre>
 * */
public class EventActionType {

    public static final String ACTION_ATTACK = "ACTION_ATTACK";
    public static final String ACTION_ACCEPT_QUEST = "ACTION_ACCEPT_QUEST";
    public static final String ACTION_LEARN_SKILL = "ACTION_LEARN_SKILL";
    public static final String ACTION_CLOSE = "ACTION_CLOSE";
    public static final String ACTION_DIALOGUE = "ACTION_DIALOGUE";
    public static final String ACTION_TURN_IN_QUEST = "ACTION_TURN_IN_QUEST";
    public static final String ACTION_MOVE = "ACTION_MOVE";
    public static final String ACTION_CHANGE_STEP = "ACTION_CHANGE_STEP";
    public static final String ACTION_CHANGE_ATTR = "ACTION_CHANGE_ATTR";
}
