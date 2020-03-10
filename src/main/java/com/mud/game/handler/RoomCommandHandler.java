package com.mud.game.handler;

import com.mud.game.structs.EmbeddedCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * 房间命令缓存类
 *
 * 这个类中定义的房间命令将在服务器启动的时候被初始化  参见：{@link com.mud.game.server.ServerManager}
 *
 * */
public class RoomCommandHandler {


    /**
     *  房间命令缓存 key为技能命令的标识， value为对应的中文描述  如 {"mining": "挖矿"}
     * */
    public static Map<String, EmbeddedCommand> roomCommandSet = new HashMap<>();

    /** 外部方法  初始化房间命令缓存 这个方法总会在服务器启动的时候被调用  参见 {@link com.mud.game.server.ServerManager} {@code start} */
    public static void initRoomCommandSet(){
        roomCommandSet.put("mining", new EmbeddedCommand("挖矿", "mining", ""));
        roomCommandSet.put("collect", new EmbeddedCommand("采药", "collect", ""));
        roomCommandSet.put("fishing", new EmbeddedCommand("钓鱼", "fishing", ""));
        roomCommandSet.put("lit", new EmbeddedCommand("照明", "mining", ""));
        roomCommandSet.put("burn", new EmbeddedCommand("点燃", "mining", ""));
    }
}
