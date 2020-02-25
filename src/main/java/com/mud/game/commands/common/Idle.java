package com.mud.game.commands.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

public class Idle extends BaseCommand {
    /*
    * @ 心跳包，用于前端监测服务器状态
    * */
    public Idle(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException, JsonProcessingException {
        Session session = getSession();
        session.sendText("");
    }
}
