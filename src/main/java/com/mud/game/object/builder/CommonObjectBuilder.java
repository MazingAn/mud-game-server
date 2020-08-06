package com.mud.game.object.builder;

import com.mud.game.object.manager.*;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.*;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.supermodel.BaseCommonObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;

public class CommonObjectBuilder {

    public static SkillObject buildSkill(String skillKey) {
        SkillObject skillObject = SkillObjectManager.create(skillKey);
        skillObject = MongoMapper.skillObjectRepository.save(skillObject);
        return skillObject;
    }

    public static EquipmentObject buildEquipment(String equipmentTemplateKey) {
        EquipmentObject equipmentObject = EquipmentObjectManager.create(equipmentTemplateKey);
        equipmentObject = MongoMapper.equipmentObjectRepository.save(equipmentObject);
        return equipmentObject;
    }

    public static NormalObjectObject buildNormalObject(String normalObjectTemplateKey) {
        NormalObjectObject normalObjectObject = NormalObjectObjectManager.create(normalObjectTemplateKey);
        normalObjectObject = MongoMapper.normalObjectObjectRepository.save(normalObjectObject);
        return normalObjectObject;
    }

    public static GemObject buildGemObject(String gemTemplateKey) {
        GemObject gemObject = GemObjectManager.create(gemTemplateKey);
        gemObject = MongoMapper.gemObjectRepository.save(gemObject);
        return gemObject;
    }

    public static SkillBookObject buildSkillBookObject(String skillBookKey) {
        SkillBookObject skillBookObject = SkillBookObjectManager.create(skillBookKey);
        skillBookObject = MongoMapper.skillBookObjectRepository.save(skillBookObject);
        return skillBookObject;
    }

    public static CommonObject buildCommonObject(String templateKey) {
        /*
         * 在创建的时候没有明确的类型，则需要自己判断
         * */
        if (DbMapper.gemRepository.existsByDataKey(templateKey)) {
            return buildGemObject(templateKey);
        } else if (DbMapper.normalObjectRepository.existsByDataKey(templateKey)) {
            return buildNormalObject(templateKey);
        } else if (DbMapper.equipmentRepository.existsByDataKey(templateKey)) {
            return buildEquipment(templateKey);
        } else if (DbMapper.skillBookRepository.existsByDataKey(templateKey)) {
            return buildSkillBookObject(templateKey);
        } else {
            return null;
        }
    }

    public static void save(CommonObject commonObject) {
        /*在持久化物品的时候没有明确的类型，使用这个方法保存*/
        if (DbMapper.gemRepository.existsByDataKey(commonObject.getDataKey())) {
            MongoMapper.gemObjectRepository.save((GemObject) commonObject);
        } else if (DbMapper.normalObjectRepository.existsByDataKey(commonObject.getDataKey())) {
            MongoMapper.normalObjectObjectRepository.save((NormalObjectObject) commonObject);
        } else if (DbMapper.equipmentRepository.existsByDataKey(commonObject.getDataKey())) {
            MongoMapper.equipmentObjectRepository.save((EquipmentObject) commonObject);
        } else if (DbMapper.skillBookRepository.existsByDataKey(commonObject.getDataKey())) {
            MongoMapper.skillBookObjectRepository.save((SkillBookObject) commonObject);
        }
    }

    public static CommonObject findObjectByDataKeyAndOwner(String objectKey, String ownerId) {
        /*在持久化物品的时候没有明确的类型，使用这个方法保存*/
        if (DbMapper.gemRepository.existsByDataKey(objectKey)) {
            return MongoMapper.gemObjectRepository.findGemObjectByDataKeyAndOwner(objectKey, ownerId);
        } else if (DbMapper.normalObjectRepository.existsByDataKey(objectKey)) {
            return MongoMapper.normalObjectObjectRepository.findNormalObjectObjectByDataKeyAndOwner(objectKey, ownerId);
        } else if (DbMapper.equipmentRepository.existsByDataKey(objectKey)) {
            return MongoMapper.equipmentObjectRepository.findEquipmentObjectByDataKeyAndOwner(objectKey, ownerId);
        } else if (DbMapper.skillBookRepository.existsByDataKey(objectKey)) {
            return MongoMapper.skillBookObjectRepository.findSkillBookObjectByDataKeyAndOwner(objectKey, ownerId);
        } else {
            return null;
        }
    }


    //TODO: 创建findObjectbyId方法
    public static CommonObject findObjectById(String objectId) {
        /*根据主键查询物品信息*/
        CommonObject commonObject = null;
        commonObject = MongoMapper.gemObjectRepository.findGemObjectById(objectId);
        if (null != commonObject) {
            return commonObject;
        }
        commonObject = MongoMapper.normalObjectObjectRepository.findNormalObjectObjectById(objectId);
        if (null != commonObject) {
            return commonObject;
        }
        commonObject = MongoMapper.equipmentObjectRepository.findEquipmentObjectById(objectId);
        if (null != commonObject) {
            return commonObject;
        }
        commonObject = MongoMapper.skillBookObjectRepository.findSkillBookObjectById(objectId);
        if (null != commonObject) {
            return commonObject;
        }
        return null;
    }

    public static BaseCommonObject findObjectTemplateByDataKey(String objectKey) {
        if (DbMapper.gemRepository.existsByDataKey(objectKey)) {
            return DbMapper.gemRepository.findGemByDataKey(objectKey);
        } else if (DbMapper.normalObjectRepository.existsByDataKey(objectKey)) {
            return DbMapper.normalObjectRepository.findNormalObjectByDataKey(objectKey);
        } else if (DbMapper.equipmentRepository.existsByDataKey(objectKey)) {
            return DbMapper.equipmentRepository.findEquipmentByDataKey(objectKey);
        } else if (DbMapper.skillBookRepository.existsByDataKey(objectKey)) {
            return DbMapper.skillBookRepository.findSkillBookByDataKey(objectKey);
        } else {
            return null;
        }
    }

}
