package com.mud.game.messages;

public class PayFinishMessage {
    private int status;
    private int amount;

    public PayFinishMessage(int status, int amount) {
        this.status = status;
        this.amount = amount;
    }

    public PayFinishMessage(int status) {
        this.status = status;
    }

    public int isStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
