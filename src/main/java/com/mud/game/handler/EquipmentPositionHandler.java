package com.mud.game.handler;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.EquipmentPosition;
import com.mud.game.worlddata.db.models.SkillPosition;

import java.util.HashMap;
import java.util.Map;

/**
 * 装备位置缓存
 *
 * 这个类中定义的装备表将在服务器启动的时候被初始化  参见：{@link com.mud.game.server.ServerManager}
 *
 * */
public class EquipmentPositionHandler {

    /** 装备位置缓存 key为技能位置的标识， value为对应的中文描述  如 {"left_hand": "左手"}*/
    public static Map<String, String> equipmentPositionNameMapping = new HashMap<>();

    /** 内部方法 初始装备能位置表，查询数据库 */
    private static void initEquipmentPositionNameMapping(){
        for(EquipmentPosition position: DbMapper.equipmentPositionRepository.findAll()){
            equipmentPositionNameMapping.put(position.getDataKey(), position.getName());
        }
    }

    /** 外部方法  初始化装备位置表 这个方法总会在服务器启动的时候被调用  参见 {@link com.mud.game.server.ServerManager} {@code start} */
    public static void initEquipmentPositionHandler(){
        initEquipmentPositionNameMapping();
    }
}
