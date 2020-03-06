package com.mud.game.handler;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.EquipmentPosition;
import com.mud.game.worlddata.db.models.SkillPosition;

import java.util.HashMap;
import java.util.Map;

public class EquipmentPositionHandler {
    public static Map<String, String> equipmentPositionNameMapping = new HashMap<>();

    private static void initEquipmentPositionNameMapping(){
        for(EquipmentPosition position: DbMapper.equipmentPositionRepository.findAll()){
            equipmentPositionNameMapping.put(position.getDataKey(), position.getName());
        }
    }

    public static void initEquipmentPositionHandler(){
        initEquipmentPositionNameMapping();
    }
}
