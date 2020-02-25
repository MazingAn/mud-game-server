package com.mud.game.handler;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.SkillFunctionType;
import com.mud.game.worlddata.db.models.SkillPosition;

import java.util.HashMap;
import java.util.Map;

public class SkillPositionHandler {

    public static Map<String, String> skillPositionNameMapping = new HashMap<>();

    private static void initSkillPositionNameMapping(){
        for(SkillPosition position: DbMapper.skillPositionRepository.findAll()){
            skillPositionNameMapping.put(position.getDataKey(), position.getName());
        }
    }

    public static void initSkillPositionHandler(){
        initSkillPositionNameMapping();
    }
}
