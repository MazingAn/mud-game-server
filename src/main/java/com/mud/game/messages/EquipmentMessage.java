package com.mud.game.messages;

import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.CommonObjectInfo;
import com.mud.game.structs.EquipmentObjectAppearance;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.HashMap;
import java.util.Map;

public class EquipmentMessage {
    private Map<String, Object> equipments;

    public EquipmentMessage(PlayerCharacter playerCharacter) {
        this.equipments = new HashMap<>();
        Map<String,String> sourceData =  playerCharacter.getEquippedEquipments();
        for(String key: sourceData.keySet()){
            if(sourceData.get(key)!=null){
                EquipmentObject equipmentObject = MongoMapper.equipmentObjectRepository.findEquipmentObjectById(sourceData.get(key));
                this.equipments.put(key, new EquipmentObjectAppearance(equipmentObject));
            }else{
                this.equipments.put(key, null);
            }
        }
    }

    public Map<String, Object> getEquipments() {
        return equipments;
    }

    public void setEquipments(Map<String, Object> equipments) {
        this.equipments = equipments;
    }
}
