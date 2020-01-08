package com.mud.game.worlddata.db.models;

import com.mud.game.worlddata.db.models.supermodel.CommonObject;

import javax.persistence.Entity;

@Entity
public class DrugAndFood extends CommonObject {
    /*药品或食物*/
    // 药品作用的属性
    private String useForAttr;
    // 药品使用一次增加的属性值
    private float addedValue;

    public String getUseForAttr() {
        return useForAttr;
    }

    public void setUseForAttr(String useForAttr) {
        this.useForAttr = useForAttr;
    }

    public float getAddedValue() {
        return addedValue;
    }

    public void setAddedValue(float addedValue) {
        this.addedValue = addedValue;
    }
}
