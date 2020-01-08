package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.*;
import com.mud.game.object.supertypeclass.BaseGameObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.object.typeclass.WorldObjectCreator;
import com.mud.game.object.typeclass.WorldObjectObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

public class Look extends BaseCommand {
    public Look(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException, JsonProcessingException {
        JSONObject args = getArgs();
        Session session = getSession();
        PlayerCharacter playerCharacter = (PlayerCharacter) getCaller();
        String targetId = args.getString("args");
        long start = System.currentTimeMillis();
        if(MongoMapper.worldObjectObjectRepository.existsById(targetId)){
            WorldObjectObject target = MongoMapper.worldObjectObjectRepository.findWorldObjectObjectById(targetId);
            WorldObjectObjectManager.onPlayerLook(target, playerCharacter, session);
        }else if(MongoMapper.worldObjectCreatorRepository.existsById(targetId)){
            WorldObjectCreator target = MongoMapper.worldObjectCreatorRepository.findWorldObjectCreatorById(targetId);
            WorldObjectCreatorManager.onPlayerLook(target, playerCharacter, session);
        }
        else if(MongoMapper.worldNpcObjectRepository.existsById(targetId)){
            WorldNpcObject target = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(targetId);
            WorldNpcObjectManager.onPlayerLook(target, playerCharacter, session);
        }else if(MongoMapper.playerCharacterRepository.existsById(targetId)){
            PlayerCharacter target = MongoMapper.playerCharacterRepository.findPlayerCharacterById(targetId);
            PlayerCharacterManager.onPlayerLook(target, playerCharacter, session);
        }
        long end = System.currentTimeMillis();
        System.out.println("查看一个物体，耗费时间："+(end-start)+"ms");
    }
}
