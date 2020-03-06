package com.mud.game.object.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.mud.game.handler.EquipmentPositionHandler;
import com.mud.game.messages.MsgMessage;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.BagpackObject;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.structs.CommonObjectInfo;
import com.mud.game.structs.EmbeddedCommand;
import com.mud.game.structs.EquipmentObjectAppearance;
import com.mud.game.structs.GameObjectAppearance;
import com.mud.game.utils.jsonutils.Attr2Map;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.jsonutils.JsonStrConvetor;
import com.mud.game.utils.resultutils.GameWords;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Equipment;
import com.mud.game.worlddata.db.models.EquipmentPosition;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.HashMap;
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
            equipment.setTotalNumber(0);
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
                Object value = equipmentObject.getAttrs().get(attrKey).get("value");
                GameCharacterManager.changeStatus(character, attrKey, value);
            }
            // TODO：应用装备内的宝石的属性

            // 更新装备的状态
            equipmentObject.setEquipped(true);
            equipmentObject.setEquippedPosition(position);
            // 更新玩家状态
            character.getEquippedEquipments().put(position, equipmentObject.getId());
            // 同步装备和玩家状态
            syncEquipmentAndCharacterStatus(equipmentObject, character, session);
        }
    }

    public static void takeOff(EquipmentObject equipmentObject, CommonCharacter character, String position, Session session) throws JsonProcessingException {
        /*从角色身上取掉装备*/
        // Todo： 判断背包里面还能不能装下装备，如果装不下那就卸不掉，只能用手拿着

        // 开始卸掉装备
        for(String attrKey : equipmentObject.getAttrs().keySet()){
            Object valueStr = equipmentObject.getAttrs().get(attrKey).get("value");
            float value =  Float.parseFloat(valueStr.toString());
            GameCharacterManager.changeStatus(character, attrKey, value * -1);
        }
        equipmentObject.setEquipped(false);
        equipmentObject.setEquippedPosition(null);
        character.getEquippedEquipments().put(position, null);
        syncEquipmentAndCharacterStatus(equipmentObject, character, session);
    }

    public static void syncEquipmentAndCharacterStatus(EquipmentObject equipmentObject, CommonCharacter character, Session session) throws JsonProcessingException {
        MongoMapper.equipmentObjectRepository.save(equipmentObject);
        if(character.getClass().equals(PlayerCharacter.class)){
            // 同步背包里装备的状态
            syncEquipmentAndBagpackStatus(equipmentObject, (PlayerCharacter) character);
            // 保存玩家信息
            MongoMapper.playerCharacterRepository.save((PlayerCharacter) character);
            // 返回客户端最新的背包信息和状态信息
            PlayerCharacterManager.showStatus((PlayerCharacter) character, session);
            PlayerCharacterManager.returnBagpack((PlayerCharacter) character, session);
            PlayerCharacterManager.returnEquippedEquipments((PlayerCharacter) character, session);
        }else{
            MongoMapper.worldNpcObjectRepository.save((WorldNpcObject) character);
        }
    }

    public static void syncEquipmentAndBagpackStatus(EquipmentObject equipmentObject, PlayerCharacter character){
        /*
        * 同步背包里装备的状态
        * 每当装备的状态发生改变的时候，需要手动更新一下背包里面对应的装备的状态，这是一处设计缺陷
        * */
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(character.getBagpack());
        for(String cellId: bagpackObject.getItems().keySet()){
            if(cellId.startsWith(equipmentObject.getId())){
                bagpackObject.getItems().put(cellId, new CommonObjectInfo(equipmentObject, 1));
                break;
            }
        }
        MongoMapper.bagpackObjectRepository.save(bagpackObject);
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

        if(equipmentObject.isEquipped()){ // 已经装备显示卸掉命令
            String position = equipmentObject.getEquippedPosition();
            Map<String, Object> args = new HashMap<>();
            args.put("dbref", equipmentObject.getId());
            args.put("position", position);
            cmds.add(new EmbeddedCommand("卸掉", "take_off_equipment", args));
        }else{ // 如果装备没有被装备，则显示装备命令
            for(String position : equipmentObject.getPositions()){
                Map<String, Object> args = new HashMap<>();
                args.put("dbref", equipmentObject.getId());
                args.put("position", position);
                cmds.add(new EmbeddedCommand("装备到"+ EquipmentPositionHandler.equipmentPositionNameMapping.get(position),
                        "equip_equipment", args));
            }
        }

        return cmds;
    }

    public static void onPlayerLook(EquipmentObject equipmentObject, PlayerCharacter playerCharacter, Session session) throws JsonProcessingException {
        /*
         * @ 当玩家查看装备的时候返回装备信息和可执行的命令（操作）
         * */
        Map<String, Object> lookMessage = new HashMap<>();
        EquipmentObjectAppearance appearance = new EquipmentObjectAppearance(equipmentObject);
        // 设置玩家可以对此物体执行的命令
        appearance.setCmds(EquipmentObjectManager.getAvailableCommands(equipmentObject, playerCharacter));
        lookMessage.put("look_obj", appearance);
        session.sendText(JsonResponse.JsonStringResponse(lookMessage));
    }

}
