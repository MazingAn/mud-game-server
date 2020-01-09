package com.mud.game.handler;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.School;

import java.util.HashMap;
import java.util.Map;

public class SchoolHandler {
    public static Map<String, String> mapping = new HashMap<>();

    public static void initSchoolHandler(){
        for(School school: DbMapper.schoolRepository.findAll()){
            mapping.put(school.getDataKey(), school.getName());
        }
    }

}
