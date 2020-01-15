package com.mud.game.handler;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.School;
import com.mud.game.worlddata.db.models.SkillCategoryType;
import com.mud.game.worlddata.db.models.SkillFunctionType;

import java.util.HashMap;
import java.util.Map;

public class SkillTypeHandler {
    public static Map<String, String> functionTypeMapping = new HashMap<>();
    public static Map<String, String> categoryTypeMapping = new HashMap<>();

    private static void initSkillFunctionTypeHandler(){
        for(SkillFunctionType type: DbMapper.skillFunctionTypeRepository.findAll()){
            functionTypeMapping.put(type.getDataKey(), type.getName());
        }
    }

    private static void initSkillCategoryTypeHandler(){
        for(SkillCategoryType type: DbMapper.skillCategoryTypeRepository.findAll()){
            categoryTypeMapping.put(type.getDataKey(), type.getName());
        }
    }

    public static void initSkillTypeHandler(){
        initSkillCategoryTypeHandler();
        initSkillFunctionTypeHandler();
    }
}
