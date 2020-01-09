package com.mud.game.object.manager;

import com.mud.game.handler.ConditionHandler;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldExitObject;
import com.mud.game.object.typeclass.WorldRoomObject;
import com.mud.game.worlddata.db.models.WorldExit;
import com.mud.game.worldrun.db.mappings.MongoMapper;

public class WorldExitObjectManager {

    public static final String reverseExitPrefix = "reverse";

    /*
    * @ 新建WrodlExit
    * */
    public static WorldExitObject build(WorldExit template, boolean reverse){
        // 新建出口
        WorldExitObject obj = new WorldExitObject();
        if(reverse){
            obj.setDataKey(reverseExitPrefix+template.getDataKey());
            obj.setLocation(template.getDestination());
            obj.setDestination(template.getLocation());
        }else{
            obj.setDataKey(template.getDataKey());
            obj.setLocation(template.getLocation());
            obj.setDestination(template.getDestination());
        }
        obj.setDescription(template.getDescription());
        obj.setName(template.getName());
        obj.setVerb(template.getVerb());
        obj.setLocked(template.isLocked());
        obj.setLockDescription(template.getLockDescription());
        obj.setUnlockDescription(template.getUnlockDescription());
        obj.setUnlockCondition(template.getUnlockCondition());
        obj.setTwoWay(template.isTwoWay());
        obj.setShowCondition(template.getShowCondition());
        // 把出口放到房间内
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(obj.getLocation());
        WorldRoomObjectManager.updateExit(room, obj);
        return obj;
    }

    /*
     * @更新WorldExit
     */
    public static void update(WorldExitObject obj, WorldExit template, boolean reverse){
        if(reverse){
            obj.setLocation(template.getDestination());
            obj.setDestination(template.getLocation());
        }else{
            obj.setLocation(template.getLocation());
            obj.setDestination(template.getDestination());
        }
        obj.setDescription(template.getDescription());
        obj.setName(template.getName());
        obj.setVerb(template.getVerb());
        obj.setLocked(template.isLocked());
        obj.setLockDescription(template.getLockDescription());
        obj.setUnlockDescription(template.getUnlockDescription());
        obj.setUnlockCondition(template.getUnlockCondition());
        obj.setTwoWay(template.isTwoWay());
        obj.setShowCondition(template.getShowCondition());
        // 把出口放到房间内
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(obj.getLocation());
        WorldRoomObjectManager.updateExit(room, obj);
    }

    public static boolean playerCharacterCanTranverse(PlayerCharacter playerCharacter, WorldExitObject exit){
        /*
        * @ 检查当前玩家能否通过这个出口
        * */
        if(!exit.getUnlockCondition().equals("".trim())){
            return ConditionHandler.matchCondition(exit.getUnlockCondition(), playerCharacter);
        }
        return false;
    }


}
