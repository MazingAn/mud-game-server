package com.mud.game.commands.unlogin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.object.account.Player;
import com.mud.game.object.manager.PlayerManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;


/**
 * 注册账户命令
 *
 * <pre>
 *     请求示例：
 *      {   "cmd": "create",
 *           "args": {
 *              "playername":"test001"
 *              "password":"********",
 *              "connect":true
 *              }
 * *        }
 * </pre>
 * */
public class Create extends BaseCommand {


    public Create(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        String username = this.getArgs().getString("playername");
        String password = this.getArgs().getString("password");
        boolean connect = this.getArgs().getBoolean("connect");
        Session session = this.getSession();
        Player player = PlayerManager.create(username, password, session);
        if(player != null && connect){
            PlayerManager.login(username,password, session);
        }
    }
}
