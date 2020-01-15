package com.mud.game.handler;

import com.mud.game.structs.EmbeddedCommand;

import java.util.HashMap;
import java.util.Map;

public class RoomCommandHandler {
    public static Map<String, EmbeddedCommand> roomCommandSet = new HashMap<>();

    public static void initRoomCommandSet(){
        roomCommandSet.put("mining", new EmbeddedCommand("挖矿", "mining", ""));
        roomCommandSet.put("collect", new EmbeddedCommand("采药", "collect", ""));
        roomCommandSet.put("fishing", new EmbeddedCommand("钓鱼", "fishing", ""));
        roomCommandSet.put("lit", new EmbeddedCommand("照明", "mining", ""));
        roomCommandSet.put("burn", new EmbeddedCommand("点燃", "mining", ""));
    }
}
