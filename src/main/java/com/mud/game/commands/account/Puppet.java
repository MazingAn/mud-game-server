package com.mud.game.commands.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

public class Puppet extends BaseCommand {
    public Puppet(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }


    /**
     * 把一个玩家角色放入游戏中
     *
     * 请求示例：
     * <pre>
     * {
     *      "cmd":"puppet",
     *      "args":"5e6702e8c94709756f1dd1d5"
     * }
     * </pre>
     *
     * */
    @Override
    public void execute() throws JSONException, JsonProcessingException {
        String playerCharacterId = getArgs().getString("args");
        PlayerCharacterManager.puppet(playerCharacterId, getSession());
    }
}
