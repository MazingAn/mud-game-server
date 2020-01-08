package com.mud.game.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yeauty.pojo.Session;

import java.util.Map;

public abstract class BaseCommand {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String key;
    private Object caller;
    private Session session;
    private JSONObject args;

    public BaseCommand(String key, Object caller, JSONObject args, Session session) {
        this.key = key;
        this.caller = caller;
        this.args = args;
        this.session = session;
        //如果命令的调用者有选定的目标，则设置；否则为null
        try {
            execute();
        }catch (Exception e){
            logger.error("在执行命令:"+getKey()+"的时候出现错误， 参数为：" + args.toString());
            e.printStackTrace();
        }
    }

    public abstract void execute() throws JSONException, JsonProcessingException;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getCaller() {
        return caller;
    }

    public void setCaller(Object caller) {
        this.caller = caller;
    }

    public JSONObject getArgs() {
        return args;
    }

    public void setArgs(JSONObject args) {
        this.args = args;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
