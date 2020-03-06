package com.mud.game.structs;


import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.worlddata.db.models.Equipment;

public class CommonObjectInfo {
    private String category;
    private String dataKey;
    private String dbref;
    private String desc;
    private String icon;
    private String name;
    private int number;
    private int strength_level;
    private int quality;
    private int max_stack;
    private boolean equipped;

    public CommonObjectInfo(){
    }

    public CommonObjectInfo(CommonObject commonObject, int number) {
        this.category = commonObject.getCategory();
        this.dbref = commonObject.getId();
        this.desc = commonObject.getDescription();
        this.icon = commonObject.getIcon();
        this.name = commonObject.getName();
        this.number = number;
        this.strength_level = commonObject.getLevel();
        this.quality = commonObject.getLevel();
        this.dataKey = commonObject.getDataKey();
        this.max_stack = commonObject.getMaxStack();
        if(commonObject.getClass().equals(EquipmentObject.class)){
            this.equipped = ((EquipmentObject)commonObject).isEquipped();
        }else{
            this.equipped = false;
        }
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDbref() {
        return dbref;
    }

    public void setDbref(String dbref) {
        this.dbref = dbref;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public int getMax_stack() {
        return max_stack;
    }

    public void setMax_stack(int max_stack) {
        this.max_stack = max_stack;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }
}
