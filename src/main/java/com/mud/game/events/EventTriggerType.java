package com.mud.game.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * "EVENT_TRIGGER_ARRIVE" : "到达该房间之后触发",
 * "EVENT_TRIGGER_KILL": "杀死该对象之后触发",
 * "EVENT_TRIGGER_DIE" : "该目标死亡之后触发",
 * "EVENT_TRIGGER_TRAVERSE": "通过改出口时触发",
 * "EVENT_TRIGGER_ACTION": "与该物体交互之后触发",
 * "EVENT_TRIGGER_SENTENCE" : "对话进行到该句子的时候触发"
 * </pre>
 * */
public class EventTriggerType {
    public static final String EVENT_TRIGGER_ARRIVE = "EVENT_TRIGGER_ARRIVE";
    public static final String EVENT_TRIGGER_KILL = "EVENT_TRIGGER_KILL";
    public static final String EVENT_TRIGGER_DIE = "EVENT_TRIGGER_DIE";
    public static final String EVENT_TRIGGER_TRAVERSE = "EVENT_TRIGGER_TRAVERSE";
    public static final String EVENT_TRIGGER_ACTION = "EVENT_TRIGGER_ACTION";
    public static final String EVENT_TRIGGER_SENTENCE = "EVENT_TRIGGER_SENTENCE";

}
