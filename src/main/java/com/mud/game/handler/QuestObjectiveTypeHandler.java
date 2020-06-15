package com.mud.game.handler;

import java.util.HashMap;
import java.util.Map;

public class QuestObjectiveTypeHandler {
    public static final String OBJECTIVE_KILL = "OBJECTIVE_KILL";
    public static final String OBJECTIVE_OBJECT = "OBJECTIVE_OBJECT";
    public static final String OBJECTIVE_TALK = "OBJECTIVE_TALK";
    public static final String OBJECTIVE_ARRIVE = "OBJECTIVE_ARRIVE";
    public static final String OBJECTIVE_ATTR = "OBJECTIVE_ATTR";

    public static final Map<String, String> objectiveTypeMapper = new HashMap<>();

    public static void initObjectiveTypeMapper(){
        objectiveTypeMapper.put(OBJECTIVE_KILL, "杀死");
        objectiveTypeMapper.put(OBJECTIVE_OBJECT, "获得");
        objectiveTypeMapper.put(OBJECTIVE_TALK, "对话");
        objectiveTypeMapper.put(OBJECTIVE_ARRIVE, "到达");
        objectiveTypeMapper.put(OBJECTIVE_ATTR, "属性");
    }

}
