package com.mud.game.structs;

public class CommonObjectAppearance {
    /*
    * 普通物品的数据结构
    * 包含了普通物品的描述信息
    * */
    private String name;
    private int number;
    private int maxStack;
    private byte strengthLevel;
    private byte quality;

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

    public int getMaxStack() {
        return maxStack;
    }

    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
    }

    public byte getStrengthLevel() {
        return strengthLevel;
    }

    public void setStrengthLevel(byte strengthLevel) {
        this.strengthLevel = strengthLevel;
    }

    public byte getQuality() {
        return quality;
    }

    public void setQuality(byte quality) {
        this.quality = quality;
    }
}
