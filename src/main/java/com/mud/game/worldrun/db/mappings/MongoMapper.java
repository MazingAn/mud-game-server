package com.mud.game.worldrun.db.mappings;

import com.mud.game.object.typeclass.GemObject;
import com.mud.game.object.typeclass.WorldObjectCreator;
import com.mud.game.object.typeclass.WorldObjectObject;
import com.mud.game.worlddata.db.models.WorldNpc;
import com.mud.game.worldrun.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoMapper<F, S> {
    /*
    * 这是所有的仓库映射
    * 仓库会在这里被全部自动实现并开放出去
    * 为了绕开Spring boot 不能对全局静态变量进行@Autowried操作，所有仓库的@AutoWried动作都放在了Setter方法内
    * */

    public static WorldAreaObjectRepository worldAreaObjectRepository;
    public static WorldRoomObjectRepository worldRoomObjectRepository;
    public static WorldExitObjectRepository worldExitObjectRepository;
    public static PlayerRepository playerRepository;
    public static PlayerCharacterRepository playerCharacterRepository;
    public static WorldObjectObjectRepository worldObjectObjectRepository;
    public static WorldObjectCreatorRepository worldObjectCreatorRepository;
    public static WorldNpcObjectRepository worldNpcObjectRepository;
    public static SkillObjectRepository skillObjectRepository;
    public static GemObjectRepository gemObjectRepository;
    public static EquipmentObjectRepository equipmentObjectRepository;

    @Autowired
    public void setWorldAreaObjectRepository(WorldAreaObjectRepository worldAreaObjectRepository) {
        MongoMapper.worldAreaObjectRepository = worldAreaObjectRepository;
    }

    @Autowired
    public void setWorldRoomObjectRepository(WorldRoomObjectRepository worldRoomObjectRepository) {
        MongoMapper.worldRoomObjectRepository = worldRoomObjectRepository;
    }

    @Autowired
    public void setWorldExitObjectRepository(WorldExitObjectRepository worldExitObjectRepository) {
        MongoMapper.worldExitObjectRepository = worldExitObjectRepository;
    }

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {
        MongoMapper.playerRepository = playerRepository;
    }

    @Autowired
    public void setPlayerCharacterRepository(PlayerCharacterRepository playerCharacterRepository) {
        MongoMapper.playerCharacterRepository = playerCharacterRepository;
    }

    @Autowired
    public void setWorldObjectObjectRepository(WorldObjectObjectRepository worldObjectObjectRepository) {
        MongoMapper.worldObjectObjectRepository = worldObjectObjectRepository;
    }

    @Autowired
    public void setWorldObjectCreatorRepository(WorldObjectCreatorRepository worldObjectCreatorRepository) {
        MongoMapper.worldObjectCreatorRepository = worldObjectCreatorRepository;
    }

    @Autowired
    public void setWorldNpcObjectRepository(WorldNpcObjectRepository worldNpcObjectRepository) {
        MongoMapper.worldNpcObjectRepository = worldNpcObjectRepository;
    }

    @Autowired
    public void setSkillObjectRepository(SkillObjectRepository skillObjectRepository) {
        MongoMapper.skillObjectRepository = skillObjectRepository;
    }

    @Autowired
    public void setGemObjectRepository(GemObjectRepository gemObjectRepository) {
        MongoMapper.gemObjectRepository = gemObjectRepository;
    }

    @Autowired
    public void setEquipmentObjectRepository(EquipmentObjectRepository equipmentObjectRepository) {
        MongoMapper.equipmentObjectRepository = equipmentObjectRepository;
    }
}

