package com.mud.game.server;

import com.mud.game.handler.*;
import com.mud.game.object.builder.UniqueWorldObjectBuilder;
import com.mud.game.object.manager.HangUpManager;
import com.mud.game.object.manager.WorldNpcObjectManager;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.object.updator.GameObjectUpdator;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.GameSetting;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.Timer;

public class ServerManager {

    public static GameSetting gameSetting;

    public static void start() {
        // 拿到游戏配置信息
        System.out.println("正在加载游戏配置...");
        try {
            gameSetting = DbMapper.gameSettingRepository.findAll().iterator().next();
        } catch (Exception e) {
            System.out.println("服务器基础设置找到！！！");
            gameSetting = null;
        }


        System.out.println("正在加载NPC...");
        // 复活所有NPC
        for (WorldNpcObject npc : MongoMapper.worldNpcObjectRepository.findAll()) {
            npc.setHp(npc.getMax_hp());
            npc.setName(npc.getName().replaceAll("的尸体", ""));
            MongoMapper.worldNpcObjectRepository.save(npc);
        }

        System.out.println("初始化指令系统...");
        // 初始化游戏的Handler...
        // 初始化命令集合...
        ConditionHandler.initConditionHandler();
        CommandSetHandler.initUnLoginCommandSet();
        CommandSetHandler.initAccountCommandSet();
        CommandSetHandler.initPlayerCharacterCommandSet();
        CommandSetHandler.initPlayerCharacterDieCommandSet();
        // 初始化缓存信息...
        System.out.println("初始化缓存信息...");
        SchoolHandler.initSchoolHandler();
        SkillTypeHandler.initSkillTypeHandler();
        SkillFunctionHandler.initSkillFunctionSet();
        SkillPositionHandler.initSkillPositionHandler();
        EquipmentPositionHandler.initEquipmentPositionHandler();
        RoomCommandHandler.initRoomCommandSet();
        QuestObjectiveTypeHandler.initObjectiveTypeMapper();
        UnitHandler.initUnits();
        StatusHandler.initAttrMapping();
        EventTriggerTypeHandler.initEventTriggerType();
        EventActionTypeHandler.initEventActionType();
        SkillActionHandler.initSkillActionList();
        ObjectFunctionHandler.initObjectFunctionSet();
        HangUpManager.iniRandomObjectList();
        // 创建默认频道...
        System.out.println("创建默认频道...");
        ChannelHandler.initChannels();
        //创建npc游荡定时器
        WorldNpcObjectManager.worldNpcWanderRoom();
        System.out.println("服务器启动完成！");
    }

    public static void stop() {

    }

    public static void restart() {
        start();
        stop();
    }

    public static void update() {
    }

    public static void apply() {
        UniqueWorldObjectBuilder.buildUniqueObjects("WorldAreaObject");
        UniqueWorldObjectBuilder.buildUniqueObjects("WorldRoomObject");
        UniqueWorldObjectBuilder.buildUniqueObjects("WorldExitObject");
        UniqueWorldObjectBuilder.buildUniqueObjects("WorldObjectObject");
        UniqueWorldObjectBuilder.buildUniqueObjects("WorldObjectCreator");
        UniqueWorldObjectBuilder.buildUniqueObjects("WorldNpcObject");
        GameObjectUpdator.updateGameObject("Skill");
        GameObjectUpdator.updateGameObject("SkillBook");
    }

}
