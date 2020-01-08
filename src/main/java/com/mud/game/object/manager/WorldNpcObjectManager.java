package com.mud.game.object.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.object.typeclass.WorldObjectCreator;
import com.mud.game.object.typeclass.WorldRoomObject;
import com.mud.game.structs.GameObjectAppearance;
import com.mud.game.structs.NpcAppearance;
import com.mud.game.utils.jsonutils.Attr2Map;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.CharacterModel;
import com.mud.game.worlddata.db.models.EventData;
import com.mud.game.worlddata.db.models.WorldNpc;
import com.mud.game.worlddata.db.models.WorldObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.yeauty.pojo.Session;

import java.util.*;

public class WorldNpcObjectManager {

    public static WorldNpcObject build(WorldNpc template) throws JsonProcessingException {
        WorldNpcObject obj = new WorldNpcObject();
        obj.setDataKey(template.getDataKey());
        obj.setDescription(template.getDescription());
        obj.setLocation(template.getLocation());
        // 初始化npc信息
        // 根据注册的信息设置角色信息
        obj.setName(template.getName());
        obj.setGender(template.getGender());
        obj.setArm(20);
        obj.setBody(20);
        obj.setBone(20);
        obj.setSmart(20);
        obj.setLooks(20);
        obj.setLucky(20);
        // 从玩家模版加载初始化信息
        CharacterModel npcTemplate = DbMapper.characterModelRepository.findCharacterModelByDataKey(template.getModel());
        obj.setCustomerAttr(Attr2Map.transform(npcTemplate.getAttrs()));
        obj.setTitle(template.getTitle());
        obj.setSchoolTitle(template.getSchoolTitle());
        obj.setSchool(template.getSchool());
        // 玩家信息的初始化设置
        obj.setAfter_arm(0);
        obj.setAfter_body(0);
        obj.setAfter_bone(0);
        obj.setAfter_smart(0);
        obj.setAfter_looks(0);
        obj.setAfter_lucky(0);
        obj.setShowCondition(template.getShowCondition());
        // TODO 加载默认技能信息，
        // TODO 加载默认装备信息
        // 加载掉落信息
        bindLootList(obj);
        // 加载绑定的事件
        bindEvents(obj);
        // 把npc放到房间内
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(obj.getLocation());
        WorldRoomObjectManager.updateNpc(room, obj);
        return obj;
    }

    public static void update(WorldNpcObject obj, WorldNpc template) throws JsonProcessingException {
        obj.setDataKey(template.getDataKey());
        obj.setDescription(template.getDescription());
        obj.setLocation(template.getLocation());
        // 初始化npc信息
        // 根据注册的信息设置角色信息
        obj.setName(template.getName());
        obj.setGender(template.getGender());
        obj.setArm(20);
        obj.setBody(20);
        obj.setBone(20);
        obj.setSmart(20);
        obj.setLooks(20);
        obj.setLucky(20);
        // 从玩家模版加载初始化信息
        CharacterModel npcTemplate = DbMapper.characterModelRepository.findCharacterModelByDataKey(template.getModel());
        obj.setCustomerAttr(Attr2Map.transform(npcTemplate.getAttrs()));
        obj.setTitle(template.getTitle());
        obj.setSchoolTitle(template.getSchoolTitle());
        obj.setSchool(template.getSchool());
        // 玩家信息的初始化设置
        obj.setAfter_arm(0);
        obj.setAfter_body(0);
        obj.setAfter_bone(0);
        obj.setAfter_smart(0);
        obj.setAfter_looks(0);
        obj.setAfter_lucky(0);
        obj.setShowCondition(template.getShowCondition());
        // TODO 加载默认技能信息，
        // TODO 加载默认装备信息
        // 加载掉落信息
        bindLootList(obj);
        // 加载绑定的事件
        bindEvents(obj);
        // 把npc放到房间内
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(obj.getLocation());
        WorldRoomObjectManager.updateNpc(room, obj);
    }

    public static void onPlayerLook(WorldNpcObject npc, PlayerCharacter playerCharacter, Session session) throws JsonProcessingException {
        /*
        * @ 当玩家查看NPC的时候返回NPC信息和可用命令
        * */
        Map<String, Object> lookMessage = new HashMap<>();
        NpcAppearance appearance = new NpcAppearance(npc);
        // 设置玩家可以对此物体执行的命令
        appearance.setCmds(WorldNpcObjectManager.getAvailableCommands(npc, playerCharacter));
        lookMessage.put("look_obj", appearance);
        session.sendText(JsonResponse.JsonStringResponse(lookMessage));
    }


    public static List<Map<String, Object>> getAvailableCommands(WorldNpcObject npc, PlayerCharacter playerCharacter){
        /*
         * @ 获取NPC可执行的命令
         * @ NPC对应的命令还是比较多的，不同身份的NPC对不同玩家会有不通的可执行命令
         * */
        List<Map<String, Object>> cmds = new ArrayList<>();
        // TODO: 拜师命令
        // TODO: 交易命令
        // TODO: 出师命令
        // TODO: 攻击命令
        // TODO: 切磋命令
        // TODO：师门任务命令
        // TODO: 副本传送命令
        // TODO：地图传送命令
//        Map<String, Object> cmd = new HashMap<>();
//        cmds.add(cmd);
        return cmds;
    }

    private static void bindEvents(WorldNpcObject obj){
        /*
         * 绑定NPC对应的事件（可用命令）
         * */
        Iterable<EventData> eventData = DbMapper.eventDataRepository.findEventDataByTriggerObject(obj.getDataKey());
        Set<String> events = new HashSet<>();
        for(EventData event : eventData){
            events.add(event.getDataKey());
        }
        obj.setEvents(events);
    }

    private static void bindLootList(WorldNpcObject obj){
        /*
        * @ TODO:设置物品NPC死亡掉落表
        * */
        ;
    }


}
