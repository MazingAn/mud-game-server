package com.mud.game.server;

import com.mud.game.handler.CommandSetHandler;
import com.mud.game.handler.ConditionHandler;
import com.mud.game.handler.SchoolHandler;
import com.mud.game.object.builder.UniqueWorldObjectBuilder;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.GameSetting;

public class ServerManager {

    public static GameSetting gameSetting;

    public static void start(){
        // 拿到游戏配置信息
        try {
            gameSetting = DbMapper.gameSettingRepository.findAll().iterator().next();
        }catch (Exception e){
            e.printStackTrace();
            gameSetting = null;
        }
        // 初始化游戏的Handler
        ConditionHandler.initConditionHandler();
        CommandSetHandler.initUnLoginCommandSet();
        CommandSetHandler.initAccountCommandSet();
        CommandSetHandler.initPlayerCharacterCommandSet();
        SchoolHandler.initSchoolHandler();
    }

    public static void stop(){
    }

    public static void restart(){
        start();
        stop();
    }

    public static void update(){
    }

    public static void apply(){
        UniqueWorldObjectBuilder.buildUniqueObjects("WorldAreaObject");
        UniqueWorldObjectBuilder.buildUniqueObjects("WorldRoomObject");
        UniqueWorldObjectBuilder.buildUniqueObjects("WorldExitObject");
        UniqueWorldObjectBuilder.buildUniqueObjects("WorldObjectObject");
        UniqueWorldObjectBuilder.buildUniqueObjects("WorldObjectCreator");
        UniqueWorldObjectBuilder.buildUniqueObjects("WorldNpcObject");
    }

}
