package com.mud.game.object.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.condition.ConditionHandler;
import com.mud.game.messages.MsgMessage;
import com.mud.game.object.account.Player;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldObjectCreator;
import com.mud.game.object.typeclass.WorldObjectObject;
import com.mud.game.object.typeclass.WorldRoomObject;
import com.mud.game.structs.GameObjectAppearance;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.EventData;
import com.mud.game.worlddata.db.models.LootList;
import com.mud.game.worlddata.db.models.WorldObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.yeauty.pojo.Session;

import java.util.*;

public class WorldObjectCreatorManager {

    public static WorldObjectCreator build(WorldObject template){
        WorldObjectCreator obj = new WorldObjectCreator();
        obj.setDataKey(template.getDataKey());
        obj.setDescription(template.getDescription());
        obj.setName(template.getName());
        obj.setIcon(template.getIcon());
        obj.setLocation(template.getLocation());
        obj.setActionName(template.getActionName());
        obj.setLootNumber(template.getLootNumber());
        obj.setLootOnce(template.isLootOnce());
        obj.setRefreshTime(template.getRefreshTime());
        obj.setShowCondition(template.getShowCondition());
        bindLootList(obj);
        bindEvents(obj);
        // 把物品生成器放到房间内
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(obj.getLocation());
        WorldRoomObjectManager.updateCreators(room, obj);
        return obj;
    }

    public static void update(WorldObjectCreator obj, WorldObject template){
        obj.setDataKey(template.getDataKey());
        obj.setDescription(template.getDescription());
        obj.setName(template.getName());
        obj.setIcon(template.getIcon());
        obj.setLocation(template.getLocation());
        obj.setActionName(template.getActionName());
        obj.setLootNumber(template.getLootNumber());
        obj.setLootOnce(template.isLootOnce());
        obj.setRefreshTime(template.getRefreshTime());
        obj.setShowCondition(template.getShowCondition());
        bindLootList(obj);
        bindEvents(obj);
        // 把物品生成器放到房间内
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(obj.getLocation());
        WorldRoomObjectManager.updateCreators(room, obj);
    }

    private static void bindEvents(WorldObjectCreator obj){
        /*
         * 绑定世界物体对应的事件
         * */
        Iterable<EventData> eventData = DbMapper.eventDataRepository.findEventDataByTriggerObject(obj.getDataKey());
        Set<String> events = new HashSet<>();
        for(EventData event : eventData){
            events.add(event.getDataKey());
        }
        obj.setEvents(events);
    }

    public static void onPlayerLook(WorldObjectCreator obj, PlayerCharacter playerCharacter, Session session) throws JsonProcessingException {
        /*
         * @ 当玩家查看游戏世界内的物体生成器的时候返回物体信息和可执行的命令（操作）
         * */
        Map<String, Object> lookMessage = new HashMap<>();
        GameObjectAppearance appearance = new GameObjectAppearance(obj);
        // 设置玩家可以对此物体执行的命令
        appearance.setCmds(getAvailableCommands(obj, playerCharacter));
        lookMessage.put("look_obj", appearance);
        session.sendText(JsonResponse.JsonStringResponse(lookMessage));
    }


    private static List<Map<String, Object>> getAvailableCommands(WorldObjectCreator obj, PlayerCharacter playerCharacter){
        /*
         * @ 获取物体生成器的可操作命令
         * @ 对于物品生成器来说 只有一个生成命令
         * @ 返回的命令列表只包含loot命令以及配套参数，玩家执行loot命令之后才进行物品的获取
         * */
        List<Map<String, Object>> cmds = new ArrayList<>();
        Map<String, Object> cmd = new HashMap<>();
        cmd.put("cmd", "loot");
        cmd.put("name", obj.getActionName());
        cmd.put("args", obj.getId());
        cmds.add(cmd);
        return cmds;
    }


    private static void bindLootList(WorldObjectCreator obj){
        /*
        * @ 设置物品生成器可以生成的物品列表记录
        * */
        Iterable<LootList> records = DbMapper.lootListRepository.getLootListsByProvider(obj.getDataKey());
        Set<LootList> lootLists = new HashSet<>();
        for(LootList loot : records){
            lootLists.add(loot);
        }
        obj.setLootLists(lootLists);
    }

    public static void onPlayerLoot(WorldObjectCreator creator, PlayerCharacter playerCharacter, Session session) throws JsonProcessingException {
        /*
        * @ 玩家从物品生成器获得物品
        * */
        Set<LootList> lootLists = creator.getLootLists();
        for(LootList lootList : lootLists) {
            // 玩家开始获取物品
            if (ConditionHandler.matchCondition(lootList.getLootCondition(), playerCharacter)){
                //TODO: 玩家接受生成的物品
                session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("这特么的还没实现！但是现在假设你已经获得了东西。")));
            }
        }
    }

}
