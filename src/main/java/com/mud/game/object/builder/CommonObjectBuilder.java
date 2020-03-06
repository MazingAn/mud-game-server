package com.mud.game.object.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.manager.EquipmentObjectManager;
import com.mud.game.object.manager.GemObjectManager;
import com.mud.game.object.manager.NormalObjectObjectManager;
import com.mud.game.object.manager.SkillObjectManager;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.GemObject;
import com.mud.game.object.typeclass.NormalObjectObject;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Gem;
import com.mud.game.worlddata.db.models.NormalObject;
import com.mud.game.worlddata.db.models.Skill;
import com.mud.game.worldrun.db.mappings.MongoMapper;

public class CommonObjectBuilder {

    public static SkillObject buildSkill(String skillKey) throws JsonProcessingException {
        return SkillObjectManager.create(skillKey);
    }

    public static EquipmentObject buildEquipment(String equipmentTemplateKey){
        return EquipmentObjectManager.create(equipmentTemplateKey);
    }

    public static NormalObjectObject buildNormalObject(String normalObjectTemplateKey){
        return NormalObjectObjectManager.create(normalObjectTemplateKey);
    }

    public static GemObject buildGemObject(String gemTemplateKey) throws JsonProcessingException {
        return GemObjectManager.create(gemTemplateKey);
    }

    public static CommonObject buildCommonObject(String templateKey) throws JsonProcessingException {
        /*
        * 在创建的时候没有明确的类型，则需要自己判断
        * */
        if(DbMapper.gemRepository.existsByDataKey(templateKey)){
            return buildGemObject(templateKey);
        }else if(DbMapper.normalObjectRepository.existsByDataKey(templateKey)){
            return buildNormalObject(templateKey);
        }else if(DbMapper.equipmentRepository.existsByDataKey(templateKey)){
            return buildEquipment(templateKey);
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
        }
    }

}
