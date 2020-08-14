package com.mud.game.structs;

import java.util.Map;

public class CheckStrengthenInfo {
    // 装备属性
    Map<String, Object> attr_after;
    //是否能够强化的标记
    Boolean can_strength;
    //需要的材料
    Map<String, Map<String, Object>> metarials;
    //当前的属性和值
    Map<String, Map<String, Object>> attr_befor;

    public CheckStrengthenInfo(Map<String, Object> attr_after, Boolean can_strength, Map<String, Map<String, Object>> metarials, Map<String, Map<String, Object>> attr_befor) {
        this.attr_after = attr_after;
        this.can_strength = can_strength;
        this.metarials = metarials;
        this.attr_befor = attr_befor;
    }

    public Map<String, Object> getAttr_after() {
        return attr_after;
    }

    public void setAttr_after(Map<String, Object> attr_after) {
        this.attr_after = attr_after;
    }

    public Boolean getCan_strength() {
        return can_strength;
    }

    public void setCan_strength(Boolean can_strength) {
        this.can_strength = can_strength;
    }

    public Map<String, Map<String, Object>> getMetarials() {
        return metarials;
    }

    public void setMetarials(Map<String, Map<String, Object>> metarials) {
        this.metarials = metarials;
    }

    public Map<String, Map<String, Object>> getAttr_befor() {
        return attr_befor;
    }

    public void setAttr_befor(Map<String, Map<String, Object>> attr_befor) {
        this.attr_befor = attr_befor;
    }
}
