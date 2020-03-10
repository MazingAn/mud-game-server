package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

/**
 * 玩家移动到某一个房间（通过出口）
 * 请求示例:
 * <pre>
 *    {
 *        "cmd": "goto",
 *        "args": "exit_id" //要通过的出口的ID
 *    }
 * </pre>
 * */

public class GotoRoom extends BaseCommand {
    public GotoRoom(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException, JsonProcessingException {
        JSONObject args = getArgs();
        Session session = getSession();
        PlayerCharacter playerCharacter = (PlayerCharacter) getCaller();
        String exitId = args.getString("args");
        long start = System.currentTimeMillis();
        PlayerCharacterManager.gotoRoom(playerCharacter, exitId, session);
        long end = System.currentTimeMillis();
        System.out.println("从一个房间移动到另一个房间，耗费时间："+(end-start)+"ms");
    }
}
