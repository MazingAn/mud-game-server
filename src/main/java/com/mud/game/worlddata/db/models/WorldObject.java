package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;
import com.mud.game.worlddata.db.models.supermodel.BaseObject;

import javax.persistence.*;
import java.net.URL;

@Entity
@Mark(name = "世界物体")
public class WorldObject extends BaseObject {

    @Mark(name = "所处位置", link = "worldRoom")
    private String location;

    //物品生成器，获取物品的时候的按钮名称
    @Mark(name = "交互名称")
    private String actionName;

    //如果是物品生成器的时候，是否只生成一次
    @Mark(name = "只生成一次")
    @Column(columnDefinition = "bool default false")
    private boolean lootOnce;
    @Mark(name = "描述名称")
    private String name;
    // 如果是物品生成器，获取物品的时候的数量
    @Mark(name = "物品生成数量")
    @Column(columnDefinition = "int default 1")
    private int lootNumber;

    // 如果是物品生成器，物品刷新的时间
    @Mark(name = "冷却时间")
    @Column(columnDefinition = "float default 5")
    private float refreshTime;

    @Mark(name = "显示条件")
    @Column(nullable = true, length = 1024)
    private String showCondition;

    @Mark(name = "图标")
    private String icon;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isLootOnce() {
        return lootOnce;
    }

    public void setLootOnce(boolean lootOnce) {
        this.lootOnce = lootOnce;
    }

    public int getLootNumber() {
        return lootNumber;
    }

    public void setLootNumber(int lootNumber) {
        this.lootNumber = lootNumber;
    }

    public float getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(float refreshTime) {
        this.refreshTime = refreshTime;
    }

    public String getShowCondition() {
        return showCondition;
    }

    public void setShowCondition(String condition) {
        this.showCondition = condition;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
