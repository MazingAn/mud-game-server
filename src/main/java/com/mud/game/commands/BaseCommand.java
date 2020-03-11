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


/**
 * 所有命令类的基类
 *
 * <p>
 *     定义了基本的命令执行方式，实现了默认构造函数，要求用户必须取得命令key， 调用者， 请求参数 ，当前session
 * </p>
 *
 * @version v1.0
 * @author 安明哲
 * @since v1.0
 * */
public abstract class BaseCommand {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** 命令对应的key： 客户端的请求总是以这个key作为cmd参数的值，由此才能反射到对应的命令类并执行 */
    private String key;
    /** 命令的调用者： 可以是任何物体，但一般情况下是PlayerCharacter类型的对象 */
    private Object caller;
    /** 命令调用者的session： 保证可以通过这个session返回命令执行信息到对应的客户端 */
    private Session session;
    /** 命令执行时刻需要的输入参数 */
    private JSONObject args;

    /**
     * 构造函数，获取传入的参数，并执行命令
     * @param key 命令绑定的key
     * @param caller 命令的调用者
     * @param args 命令参数
     * @param session 命令信息的返回通道
     * */
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

    /**
     * 虚函数，所有命令类必须有自己的实现
     * 一般情况下，命令会在这个函数里解析命令参数，并执行具体的游戏逻辑，返回信息到客户端
     *
     * 在返回Json字符串给客户端的时候可能引发的异常
     * @throws JSONException 在解析命令参数是可能引发的json解析异常
     * */
    public abstract void execute() throws JSONException;

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
