package com.mud.game.object.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.handler.ConditionHandler;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.object.typeclass.WorldObjectObject;
import com.mud.game.object.typeclass.WorldRoomObject;
import com.mud.game.structs.EmbeddedCommand;
import com.mud.game.structs.GameObjectAppearance;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.EventData;
import com.mud.game.worlddata.db.models.WorldObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.yeauty.pojo.Session;

import java.util.*;

public class WorldObjectObjectManager {

    public static WorldObjectObject build(WorldObject template){
        WorldObjectObject obj = new WorldObjectObject();
        obj.setDataKey(template.getDataKey());
        obj.setDescription(template.getDescription());
        obj.setName(template.getName());
        obj.setIcon(template.getIcon());
        obj.setLocation(template.getLocation());
        obj.setShowCondition(template.getShowCondition());
        bindEvents(obj);
        // 把物体放到房间内
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(obj.getLocation());
        WorldRoomObjectManager.updateThings(room, obj);
        return obj;
    }

    public static void update(WorldObjectObject obj, WorldObject template){
        obj.setDataKey(template.getDataKey());
        obj.setDescription(template.getDescription());
        obj.setName(template.getName());
        obj.setIcon(template.getIcon());
        obj.setLocation(template.getLocation());
        bindEvents(obj);
        // 把物体放到房间内
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(obj.getLocation());
        WorldRoomObjectManager.updateThings(room, obj);
    }

    public static void bindEvents(WorldObjectObject obj){
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

    public static void onPlayerLook(WorldObjectObject obj, PlayerCharacter playerCharacter, Session session) throws JsonProcessingException {
        /*
         * @ 当玩家查看游戏世界内的物体的时候返回物体信息和可执行的命令（操作）
         * */
        Map<String, Object> lookMessage = new HashMap<>();
        GameObjectAppearance appearance = new GameObjectAppearance(obj);
        // 设置玩家可以对此物体执行的命令
        appearance.setCmds(WorldObjectObjectManager.getAvailableCommands(obj, playerCharacter));
        lookMessage.put("look_obj", appearance);
        session.sendText(JsonResponse.JsonStringResponse(lookMessage));
    }

    public static List<EmbeddedCommand> getAvailableCommands(WorldObjectObject obj, PlayerCharacter playerCharacter){
        /*
        * @ 获取世界物体的可操作命令
        * @ 世界物体的可操作命令本质上是世界物体绑定的事件
        * @ 每一个绑定的事件都有对应的名字ActionName
        * @ 但是在配置的时候，也允许有同名的事件（ActionName相同）这个时候据需要预先判断
        * @ 对于同名的多个event，只为玩家显示一个，而这个一个是玩家肯定能够执行的
        * @ 判断的依据是event对应的triggerCondition可以生效（PS:配置中，总是能保证多个同名事件只有一个会生效）
        * */
        // 最重要返回的命令列表
        List<EmbeddedCommand> cmds = new ArrayList<>();
        // 获取物体绑定的事件(仅限于Action事件，也就是点击交互事件)
        Map<String, Set<EventData>>eventDataGroupByActionNameMap = new HashMap<>();
        for(String eventKey : obj.getEvents()) {
            //根据事件的名字对事件进行分组
            EventData eventData = DbMapper.eventDataRepository.findEventDataByDataKey(eventKey);
            if(!eventDataGroupByActionNameMap.containsKey(eventData.getActionName())){
                Set<EventData> events = new HashSet<>();
                events.add(eventData);
                eventDataGroupByActionNameMap.put(eventData.getActionName(), events);
            }else{
                Set<EventData> events = eventDataGroupByActionNameMap.get(eventData.getActionName());
                events.add(eventData);
                eventDataGroupByActionNameMap.put(eventData.getActionName(), events);
            }
        }
        // 根据分好组的事件，筛选出来可执行的event并转换为命令方式添加到可操作指令集中供客户端调用
        for(String actionName: eventDataGroupByActionNameMap.keySet()){
            Set<EventData> events = eventDataGroupByActionNameMap.get(actionName);
            playerCharacter.setRandomNumber(Math.random()*101);
            for(EventData eventData : events){
                if(ConditionHandler.matchCondition(eventData.getTriggerCondition(), playerCharacter)){
                    Map<String, String> args = new HashMap<>();
                    args.put("event", eventData.getDataKey());
                    args.put("dbref", playerCharacter.getId());
                    cmds.add(new EmbeddedCommand(eventData.getActionName(), "chose_action", args));
                }
            }
        }
        return cmds;
    }



}
