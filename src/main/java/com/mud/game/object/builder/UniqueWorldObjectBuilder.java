package com.mud.game.object.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.events.EventTriggerType;
import com.mud.game.object.manager.*;
import com.mud.game.object.typeclass.*;
import com.mud.game.worlddata.db.TypeClassMapper;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.*;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.Iterator;

public class UniqueWorldObjectBuilder {

    public static void buildUniqueObjects(String type) {
        /*
         * 建造世界物体（区域，房间，出口，NPC）
         * 这类物体有一个共同点，就是不能重复
         */
        switch (type){
            /*
             * @创建区域
             */
            case "WorldAreaObject":
                Iterable<WorldArea> areas = DbMapper.worldAreaRepository.findAll();
                for (WorldArea template : areas) {
                    WorldAreaObject area = null;
                    if (MongoMapper.worldAreaObjectRepository.existsByDataKey(template.getDataKey())) {
                        area = MongoMapper.worldAreaObjectRepository.findWorldAreaObjectByDataKey(template.getDataKey());
                        WorldAreaObjectManager.update(area, template);
                    } else {
                        area = WorldAreaObjectManager.build(template);
                    }
                    MongoMapper.worldAreaObjectRepository.save(area);
                }
                break;

            /*
            *@ 创建或更新房间
            * */
            case "WorldRoomObject":
                Iterable<WorldRoom> rooms = DbMapper.worldRoomRepository.findAll();
                for (WorldRoom template : rooms) {
                    WorldRoomObject room = null;
                    if (MongoMapper.worldRoomObjectRepository.existsByDataKey(template.getDataKey())) {
                        room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(template.getDataKey());
                        WorldRoomObjectManager.update(room, template);
                    } else {
                        room = WorldRoomObjectManager.build(template);
                    }
                    MongoMapper.worldRoomObjectRepository.save(room);
                }
                break;

            /*
             * @创建出口
             */
            case "WorldExitObject":
                Iterable<WorldExit> exits = DbMapper.worldExitRepository.findAll();
                for(WorldExit template: exits){
                    WorldExitObject exit = null;
                    if(MongoMapper.worldExitObjectRepository.existsByDataKey(template.getDataKey())){
                        exit = MongoMapper.worldExitObjectRepository.findWorldExitObjectByDataKey(template.getDataKey());
                        WorldExitObjectManager.update(exit, template, false);
                    }else{
                        exit = WorldExitObjectManager.build(template, false);
                    }
                    MongoMapper.worldExitObjectRepository.save(exit);
                    // 如果出口是双向出口，则还要再创建一次反向出口
                    if(template.isTwoWay()){
                        String reversExitDataKey = WorldExitObjectManager.reverseExitPrefix + exit.getDataKey();
                        WorldExitObject reverseExit = null;
                        if(MongoMapper.worldExitObjectRepository.existsByDataKey(reversExitDataKey)){
                            reverseExit = MongoMapper.worldExitObjectRepository.findWorldExitObjectByDataKey(reversExitDataKey);
                            WorldExitObjectManager.update(reverseExit, template, true);
                        }else{
                            reverseExit = WorldExitObjectManager.build(template, true);
                        }
                        MongoMapper.worldExitObjectRepository.save(reverseExit);
                    }
                }
                break;

            /*
            *  @创建世界物体
            * */
            case "WorldObjectObject":
                Iterable<WorldObject> templates = DbMapper.worldObjectRepository.findWorldObjectsByTypeClass(TypeClassMapper.WORLD_OBJECT);
                for(WorldObject template: templates) {
                    WorldObjectObject object = null;
                    if(MongoMapper.worldObjectObjectRepository.existsByDataKey(template.getDataKey())){
                        object = MongoMapper.worldObjectObjectRepository.findWorldObjectObjectByDataKey(template.getDataKey());
                        WorldObjectObjectManager.update(object, template);
                    }else{
                        object = WorldObjectObjectManager.build(template);
                    }
                    MongoMapper.worldObjectObjectRepository.save(object);
                }
                break;

            /*
            * @ 创建物品生成器
            * */
            case "WorldObjectCreator":
                Iterable<WorldObject> creatorTemplates = DbMapper.worldObjectRepository.findWorldObjectsByTypeClass(TypeClassMapper.WORLD_OBJECT_CREATOR);
                for(WorldObject template: creatorTemplates) {
                    WorldObjectCreator object = null;
                    if(MongoMapper.worldObjectCreatorRepository.existsByDataKey(template.getDataKey())){
                        object = MongoMapper.worldObjectCreatorRepository.findWorldObjectCreatorByDataKey(template.getDataKey());
                        WorldObjectCreatorManager.update(object, template);
                    }else{
                        object = WorldObjectCreatorManager.build(template);
                    }
                    MongoMapper.worldObjectCreatorRepository.save(object);
                }
                break;

            /*
            * @ 创建npc
            * */
            case "WorldNpcObject":
                Iterable<WorldNpc> npcTemplates = DbMapper.worldNpcRepository.findAll();
                for(WorldNpc template: npcTemplates) {
                    WorldNpcObject object = null;
                    if(MongoMapper.worldNpcObjectRepository.existsByDataKey(template.getDataKey())){
                        object = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectByDataKey(template.getDataKey());
                        WorldNpcObjectManager.update(object, template);
                    }else{
                        object = WorldNpcObjectManager.build(template);
                    }
                    MongoMapper.worldNpcObjectRepository.save(object);
                }
                break;
        }
    }

}
