package com.mud.game.utils.overflow;

public class OverFlowDesc {

    //溢出值
    private int flowNumber;
    //合法值
    private int validNumber;

    OverFlowDesc(int flowNumber, int validNumber) {
        this.flowNumber = flowNumber;
        this.validNumber = validNumber;
    }

    public int getFlowNumber() {
        return flowNumber;
    }

    public void setFlowNumber(int flowNumber) {
        this.flowNumber = flowNumber;
    }

    public int getValidNumber() {
        return validNumber;
    }

    public void setValidNumber(int validNumber) {
        this.validNumber = validNumber;
    }
}
