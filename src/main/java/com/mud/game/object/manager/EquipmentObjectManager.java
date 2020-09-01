package com.mud.game.object.manager;

import com.mud.game.handler.EquipmentPositionHandler;
import com.mud.game.messages.AlertMessage;
import com.mud.game.messages.CheckStrengthenMessage;
import com.mud.game.messages.MsgMessage;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.*;
import com.mud.game.structs.CheckStrengthenInfo;
import com.mud.game.structs.EmbeddedCommand;
import com.mud.game.structs.EquipmentObjectAppearance;
import com.mud.game.utils.jsonutils.Attr2Map;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.jsonutils.JsonStrConvetor;
import com.mud.game.utils.resultutils.GameWords;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Equipment;
import com.mud.game.worlddata.db.models.Gem;
import com.mud.game.worlddata.db.models.NormalObject;
import com.mud.game.worlddata.db.models.StrengthenMaterial;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.yeauty.pojo.Session;

import java.util.*;

import static com.mud.game.constant.Constant.MAX_LEVEL;
import static com.mud.game.constant.Constant.STRENGTHEN_COEFFICIENT;


public class EquipmentObjectManager {


    public static EquipmentObject create(String equipmentTemplateKey) {
        /*创建一件装备*/
        EquipmentObject equipment = new EquipmentObject();
        try {
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
        } catch (Exception e) {
            System.out.println("在创建装备的时候出现问题，请检查游戏装备配置文件，装备KEY： " + equipmentTemplateKey);
        }
        return equipment;
    }

    /**
     * 装备 装备到角色身上
     *
     * @param equipmentObject 装备
     * @param character       角色
     * @param position        要装备的位置
     * @param session         通信通道
     */
    public static void equipTo(EquipmentObject equipmentObject, CommonCharacter character, String position, Session session) {
        /*装备到角色身上*/
        if (checkBeforeEquip(equipmentObject, character, position, session)) {
            // 移除掉旧的装备
            Map<String, String> playerEquippedEquipments = character.getEquippedEquipments();
            // 如果角色装备字典里面包含 要装备的位置
            if (playerEquippedEquipments.containsKey(position)) {
                // 如果当前装备字典里面获得的对应位置装备不为空
                if (playerEquippedEquipments.get(position) != null) {
                    // 拿到当前位置的旧装备
                    String oldEquipmentId = playerEquippedEquipments.get(position);
                    EquipmentObject oldEquipmentObject = MongoMapper.equipmentObjectRepository.findEquipmentObjectById(oldEquipmentId);
                    // 脱掉旧装备
                    takeOff(oldEquipmentObject, character, position, session);
                }
            }

            // 依次应用装备属性
            // 循环遍历 装备的属性
            if (null != equipmentObject.getAttrs()) {
                for (String attrKey : equipmentObject.getAttrs().keySet()) {
                    // 找到对应的属性名字 对应的数值
                    Object value = equipmentObject.getAttrs().get(attrKey).get("value");
                    // 应用属性名字和对应的数值到角色身上
                    GameCharacterManager.changeStatus(character, attrKey, value);
                }
            }
            // 添加装备宝石属性
            List<GemObject> gemObjectList = equipmentObject.getGems();
            for (int i = 0; i < gemObjectList.size(); i++) {
                if (gemObjectList.size() > 0) {
                    for (String attrKey : gemObjectList.get(i).getAttrs().keySet()) {
                        // 找到对应的属性名字 对应的数值
                        Object value = gemObjectList.get(i).getAttrs().get(attrKey).get("value");
                        // 应用属性名字和对应的数值到角色身上
                        GameCharacterManager.changeStatus(character, attrKey, value);
                    }
                }
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

    /**
     * 脱掉装备
     *
     * @param equipmentObject 装备
     * @param character       角色
     * @param position        要装备的位置
     * @param session         通信通道
     */
    public static void takeOff(EquipmentObject equipmentObject, CommonCharacter character, String position, Session session) {
        /*从角色身上取掉装备*/
        if (character instanceof PlayerCharacter) {
            // 判断背包里面还能不能装下装备，如果装不下那就卸不掉，只能用手拿着
            BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(((PlayerCharacter) character).getBagpack());
            if (!CommonItemContainerManager.checkCanAdd(bagpackObject, equipmentObject, 1)) {
                //无法卸掉装备信息提示
                character.msg(new ToastMessage("无法卸掉装备，请清理你的背包！"));
            } else {
                // 开始卸掉装备
                if(null!=equipmentObject.getAttrs()){
                for (String attrKey : equipmentObject.getAttrs().keySet()) {
                    Object valueStr = equipmentObject.getAttrs().get(attrKey).get("value");
                    float value = Float.parseFloat(valueStr.toString());
                    GameCharacterManager.changeStatus(character, attrKey, value * -1);
                }}
                // 去掉装备宝石属性
                List<GemObject> gemObjectList = equipmentObject.getGems();
                for (int i = 0; i < gemObjectList.size(); i++) {
                    if (gemObjectList.size() > 0) {
                        for (String attrKey : gemObjectList.get(i).getAttrs().keySet()) {
                            Object valueStr = gemObjectList.get(i).getAttrs().get(attrKey).get("value");
                            float value = Float.parseFloat(valueStr.toString());
                            GameCharacterManager.changeStatus(character, attrKey, value * -1);
                        }
                    }
                }

                equipmentObject.setEquipped(false);
                equipmentObject.setEquippedPosition(null);
                character.getEquippedEquipments().put(position, null);
                syncEquipmentAndCharacterStatus(equipmentObject, character, session);
            }
        }
    }

    /**
     * 同步装备和角色状态
     */
    public static void syncEquipmentAndCharacterStatus(EquipmentObject equipmentObject, CommonCharacter character, Session session) {
        MongoMapper.equipmentObjectRepository.save(equipmentObject);
        if (character.getClass().equals(PlayerCharacter.class)) {
            // 同步背包里装备的状态
            syncEquipmentAndBagpackStatus(equipmentObject, (PlayerCharacter) character);
            // 保存玩家信息
            MongoMapper.playerCharacterRepository.save((PlayerCharacter) character);
            // 返回客户端最新的背包信息和状态信息
            PlayerCharacterManager.showStatus((PlayerCharacter) character);
            PlayerCharacterManager.showBagpack((PlayerCharacter) character);
            PlayerCharacterManager.returnEquippedEquipments((PlayerCharacter) character);
        } else {
            MongoMapper.worldNpcObjectRepository.save((WorldNpcObject) character);
        }
    }

    /**
     * 同步装备和背包状态
     */
    private static void syncEquipmentAndBagpackStatus(EquipmentObject equipmentObject, PlayerCharacter character) {
        /*
         * 同步背包里装备的状态
         * 每当装备的状态发生改变的时候，需要手动更新一下背包里面对应的装备的状态，这是一处设计缺陷
         * */
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(character.getBagpack());
        // 如果装备是被装备的状态，则从给背包移除
        if (equipmentObject.isEquipped()) {
            CommonItemContainerManager.removeItem(bagpackObject, equipmentObject, 1);
        } else { // 如果装备是非装备状态，则添加到背包里
            CommonItemContainerManager.addItem(bagpackObject, equipmentObject, 1);
        }
        MongoMapper.bagpackObjectRepository.save(bagpackObject);
    }

    /**
     * 装备前检查
     */
    public static boolean checkBeforeEquip(EquipmentObject equipmentObject, CommonCharacter character, String position, Session session) {
        /*
         *  装备之前检查装备是否可以装备
         * */
        // 检查装备是不是当前玩家的
        if (!equipmentObject.getOwner().equals(character.getId())) {
            return false;
        }
        // 检查是否已经被使用（一个装备只能被装备一次）
        if (equipmentObject.isEquipped()) {
            if (session != null)
                session.sendText(JsonResponse.JsonStringResponse(String.format(equipmentObject.getName(), new MsgMessage(GameWords.EQUIPMENT_ALREADY_EQUIPPED))));
            return false;
        }
        // 检查装备和技能是否匹配
        return true;
    }

    /**
     * 装备可用命令
     */
    public static List<EmbeddedCommand> getAvailableCommands(EquipmentObject equipmentObject, PlayerCharacter playerCharacter) {
        /*获得装备可操作命令*/
        List<EmbeddedCommand> cmds = new ArrayList<>();
        if (equipmentObject.getOwner().equals(playerCharacter.getId())) {
            if (equipmentObject.isEquipped()) { // 已经装备显示卸掉命令
                String position = equipmentObject.getEquippedPosition();
                Map<String, Object> args = new HashMap<>();
                args.put("dbref", equipmentObject.getId());
                args.put("position", position);
                cmds.add(new EmbeddedCommand("卸掉", "take_off_equipment", args));
            } else { // 如果装备没有被装备，则显示装备命令
                for (String position : equipmentObject.getPositions()) {
                    Map<String, Object> args = new HashMap<>();
                    args.put("dbref", equipmentObject.getId());
                    args.put("position", position);
                    cmds.add(new EmbeddedCommand("装备到" + EquipmentPositionHandler.equipmentPositionNameMapping.get(position),
                            "equip_equipment", args));
                }
            }
        }
        return cmds;
    }

    /**
     * 玩家查看装备时候的回调
     */
    public static void onPlayerLook(EquipmentObject equipmentObject, PlayerCharacter playerCharacter, Session session) {
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

    /**
     * 返回强化材料信息及装备属性
     */
    public static void checkStrengthen(EquipmentObject equipmentObject, PlayerCharacter caller, Session session) {
        //能否强化
        Boolean can_strength = true;
        Boolean isMaxLevel = false;

        if (null == equipmentObject) {
            return;
        }
        //校验
        if (equipmentObject.getLevel() == MAX_LEVEL) {
            can_strength = false;
            isMaxLevel = true;
        }
        //获取装备强化需要的材料列表
        String dataKey = equipmentObject.getDataKey();
        List<StrengthenMaterial> strengthenMaterialList = DbMapper.strengthenMaterialRepository.findStrengthenMaterialByDataKeyAndLevel(dataKey, equipmentObject.getLevel());
        if (strengthenMaterialList == null || strengthenMaterialList.size() == 0) {
            can_strength = false;
        }
        Map<String, Map<String, Object>> strengthenMaterialsMap = new HashMap<>();
        Map<String, Object> strengthenMaterialMap = new HashMap<>();
        Iterator<StrengthenMaterial> strengthenMaterialIterator = strengthenMaterialList.iterator();
        StrengthenMaterial strengthenMaterial = null;
        while (strengthenMaterialIterator.hasNext()) {
            strengthenMaterial = strengthenMaterialIterator.next();
            //获取材料信息
            NormalObject target = DbMapper.normalObjectRepository.findNormalObjectByDataKey(strengthenMaterial.getDependency());
            strengthenMaterialsMap = new HashMap<>();
            strengthenMaterialMap.put("needed", strengthenMaterial.getNumber());
            strengthenMaterialMap.put("icon", target.getIcon());
            strengthenMaterialMap.put("name", target.getName());
            strengthenMaterialMap.put("desc", target.getDescription());
            BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(caller.getBagpack());
            strengthenMaterialMap.put("owned", CommonItemContainerManager.getNumberByDataKey(bagpackObject, target.getDataKey()));
            strengthenMaterialsMap.put(target.getDataKey(), strengthenMaterialMap);
        }
        //下一级装备属性
        Map<String, Object> attr_after = new HashMap<>();
        //目前装备属性
        //返回数据
        Map<String, Map<String, Object>> attr_befor = new HashMap<>();
        //装备属性数据
        Map<String, Object> attributeMap = new HashMap<>();
        Iterator<Map.Entry<String, Map<String, Object>>> mapIterator = equipmentObject.getAttrs().entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry<String, Map<String, Object>> mapEntry = mapIterator.next();
            Iterator<Map.Entry<String, Object>> mapEntryIterator = mapEntry.getValue().entrySet().iterator();
            attributeMap = new HashMap<>();
            while (mapEntryIterator.hasNext()) {
                Map.Entry<String, Object> map = mapEntryIterator.next();
                attributeMap.put(map.getKey(), map.getValue());
                if ("value".equals(map.getKey())) {
                    Object value = map.getValue();
                    int a = (int) Double.parseDouble(value.toString());
                    double v = a * STRENGTHEN_COEFFICIENT;
                    attr_after.put(mapEntry.getKey(), Math.floor(v));
                }
            }
            attributeMap.put("key", mapEntry.getKey());
            attr_befor.put(mapEntry.getKey(), attributeMap);
        }
        //下一级装备属性
        if (isMaxLevel) {
            Iterator<Map.Entry<String, Object>> attrAfterIterator = attr_after.entrySet().iterator();
            while (attrAfterIterator.hasNext()) {
                Map.Entry<String, Object> entry = attrAfterIterator.next();
                entry.setValue("强化已到最高等级！");
            }
        }
        caller.msg(new CheckStrengthenMessage(new CheckStrengthenInfo(attr_after, can_strength, strengthenMaterialsMap, attr_befor)));
    }


    /**
     * 强化
     */
    public static void strengthen(EquipmentObject equipmentObject, PlayerCharacter caller, Session session) {
        String bagpackId = caller.getBagpack();
        if (null == equipmentObject) {
            return;
        }
        //校验
        if (equipmentObject.getLevel() >= MAX_LEVEL) {
            return;
        }
        //获取装备强化需要的材料列表
        String dataKey = equipmentObject.getDataKey();
        List<StrengthenMaterial> strengthenMaterialList = DbMapper.strengthenMaterialRepository.findStrengthenMaterialByDataKeyAndLevel(dataKey, equipmentObject.getLevel());
        if (strengthenMaterialList == null || strengthenMaterialList.size() == 0) {
            return;
        }
        //校验合成材料是否足够
        //背包信息校验
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(bagpackId);
        for (int i = 0; i < strengthenMaterialList.size(); i++) {
            if (!CommonItemContainerManager.checkCanRemove(bagpackObject, strengthenMaterialList.get(i).getDependency(), strengthenMaterialList.get(i).getNumber())) {
                //获取材料信息
                NormalObject target = DbMapper.normalObjectRepository.findNormalObjectByDataKey(strengthenMaterialList.get(i).getDependency());
                caller.msg(new AlertMessage("你的{g" + target.getName() + "{n不够!"));
                break;
            }
        }
        //从背包移除材料
        for (int i = 0; i < strengthenMaterialList.size(); i++) {
            CommonObject removeObject = CommonObjectBuilder.findObjectByDataKeyAndOwner(strengthenMaterialList.get(i).getDependency(), caller.getId());
            PlayerCharacterManager.removeObjectsFromBagpack(caller, removeObject, strengthenMaterialList.get(i).getNumber());
        }
        //强化
        equipmentObject.setLevel(equipmentObject.getLevel() + 1);
        equipmentObject.setAttrs(updateAttrs(equipmentObject, STRENGTHEN_COEFFICIENT));
        MongoMapper.equipmentObjectRepository.save(equipmentObject);
        PlayerCharacterManager.syncBagpack(caller, equipmentObject);
        if (equipmentObject.getLevel() != MAX_LEVEL) {
            caller.msg(new AlertMessage("你的{g" + equipmentObject.getName() + "{n强化等级{g+1{n!"));
        }
        PlayerCharacterManager.showBagpack(caller);
    }

    /**
     * 修改装备属性
     *
     * @param equipmentObject 装备
     * @param coefficient     系数
     * @return
     */
    public static Map<String, Map<String, Object>> updateAttrs(EquipmentObject equipmentObject, Double coefficient) {
        Map<String, Map<String, Object>> attrs = equipmentObject.getAttrs();
        Iterator<Map.Entry<String, Map<String, Object>>> mapIterator = attrs.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry<String, Map<String, Object>> mapEntry = mapIterator.next();
            Iterator<Map.Entry<String, Object>> mapEntryIterator = mapEntry.getValue().entrySet().iterator();
            while (mapEntryIterator.hasNext()) {
                Map.Entry<String, Object> map = mapEntryIterator.next();
                if ("value".equals(map.getKey())) {
                    if (null != map.getValue()) {
                        Object value = map.getValue();
                        int a = (int) Double.parseDouble(value.toString());
                        double v = a * coefficient;
                        map.setValue(Math.floor(v));
                    }
                }
            }
        }
        return attrs;
    }
}
