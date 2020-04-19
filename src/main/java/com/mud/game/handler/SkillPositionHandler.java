package com.mud.game.handler;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.SkillPosition;

import java.util.HashMap;
import java.util.Map;


/**
 * 技能位置缓存类
 *
 * 这个类中定义的技能位置信息将在服务器启动的时候被初始化  参见：{@link com.mud.game.server.ServerManager}
 *
 * */
public class SkillPositionHandler {

    /** 技能位置信息缓存  key为技能的唯一标识 value为对应的中文名称  如{"neigong", "内功"}  */
    public static Map<String, String> skillPositionNameMapping = new HashMap<>();

    /** 内部方法  初始化技能位置缓存（从数据库一次性读取） */
    private static void initSkillPositionNameMapping(){
        for(SkillPosition position: DbMapper.skillPositionRepository.findAll()){
            skillPositionNameMapping.put(position.getDataKey(), position.getName());
        }
    }

    /** 外部方法  初始化技能位置缓存  这个方法总是会在服务器启动的时候被调用
     *  参见{@link com.mud.game.server.ServerManager} {@code start}*/
    public static void initSkillPositionHandler(){
        initSkillPositionNameMapping();
    }
}
