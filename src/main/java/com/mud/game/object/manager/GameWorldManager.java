package com.mud.game.object.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.handler.ConditionHandler;
import com.mud.game.object.supertypeclass.WorldObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.object.typeclass.WorldRoomObject;
import com.mud.game.structs.ObjectMoveInfo;
import com.mud.game.structs.SimplePlayerCharacter;
import com.mud.game.utils.resultutils.GameWords;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.*;

public class GameWorldManager {
    public static void onPlayerCharacterDisconnect(PlayerCharacter playerCharacter) throws JsonProcessingException {
        String name = playerCharacter.getName();
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(playerCharacter.getLocation());
        Set<String> playersInRoom = room.getPlayers();
        playersInRoom.remove(playerCharacter.getId());
        room.setPlayers(playersInRoom);

        Map<String, Object> offlineNotice = new HashMap<>();
        String offlineMessage = String.format(GameWords.PLAYER_OFFLINE, name);
        ObjectMoveInfo moveInfo = new ObjectMoveInfo("players", Arrays.asList(new SimplePlayerCharacter[]{new SimplePlayerCharacter(playerCharacter)}));
        offlineNotice.put("obj_moved_out", moveInfo.getInfo());
        offlineNotice.put("msg", offlineMessage);
        MongoMapper.worldRoomObjectRepository.save(room);
        WorldRoomObjectManager.broadcast(room, offlineNotice, playerCharacter.getId());
    }

    public static boolean isVisibleForPlayerCharacter(Object object, PlayerCharacter playerCharacter){
        /*
         * @ 检查物体是不是应该显示给当前玩家
         * */
        WorldObject worldObject = (WorldObject) object;
        try{
            if (!worldObject.getShowCondition().equals("".trim())) {
                return ConditionHandler.matchCondition(worldObject.getShowCondition(), playerCharacter);
            }
        }catch(Exception e){
            return true;
        }
        return true;
    }

    public static boolean isNpcVisibleForPlayerCharacter(WorldNpcObject object, PlayerCharacter playerCharacter) {
        /*
        * @ 检查npc是不是应该现实给玩家
        * */
        if (!object.getShowCondition().equals("".trim())) {
            return ConditionHandler.matchCondition(object.getShowCondition(), playerCharacter);
        }
        return true;
    }

}
