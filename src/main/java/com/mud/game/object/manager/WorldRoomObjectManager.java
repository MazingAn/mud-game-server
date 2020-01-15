package com.mud.game.object.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.handler.RoomCommandHandler;
import com.mud.game.net.session.GameSessionService;
import com.mud.game.object.typeclass.*;
import com.mud.game.structs.EmbeddedCommand;
import com.mud.game.structs.GamePosition;
import com.mud.game.structs.ObjectMoveInfo;
import com.mud.game.structs.SimplePlayerCharacter;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.resultutils.GameWords;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.*;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.yeauty.pojo.Session;

import java.util.*;

public class WorldRoomObjectManager {

    public static WorldRoomObject build(WorldRoom template){
        /*
         * @ 新建WrodlRoom
         * */
        WorldRoomObject obj = new WorldRoomObject();
        obj.setDataKey(template.getDataKey());
        obj.setDescription(template.getDescription());
        obj.setName(template.getName());
        obj.setBackground(template.getBackground());
        obj.setHangUpCommand(template.getHangUpCommand());
        obj.setIcon(template.getIcon());
        obj.setLevel(template.getLevel());
        obj.setLocation(template.getLocation());
        obj.setPeaceful(template.isPeaceful());
        obj.setPosition(new GamePosition(template.getPosition()));
        bindEvents(obj);
        return obj;
    }


    public static void update(WorldRoomObject obj, WorldRoom template){
        /*
         * @更新WorldRoom
         */
        obj.setDescription(template.getDescription());
        obj.setDataKey(template.getDataKey());
        obj.setDescription(template.getDescription());
        obj.setName(template.getName());
        obj.setBackground(template.getBackground());
        obj.setHangUpCommand(template.getHangUpCommand());
        obj.setIcon(template.getIcon());
        obj.setLevel(template.getLevel());
        obj.setLocation(template.getLocation());
        obj.setPosition(new GamePosition(template.getPosition()));
        // 清空房间内部的东西
        obj.setThings(new HashSet<>());
        obj.setEvents(new HashSet<>());
        obj.setCreators(new HashSet<>());
        obj.setNpcs(new HashSet<>());
        bindEvents(obj);
    }


    public static void updateExit(WorldRoomObject room, WorldExitObject exit){
        /*
        * 为当前房间增加出口
        * */
        try{
            Set<String> exits = room.getExits();
            exits.add(exit.getDataKey());
            room.setExits(exits);
            MongoMapper.worldRoomObjectRepository.save(room);
        }catch (Exception e){
            System.out.println("出口："+ exit.getName() + "(" + exit.getDataKey() + ")" +"没有找到其对应的房间" + exit.getLocation());
        }
    }


    public static void updateThings(WorldRoomObject room, WorldObjectObject object) {
        /*
        * 为当前房间增加物品
        * */
        try {
            Set<String> objects = room.getThings();
            objects.add(object.getDataKey());
            room.setThings(objects);
            MongoMapper.worldRoomObjectRepository.save(room);
        }catch (Exception e){
            System.out.println("物品："+ object.getName() + "(" + object.getDataKey() + ")" +"没有找到其对应的房间" + object.getLocation());
        }
    }

    public static void updateCreators(WorldRoomObject room, WorldObjectCreator creator) {
        /*
         * 为当前房间增加物品生成器
         * */
        try{
            Set<String> creators = room.getCreators();
            creators.add(creator.getDataKey());
            room.setCreators(creators);
            MongoMapper.worldRoomObjectRepository.save(room);
        }catch (Exception e){
            System.out.println("物品生成器："+ creator.getName() + "(" + creator.getDataKey() + ")" +"没有找到其对应的房间" + creator.getLocation());
        }
    }

    public static void updateNpc(WorldRoomObject room, WorldNpcObject npc) {
        /*
        * 为房间内更新（添加）一个Npc
        * */
        try{
            Set<String> npcs = room.getNpcs();
            npcs.add(npc.getDataKey());
            room.setNpcs(npcs);
            MongoMapper.worldRoomObjectRepository.save(room);
        }catch (Exception e){
            System.out.println("NPC："+ npc.getName() + "(" + npc.getDataKey() + ")" +"没有找到其对应的房间" + npc.getLocation());
        }
    }

    public static void bindEvents(WorldRoomObject room){
        /*
        * 绑定房间对应的事件
        * */
        Iterable<EventData> eventData = DbMapper.eventDataRepository.findEventDataByTriggerObject(room.getDataKey());
        Set<String> events = new HashSet<>();
        for(EventData event : eventData){
            events.add(event.getDataKey());
        }
        room.setEvents(events);
    }

    public static  void onPlayerCharacterMove(PlayerCharacter playerCharacter, WorldRoomObject oldRoom, WorldRoomObject newRoom) throws JsonProcessingException {
        /*
        * @ 管理房间内玩家的移动
        * @ 1。在玩家移动的时候要给其他玩家发送玩家的动态
        * @ 2。玩家移动之后要更新房间内保存的玩家列表并增量更新到客户端
        * */
        if(!oldRoom.getId().equals(newRoom.getId())){
            String playerLeftMessage = String.format(GameWords.PLAYER_LEFT_ROOM, playerCharacter.getName(), oldRoom.getName(), newRoom.getName());
            String playerJoinMessage = String.format(GameWords.PLAYER_JOIN_ROOM, playerCharacter.getName(), oldRoom.getName(), newRoom.getName());
            SimplePlayerCharacter simplePlayerCharacter = new SimplePlayerCharacter(playerCharacter);
            ObjectMoveInfo playerMoveInfo = new ObjectMoveInfo("players", Arrays.asList(new SimplePlayerCharacter[]{simplePlayerCharacter}));
            Map<String, Object> oldRoomMessage = new HashMap<String, Object>();
            oldRoomMessage.put("msg", playerLeftMessage);
            oldRoomMessage.put("obj_moved_out", playerMoveInfo.getInfo());
            Map<String, Object> newRoomMessage = new HashMap<String, Object>();
            newRoomMessage.put("msg", playerJoinMessage);
            newRoomMessage.put("obj_moved_in", playerMoveInfo.getInfo());
            WorldRoomObjectManager.broadcast(oldRoom, oldRoomMessage, playerCharacter.getId());
            WorldRoomObjectManager.broadcast(newRoom, newRoomMessage, playerCharacter.getId());
        }

    }

    public static void broadcast(WorldRoomObject room, Object message, String excludeId) throws JsonProcessingException {
        /*
        * @ 房间内广播，发送信息给房间内的所有玩家
        * */
        Set<String> playerIds = room.getPlayers();
        for(String id : playerIds){//检测房间内所有的玩家
            if(!excludeId.equals(id)){
                Session session = GameSessionService.getSessionByCallerId(id);
                if(session != null){
                    session.sendText(JsonResponse.JsonStringResponse(message));
                }
            }
        }
    }

    public static void removeOfflinePlayer(WorldRoomObject room){
        /*
        * @ 删除房间内没有在线的玩家
        * */
        if(room!=null){
            Set<String> playerIds = room.getPlayers();
            for(String id : playerIds){
                //如果房间内的玩家没有session或者session为null，证明玩家已经下线，可以从房间移除玩家
                if(!GameSessionService.callerId2SessionMap.containsKey(id) || GameSessionService.getSessionByCallerId(id) == null){
                    Set<String> players = room.getPlayers();
                    players.remove(id);
                    room.setPlayers(players);
                }
            }
            MongoMapper.worldRoomObjectRepository.save(room);
        }
    }


    public static void triggerArriveAction(WorldRoomObject room, PlayerCharacter playerCharacter, Session session) throws JsonProcessingException, JSONException {
        /*
        * @如果房间绑定的有事件，触发
        * */
        if(!room.getEvents().isEmpty()){
            for(String eventKey: room.getEvents()){
                EventData event = DbMapper.eventDataRepository.findEventDataByDataKey(eventKey);
                GameEventManager.trigger(playerCharacter, event, session);
            }
        }
    }


    public static List<EmbeddedCommand> getAvailableCommands(WorldRoomObject room, PlayerCharacter playerCharacter){
        /*房间内可以执行的命令*/
        List<EmbeddedCommand> cmds = new ArrayList<>();
        String commandKey = room.getHangUpCommand();
        if(commandKey != null && RoomCommandHandler.roomCommandSet.containsKey(commandKey)){
            cmds.add(RoomCommandHandler.roomCommandSet.get(commandKey));
        }
        return cmds;
    }

}
