package com.mud.game.worlddata.db.models.supermodel;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.net.URL;

/*
* 所有普通物品（可以放在背包里面的物体）的entity的超类
* */
@MappedSuperclass
public class BaseCommonObject extends BaseObject {
    // 物品分类
    @Mark(name="归类", link = "objectType")
    @Column(length = 128)
    private String category;
    // 物品的度量单位
    @Mark(name="单位")
    private String unitName;
    // 物品的是否只允许在背包里存储一份
    @Mark(name="是否唯一")
    @Column(columnDefinition = "bool default false")
    private boolean uniqueInBag;
    //物品能否丢弃
    @Mark(name="能否被丢弃")
    @Column(columnDefinition = "bool default true")
    private boolean canRemove;
    //物品能否销毁
    @Mark(name="能否被销毁")
    @Column(columnDefinition = "bool default true")
    private boolean canDiscard;
    //物品的最大堆叠（单个格子为一组，一组做多可以放多少）
    @Mark(name="最大堆叠")
    @Column(columnDefinition = "int default 1")
    private int maxStack;
    //物品的图标
    @Mark(name="图标")
    private String icon;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public boolean isUniqueInBag() {
        return uniqueInBag;
    }

    public void setUniqueInBag(boolean uniqueInBag) {
        this.uniqueInBag = uniqueInBag;
    }

    public boolean isCanRemove() {
        return canRemove;
    }

    public void setCanRemove(boolean canRemove) {
        this.canRemove = canRemove;
    }

    public boolean isCanDiscard() {
        return canDiscard;
    }

    public void setCanDiscard(boolean canDiscard) {
        this.canDiscard = canDiscard;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
