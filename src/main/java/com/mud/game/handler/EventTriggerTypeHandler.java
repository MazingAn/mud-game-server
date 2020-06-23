package com.mud.game.handler;

import com.mud.game.events.EventTriggerType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventTriggerTypeHandler {

    /**
     * 事件触发类型集合
     * 集合元素为字典，Key为集合KEY，Value为集合中文解释
     * */
    public static List<Map<String, String>> eventTriggerTypes = new ArrayList<>();

    /**
     * 初始化事件触发类型
     * */
    public static void initEventTriggerType(){
        Map<String,String> actionTrigger = new HashMap<>();
        actionTrigger.put(EventTriggerType.EVENT_TRIGGER_ACTION, "与物体交互");
        Map<String,String> dieTrigger = new HashMap<>();
        dieTrigger.put(EventTriggerType.EVENT_TRIGGER_DIE, "某个角色死亡");
        Map<String,String> killTrigger = new HashMap<>();
        killTrigger.put(EventTriggerType.EVENT_TRIGGER_KILL, "杀死某个角色");
        Map<String,String> sentenceTrigger = new HashMap<>();
        sentenceTrigger.put(EventTriggerType.EVENT_TRIGGER_SENTENCE, "对话进行到某个句子");
        Map<String,String> traverseTrigger = new HashMap<>();
        traverseTrigger.put(EventTriggerType.EVENT_TRIGGER_TRAVERSE, "通过出口");
        Map<String,String> arriveTrigger = new HashMap<>();
        arriveTrigger.put(EventTriggerType.EVENT_TRIGGER_ARRIVE, "到达某个地方");
        eventTriggerTypes.add(actionTrigger);
        eventTriggerTypes.add(dieTrigger);
        eventTriggerTypes.add(killTrigger);
        eventTriggerTypes.add(sentenceTrigger);
        eventTriggerTypes.add(traverseTrigger);
        eventTriggerTypes.add(arriveTrigger);
    }
}
