package com.mud.game.structs;

public class SkillXiShu {
    private String attrKey;
    private double value;
    private double coefficient;

    public SkillXiShu(String attrKey, double value, double coefficient) {
        this.attrKey = attrKey;
        this.value = value;
        this.coefficient = coefficient;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public String getAttrKey() {
        return attrKey;
    }

    public void setAttrKey(String attrKey) {
        this.attrKey = attrKey;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
