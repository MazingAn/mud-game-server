package com.mud.game.object.manager;

import com.mud.game.object.typeclass.NormalObjectObject;
import com.mud.game.utils.jsonutils.Attr2Map;
import com.mud.game.utils.jsonutils.JsonStrConvetor;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.NormalObject;

import java.util.ArrayList;

public class NormalObjectObjectManager {

    public static NormalObjectObject create(String templateKey) {
        try{
            NormalObjectObject normalObjectObject = new NormalObjectObject();
            NormalObject template = DbMapper.normalObjectRepository.findNormalObjectByDataKey(templateKey);
            normalObjectObject.setDataKey(template.getDataKey());
            normalObjectObject.setName(template.getName());
            normalObjectObject.setDescription(template.getDescription());
            normalObjectObject.setUnitName(template.getUnitName());
            normalObjectObject.setCategory(template.getCategory());
            normalObjectObject.setUnique(template.isUniqueInBag());
            normalObjectObject.setMaxStack(template.getMaxStack());
            normalObjectObject.setCanDiscard(template.isCanDiscard());
            normalObjectObject.setCanRemove(template.isCanRemove());
            normalObjectObject.setIcon(template.getIcon());
            normalObjectObject.setQuality(1);
            normalObjectObject.setLevel(1);
            normalObjectObject.setTotalNumber(0);
            return normalObjectObject;
        }catch (Exception e){
             System.out.println("在创建物品 " + templateKey +" 的时候发生异常！ 已经跳过对物品的创建！");
             return null;
        }
    }

}
