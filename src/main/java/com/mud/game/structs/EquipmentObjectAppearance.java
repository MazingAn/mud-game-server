package com.mud.game.structs;

import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.GemObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class EquipmentObjectAppearance {
    /*
    * @ 返回给客户端的装备信息的数据结构
    */
    private String deref;
    private String name;
    private String weapon_type;
    private String desc;
    private List<EmbeddedCommand> cmds;
    private String icon;
    private List<GemObject> gems;
    private Map<String, Map<String, Object>> attr;
    private String position;
    private String category;
    private int strength_level;
    private int quality;
    private int opend_slot;
    private int total_slot;
    private boolean can_strength;

    public EquipmentObjectAppearance(EquipmentObject equipmentObject) {
        this.deref = equipmentObject.getId();
        this.name = equipmentObject.getName();
        this.weapon_type = equipmentObject.getWeaponType();
        this.desc = equipmentObject.getDescription();
        this.cmds = new ArrayList<>();
        this.icon = equipmentObject.getIcon();
        this.gems = equipmentObject.getGems();
        this.attr = equipmentObject.getAttrs();
        this.position = equipmentObject.getPositions().toString();
        this.category = equipmentObject.getCategory();
        this.strength_level = equipmentObject.getLevel();
        this.quality = equipmentObject.getQuality();
        // TODO：修改为已经使用的孔
        this.opend_slot = equipmentObject.getMaxSlot();
        this.total_slot = equipmentObject.getMaxSlot();
        this.can_strength = false;
    }

    public String getDeref() {
        return deref;
    }

    public void setDeref(String deref) {
        this.deref = deref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeapon_type() {
        return weapon_type;
    }

    public void setWeapon_type(String weapon_type) {
        this.weapon_type = weapon_type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<EmbeddedCommand> getCmds() {
        return cmds;
    }

    public void setCmds(List<EmbeddedCommand> cmds) {
        this.cmds = cmds;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<GemObject> getGems() {
        return gems;
    }

    public void setGems(List<GemObject> gems) {
        this.gems = gems;
    }

    public Map<String, Map<String, Object>> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, Map<String, Object>> attr) {
        this.attr = attr;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStrength_level() {
        return strength_level;
    }

    public void setStrength_level(int strength_level) {
        this.strength_level = strength_level;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getOpend_slot() {
        return opend_slot;
    }

    public void setOpend_slot(int opend_slot) {
        this.opend_slot = opend_slot;
    }

    public int getTotal_slot() {
        return total_slot;
    }

    public void setTotal_slot(int total_slot) {
        this.total_slot = total_slot;
    }

    public boolean isCan_strength() {
        return can_strength;
    }

    public void setCan_strength(boolean can_strength) {
        this.can_strength = can_strength;
    }
}
