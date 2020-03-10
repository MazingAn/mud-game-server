package com.mud.game.commands.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;



/**
 * 创建角色命令
 *
 * <pre>
 *     请求示例：
 *      {
 *          cmd": "char_create",
 *          "args":
 *          {
 *              "name":"俞碧应",
 *              "gender":"男",
 *              "innate_values":[20,20,20,20]
 *          }
 *      }
 * </pre>
 * */

public class CharCreate extends BaseCommand {

    public CharCreate(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException, JsonProcessingException {
        String name = getArgs().getString("name");
        String gender = getArgs().getString("gender");
        JSONArray innateValues = getArgs().getJSONArray("innate_values");
        int arm = innateValues.getInt(0);
        int bone = innateValues.getInt(1);
        int body = innateValues.getInt(2);
        int smart = innateValues.getInt(3);
        PlayerCharacter playerCharacter = PlayerCharacterManager.create(name, gender, arm, bone, body, smart, getSession());
        if(playerCharacter != null) {
            PlayerCharacterManager.puppet(playerCharacter.getId(), getSession());
        }
    }
}
