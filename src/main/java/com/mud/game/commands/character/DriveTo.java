package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldRoomObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

/**
 * 玩家通过驾驶坐骑，直接飞往某处
 *
 * 请求示例：
 * {
 *   "cmd": "goto_room",
 *   "args": "#897987897" //目标房间的ID
 * }
 * */
public class DriveTo extends BaseCommand {


    public DriveTo(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        Session session = getSession();
        String roomId = args.getString("args");
        // 只有玩家装备坐骑的时候这个方法才能使用，而且还是延时调用
        WorldRoomObject roomObject = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectById(roomId);
        PlayerCharacterManager.moveTo(caller, roomObject.getDataKey());
    }
}
