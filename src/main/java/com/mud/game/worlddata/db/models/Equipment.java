package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;
import com.mud.game.worlddata.db.models.supermodel.BaseCommonObject;

import javax.persistence.Entity;

@Entity
@Mark(name="装备")
public class Equipment extends BaseCommonObject {
    /*装备*/

    // 可装备的位置
    @Mark(name="可装备位置", link = "equipmentPosition")
    private String positions;

    // 装备对应的套装
    @Mark(name="所属套装")
    private String suite;

    // 如果装备是武器，则设置武器类别
    @Mark(name="武器类型", link = "weaponType")
    private String weaponType;

    //装备最大开孔数量
    @Mark(name="最大开孔")
    private int maxSlot;

    //装备的品级
    @Mark(name="品级")
    private int quality;

    //装备的属性
    @Mark(name="装备属性")
    private String attrs;


    @Mark(name = "描述名称")
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

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
