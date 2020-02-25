package com.mud.game.object.supertypeclass;

import java.net.URL;
import java.util.Set;

/*
* 游戏中所有可以被玩家携带的物品的基类
* */
public class CommonObject extends BaseGameObject {
    //单位（如：金子的单位是  锭）
    private String unitName;
    //分类
    private String category;
    //效果函数
    private String function;
    //是否唯一(背包里面不得多于一组）
    private boolean isUnique;
    //最大堆叠
    private int maxStack;
    //能否销毁
    private boolean canDiscard;
    //能否丢弃
    private boolean canRemove;
    //图标
    private String icon;
    //事件
    private Set<String> gameEvents;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public void setUnique(boolean unique) {
        isUnique = unique;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
    }

    public boolean isCanDiscard() {
        return canDiscard;
    }

    public void setCanDiscard(boolean canDiscard) {
        this.canDiscard = canDiscard;
    }

    public boolean isCanRemove() {
        return canRemove;
    }

    public void setCanRemove(boolean canRemove) {
        this.canRemove = canRemove;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Set<String> getGameEvents() {
        return gameEvents;
    }

    public void setGameEvents(Set<String> gameEvents) {
        this.gameEvents = gameEvents;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
