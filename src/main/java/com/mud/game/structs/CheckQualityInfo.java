package com.mud.game.structs;

import com.mud.game.messages.AlertMessage;
import com.mud.game.object.manager.CommonItemContainerManager;
import com.mud.game.object.typeclass.BagpackObject;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.GemObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.NormalObject;
import com.mud.game.worlddata.db.models.QualityMaterial;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.mud.game.constant.Constant.MAX_LEVEL;
import static com.mud.game.constant.Constant.QUALITY_COEFFICIENT;

public class CheckQualityInfo {
    //需要装备的强化等级达到10级
    private int needed_strength_level;
    //当前装备的强化等级
    private int owned_strength_level;
    // 进阶之前的装备属性
    Map<String, Object> attr_befor;
    // 进阶之后的装备属性
    Map<String, Object> attr_after;
    //需要的材料
    Map<String, Map<String, Object>> metarials;
    //是否能够进阶
    private Boolean can_advanced;
    //当前的装备品级
    private int quality_befor;
    //进阶之后的装备品级
    private int quality_after;

    public CheckQualityInfo(EquipmentObject equipmentObject, List<QualityMaterial> qualityMaterialList, PlayerCharacter caller) {
        Boolean can_advanced = true;
        this.needed_strength_level = MAX_LEVEL;
        this.owned_strength_level = equipmentObject.getLevel();
        this.quality_befor = equipmentObject.getQuality();
        this.quality_after = equipmentObject.getQuality() + 1;
        //判断是否达到强化的最高值
        if (equipmentObject.getLevel() != MAX_LEVEL) {
            caller.msg(new AlertMessage("装备强化值不足！"));
            can_advanced = false;
        }
        if (equipmentObject.getQuality() > MAX_LEVEL) {
            caller.msg(new AlertMessage("装备进阶以达到最大值！"));
            can_advanced = false;
        }
        //材料验证
        //进阶材料信息
        if (qualityMaterialList.size() == 0) {
            caller.msg("进阶材料错误！");
            can_advanced = false;
        }
        //TODO
        Map<String, Object> attr_befor = new HashMap<>();   // 进阶之前的装备属性
        Map<String, Object> attr_after = new HashMap<>();   // 进阶之后的装备属性
        //装备属性封装
        Map<String, Object> attributeAfterMap = new HashMap<>();
        Iterator<Map.Entry<String, Map<String, Object>>> mapIterator = equipmentObject.getAttrs().entrySet().iterator();
        while (mapIterator.hasNext()) {
            attributeAfterMap = new HashMap<>();
            Map.Entry<String, Map<String, Object>> mapEntry = mapIterator.next();
            Iterator<Map.Entry<String, Object>> mapEntryIterator = mapEntry.getValue().entrySet().iterator();
            while (mapEntryIterator.hasNext()) {
                Map.Entry<String, Object> map = mapEntryIterator.next();
                attributeAfterMap.put(map.getKey(), map.getValue());
                if ("value".equals(map.getKey())) {
                    Object value = map.getValue();
                    int a = (int) Double.parseDouble(value.toString());
                    double v = a * QUALITY_COEFFICIENT;
                    attr_after.put(mapEntry.getKey(), Math.floor(v));
                }
            }
            attributeAfterMap.put("key", mapEntry.getKey());
            attr_befor.put(mapEntry.getKey(), attributeAfterMap);
        }
        //需要的材料
        QualityMaterial qualityMaterial = null;
        Map<String, Map<String, Object>> metarials = new HashMap<>();
        Map<String, Object> metarial = new HashMap<>();
        //获取背包信息
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(caller.getBagpack());
        Iterator<QualityMaterial> qualityMaterialIterator = qualityMaterialList.iterator();
        while (qualityMaterialIterator.hasNext()) {
            qualityMaterial = qualityMaterialIterator.next();
            metarial = new HashMap<>();
            metarial.put("needed", qualityMaterial.getNumber());
            //获取材料信息
            NormalObject target = DbMapper.normalObjectRepository.findNormalObjectByDataKey(qualityMaterial.getDependency());
            metarial.put("icon", target.getIcon());
            metarial.put("name", target.getName());
            metarial.put("desc", target.getDescription());
            int owmed = CommonItemContainerManager.getNumberByDataKey(bagpackObject, qualityMaterial.getDependency());
            metarial.put("owned", owmed);
            if (owmed < qualityMaterial.getNumber()) {
                can_advanced = false;
            }
            metarials.put(target.getDataKey(), metarial);
        }
        this.can_advanced = can_advanced;
        this.attr_after = attr_after;
        this.attr_befor = attr_befor;
        this.metarials = metarials;
    }

    public int getNeeded_strength_level() {
        return needed_strength_level;
    }

    public void setNeeded_strength_level(int needed_strength_level) {
        this.needed_strength_level = needed_strength_level;
    }

    public int getOwned_strength_level() {
        return owned_strength_level;
    }

    public void setOwned_strength_level(int owned_strength_level) {
        this.owned_strength_level = owned_strength_level;
    }

    public Map<String, Object> getAttr_befor() {
        return attr_befor;
    }

    public void setAttr_befor(Map<String, Object> attr_befor) {
        this.attr_befor = attr_befor;
    }

    public Map<String, Object> getAttr_after() {
        return attr_after;
    }

    public void setAttr_after(Map<String, Object> attr_after) {
        this.attr_after = attr_after;
    }

    public Map<String, Map<String, Object>> getMetarials() {
        return metarials;
    }

    public void setMetarials(Map<String, Map<String, Object>> metarials) {
        this.metarials = metarials;
    }

    public Boolean getCan_advanced() {
        return can_advanced;
    }

    public void setCan_advanced(Boolean can_advanced) {
        this.can_advanced = can_advanced;
    }

    public int getQuality_befor() {
        return quality_befor;
    }

    public void setQuality_befor(int quality_befor) {
        this.quality_befor = quality_befor;
    }

    public int getQuality_after() {
        return quality_after;
    }

    public void setQuality_after(int quality_after) {
        this.quality_after = quality_after;
    }
}
