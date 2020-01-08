package com.mud.game.worlddata.db.models;

import com.mud.game.worlddata.db.models.supermodel.CommonObject;

import javax.persistence.Entity;

@Entity
public class Equipment extends CommonObject {
    /*装备*/

    // 可装备的位置
    private String positions;

    // 装备对应的套装
    private String suite;

    // 如果装备是武器，则设置武器类别
    private String weaponType;

    //装备最大开孔数量
    private int maxSlot;

    //装备的品级
    private int quality;

    //装备的属性
    private String attrs;


    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public int getMaxSlot() {
        return maxSlot;
    }

    public void setMaxSlot(int maxSlot) {
        this.maxSlot = maxSlot;
    }
}
