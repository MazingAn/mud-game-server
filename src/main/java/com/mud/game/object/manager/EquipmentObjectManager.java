package com.mud.game.object.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.mud.game.messages.MsgMessage;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.structs.EmbeddedCommand;
import com.mud.game.utils.jsonutils.Attr2Map;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.jsonutils.JsonStrConvetor;
import com.mud.game.utils.resultutils.GameWords;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Equipment;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EquipmentObjectManager {

    public static EquipmentObject create(String equipmentTemplateKey) {
        /*创建一件装备*/
        EquipmentObject equipment = new EquipmentObject();
        try{
            Equipment template = DbMapper.equipmentRepository.findEquipmentByDataKey(equipmentTemplateKey);
            equipment.setDataKey(template.getDataKey());
            equipment.setName(template.getName());
            equipment.setDescription(template.getDescription());
            equipment.setUnitName(template.getUnitName());
            equipment.setCategory(template.getCategory());
            equipment.setUnique(template.isUniqueInBag());
            equipment.setMaxStack(template.getMaxStack());
            equipment.setCanDiscard(template.isCanDiscard());
            equipment.setCanRemove(template.isCanRemove());
            equipment.setIcon(template.getIcon());
            equipment.setPositions(JsonStrConvetor.ToSet(template.getPositions()));
            equipment.setSuite(template.getSuite());
            equipment.setWeaponType(template.getWeaponType());
            equipment.setMaxSlot(template.getMaxSlot());
            equipment.setQuality(template.getQuality());
            equipment.setGems(new ArrayList<>());
            equipment.setOwner(null);
            equipment.setEquipped(false);
            equipment.setAttrs(Attr2Map.equipmentAttrTrans(template.getAttrs()));
        }catch (Exception e){
            System.out.println("在创建装备的时候出现问题，请检查游戏装备配置文件，装备KEY： " + equipmentTemplateKey);
        }
        return equipment;
    }

    public static void equipTo(EquipmentObject equipmentObject, CommonCharacter character, String position, Session session) throws JsonProcessingException {
        /*装备到角色身上*/
        if(checkBeforeEquip(equipmentObject, position, session)){
            // 移除掉旧的装备
            Map<String, String> playerEquippedEquipments = character.getEquippedEquipments();
            if(playerEquippedEquipments.containsKey(position)){
                if(playerEquippedEquipments.get(position) != null){
                    String oldEquipmentId = playerEquippedEquipments.get(position);
                    EquipmentObject oldEquipmentObject = MongoMapper.equipmentObjectRepository.findEquipmentObjectById(oldEquipmentId);
                    takeOff(oldEquipmentObject, character, position, session);
                }
            }
            // 依次应用装备属性
            for(String attrKey : equipmentObject.getAttrs().keySet()){
                float value = equipmentObject.getAttrs().get(attrKey);
                GameCharacterManager.changeStatus(character, attrKey, value);
            }
            equipmentObject.setEquipped(true);
            character.getEquippedEquipments().put(position, equipmentObject.getId());
            syncEquipmentAndCharacterStatus(equipmentObject, character, session);
        }
    }

    public static void takeOff(EquipmentObject equipmentObject, CommonCharacter character, String position, Session session) throws JsonProcessingException {
        /*从角色身上取掉装备*/
        for(String attrKey : equipmentObject.getAttrs().keySet()){
            float value = equipmentObject.getAttrs().get(attrKey);
            GameCharacterManager.changeStatus(character, attrKey, value * -1);
        }
        equipmentObject.setEquipped(false);
        character.getEquippedEquipments().put(position, null);
        syncEquipmentAndCharacterStatus(equipmentObject, character, session);
    }

    public static void syncEquipmentAndCharacterStatus(EquipmentObject equipmentObject, CommonCharacter character, Session session) throws JsonProcessingException {
        MongoMapper.equipmentObjectRepository.save(equipmentObject);
        if(character.getClass().equals(PlayerCharacter.class)){
            MongoMapper.playerCharacterRepository.save((PlayerCharacter) character);
            PlayerCharacterManager.showStatus((PlayerCharacter) character, session);
        }else{
            MongoMapper.worldNpcObjectRepository.save((WorldNpcObject) character);
        }
    }

    public static boolean checkBeforeEquip(EquipmentObject equipmentObject, String position,  Session session) throws JsonProcessingException {
        /*
         *  装备之前检查装备是否可以装备
         * */
        // 检查是否已经被使用（一个装备只能被装备一次）
        if(equipmentObject.isEquipped()){
            if(session!=null) session.sendText(JsonResponse.JsonStringResponse(String.format(equipmentObject.getName(), new MsgMessage(GameWords.EQUIPMENT_ALREADY_EQUIPPED))));
            return false;
        }
        // 检查装备和技能是否匹配
        return true;
    }

    public static List<EmbeddedCommand> getAvailableCommands(EquipmentObject equipmentObject, PlayerCharacter playerCharacter) {
        /*获得装备可操作命令*/
        List<EmbeddedCommand> cmds = new ArrayList<>();

        return cmds;
    }

}
