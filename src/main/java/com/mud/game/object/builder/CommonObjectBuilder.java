package com.mud.game.object.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.*;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.*;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Gem;
import com.mud.game.worlddata.db.models.NormalObject;
import com.mud.game.worlddata.db.models.Skill;
import com.mud.game.worldrun.db.mappings.MongoMapper;

public class CommonObjectBuilder {

    public static SkillObject buildSkill(String skillKey)  {
        return SkillObjectManager.create(skillKey);
    }

    public static EquipmentObject buildEquipment(String equipmentTemplateKey){
        return EquipmentObjectManager.create(equipmentTemplateKey);
    }

    public static NormalObjectObject buildNormalObject(String normalObjectTemplateKey){
        return NormalObjectObjectManager.create(normalObjectTemplateKey);
    }

    public static GemObject buildGemObject(String gemTemplateKey)  {
        return GemObjectManager.create(gemTemplateKey);
    }

    public static SkillBookObject buildSkillBookObject(String skillBookKey){
        return SkillBookObjectManager.create(skillBookKey);
    }

    public static CommonObject buildCommonObject(String templateKey)  {
        /*
        * 在创建的时候没有明确的类型，则需要自己判断
        * */
        if(DbMapper.gemRepository.existsByDataKey(templateKey)){
            return buildGemObject(templateKey);
        }else if(DbMapper.normalObjectRepository.existsByDataKey(templateKey)){
            return buildNormalObject(templateKey);
        }else if(DbMapper.equipmentRepository.existsByDataKey(templateKey)){
            return buildEquipment(templateKey);
        }else if(DbMapper.skillBookRepository.existsByDataKey(templateKey)){
            return buildSkillBookObject(templateKey);
        }else{
            return null;
        }
    }

    public static void save (CommonObject commonObject){
        /*在持久化物品的时候没有明确的类型，使用这个方法保存*/
        if(DbMapper.gemRepository.existsByDataKey(commonObject.getDataKey())){
            MongoMapper.gemObjectRepository.save((GemObject)commonObject);
        }else if(DbMapper.normalObjectRepository.existsByDataKey(commonObject.getDataKey())){
            MongoMapper.normalObjectObjectRepository.save((NormalObjectObject) commonObject);
        }else if(DbMapper.equipmentRepository.existsByDataKey(commonObject.getDataKey())){
            MongoMapper.equipmentObjectRepository.save((EquipmentObject)commonObject);
        }else if(DbMapper.skillBookRepository.existsByDataKey(commonObject.getDataKey())){
            MongoMapper.skillBookObjectRepository.save((SkillBookObject) commonObject);
        }
    }

    public static CommonObject findObjectByDataKeyAndOwner(String objectKey, String ownerId){
        /*在持久化物品的时候没有明确的类型，使用这个方法保存*/
        if(DbMapper.gemRepository.existsByDataKey(objectKey)){
            return MongoMapper.gemObjectRepository.findGemObjectByDataKeyAndOwner(objectKey, ownerId);
        }else if(DbMapper.normalObjectRepository.existsByDataKey(objectKey)){
            return MongoMapper.normalObjectObjectRepository.findNormalObjectObjectByDataKeyAndOwner(objectKey, ownerId);
        }else if(DbMapper.equipmentRepository.existsByDataKey(objectKey)){
            return MongoMapper.equipmentObjectRepository.findEquipmentObjectByDataKeyAndOwner(objectKey, ownerId);
        }else if(DbMapper.skillBookRepository.existsByDataKey(objectKey)){
            return MongoMapper.skillBookObjectRepository.findSkillBookObjectByDataKeyAndOwner(objectKey, ownerId);
        }else{
            return null;
        }
    }

}
