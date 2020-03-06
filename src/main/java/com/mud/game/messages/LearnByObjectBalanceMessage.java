package com.mud.game.messages;

public class LearnByObjectBalanceMessage {
    /*
    * 消息，current_potential
    * 返回玩家当前在这个NPC这里通过物品充值所剩下的潜能点（类似于充值余额）
    * */
    private int current_potential;

    public LearnByObjectBalanceMessage(int current_potential) {
        this.current_potential = current_potential;
    }

    public int getCurrent_potential() {
        return current_potential;
    }

    public void setCurrent_potential(int current_potential) {
        this.current_potential = current_potential;
    }
}
