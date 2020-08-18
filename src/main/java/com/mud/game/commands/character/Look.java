package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.*;
import com.mud.game.object.typeclass.*;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;


/**
 * 玩家查看游戏世界中的其他对象
 * 返回这个对象的描述
 *
 * 使用示例：
 * <pre>
 *      {
 *          "cmd" : "look",
 *          "args" : "target_dbref" //被查看者的ID
 *      }
 * </pre>
 *
 * */

public class Look extends BaseCommand {
    public Look(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
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
        }else if(MongoMapper.worldNpcObjectRepository.existsById(targetId)){
            WorldNpcObject target = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(targetId);
            WorldNpcObjectManager.onPlayerLook(target, playerCharacter, session);
        }else if(MongoMapper.playerCharacterRepository.existsById(targetId)){
            PlayerCharacter target = MongoMapper.playerCharacterRepository.findPlayerCharacterById(targetId);
            PlayerCharacterManager.onPlayerLook(target, playerCharacter, session);
        }else if(MongoMapper.equipmentObjectRepository.existsById(targetId)){
            EquipmentObject target = MongoMapper.equipmentObjectRepository.findEquipmentObjectById(targetId);
            EquipmentObjectManager.onPlayerLook(target, playerCharacter, session);
        }else if(MongoMapper.skillBookObjectRepository.existsById(targetId)){
            SkillBookObject target = MongoMapper.skillBookObjectRepository.findSkillBookObjectById(targetId);
            SkillBookObjectManager.onPlayerLook(target, playerCharacter, session);
        }else if(MongoMapper.normalObjectObjectRepository.existsById(targetId)){
            NormalObjectObject target = MongoMapper.normalObjectObjectRepository.findNormalObjectObjectById(targetId);
            NormalObjectObjectManager.onPlayerLook(target, playerCharacter, session);
        }else if(MongoMapper.gemObjectRepository.existsById(targetId)){
            GemObject target = MongoMapper.gemObjectRepository.findGemObjectById(targetId);
            GemObjectManager.onPlayerLook(target, playerCharacter, session);
        }else{
            playerCharacter.msg(new ToastMessage("没有找到这件物品的详细信息！"));
        }
        long end = System.currentTimeMillis();
        System.out.println("查看一个物体，耗费时间："+(end-start)+"ms");
    }
}
