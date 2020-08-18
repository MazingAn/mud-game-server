package com.mud.game.structs;

import com.mud.game.messages.AlertMessage;
import com.mud.game.object.manager.CommonItemContainerManager;
import com.mud.game.object.typeclass.BagpackObject;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.NormalObject;
import com.mud.game.worlddata.db.models.SlotMaterial;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.mud.game.constant.Constant.MAX_SLOT;

/**
 * 返回装备强化检查的反馈
 */
public class checkSlotInfo {
    /**
     * 是否能开孔
     */
    private Boolean can_open_slot;
    /**
     * 开孔前的数量
     */
    private int slot_befor;
    /**
     * 开孔后的数量
     */
    private int slot_after;
    /**
     * 需要的材料
     */
    Map<String, Map<String, Object>> metarials;


    public checkSlotInfo(EquipmentObject equipmentObject, List<SlotMaterial> slotMaterialList, PlayerCharacter caller) {
        Boolean can_open_slot = true;
        int slot_befor = equipmentObject.getOpendSlot();
        int slot_after = equipmentObject.getOpendSlot();
        //判断装备是否可以开孔
        if (equipmentObject.getOpendSlot() < MAX_SLOT) {
            slot_after++;
        } else {
            caller.msg(new AlertMessage("装备打孔已达到最大值!"));
            can_open_slot = false;
        }
        if (slotMaterialList.size() == 0) {
            caller.msg(new AlertMessage("材料信息有误"));
            can_open_slot = false;
        }
        //需要的材料
        SlotMaterial slotMaterial = null;
        Map<String, Map<String, Object>> metarials = new HashMap<>();
        Map<String, Object> metarial = new HashMap<>();
        //获取背包信息
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(caller.getBagpack());
        Iterator<SlotMaterial> slotMaterialIterator = slotMaterialList.iterator();
        while (slotMaterialIterator.hasNext()) {
            slotMaterial = slotMaterialIterator.next();
            metarial = new HashMap<>();
            metarial.put("needed", slotMaterial.getNumber());
            //获取材料信息
            NormalObject target = DbMapper.normalObjectRepository.findNormalObjectByDataKey(slotMaterial.getDependency());
            metarial.put("icon", target.getIcon());
            metarial.put("name", target.getName());
            metarial.put("desc", target.getDescription());
            int owmed = CommonItemContainerManager.getNumberByDataKey(bagpackObject, slotMaterial.getDependency());
            metarial.put("owned", owmed);
            if (owmed < slotMaterial.getNumber()) {
                can_open_slot = false;
            }
            metarials.put(target.getDataKey(), metarial);
        }
        //赋值
        this.can_open_slot = can_open_slot;
        this.slot_befor = slot_befor;
        this.slot_after = slot_after;
        this.metarials = metarials;
    }

    public Boolean getCan_open_slot() {
        return can_open_slot;
    }

    public void setCan_open_slot(Boolean can_open_slot) {
        this.can_open_slot = can_open_slot;
    }

    public int getSlot_befor() {
        return slot_befor;
    }

    public void setSlot_befor(int slot_befor) {
        this.slot_befor = slot_befor;
    }

    public int getSlot_after() {
        return slot_after;
    }

    public void setSlot_after(int slot_after) {
        this.slot_after = slot_after;
    }

    public Map<String, Map<String, Object>> getMetarials() {
        return metarials;
    }

    public void setMetarials(Map<String, Map<String, Object>> metarials) {
        this.metarials = metarials;
    }
}
