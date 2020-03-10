package com.mud.game.handler;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.School;

import java.util.HashMap;
import java.util.Map;

/**
 * 门派名称缓存类
 *
 * 这个类中定义的门派名称将在服务器启动的时候被初始化  参见：{@link com.mud.game.server.ServerManager}
 *
 * */
public class SchoolHandler {

    /**
     *  门派名称缓存 key为技能门派的标识， value为对应的中文描述  如 {"SCHOOL_GAIBANG": "丐帮"}
     * */
    public static Map<String, String> mapping = new HashMap<>();

    /** 外部方法  初始化门派名称缓存 这个方法总会在服务器启动的时候被调用  参见 {@link com.mud.game.server.ServerManager} {@code start} */
    public static void initSchoolHandler(){
        for(School school: DbMapper.schoolRepository.findAll()){
            mapping.put(school.getDataKey(), school.getName());
        }
    }

}
