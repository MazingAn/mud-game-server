package com.mud.game.messages;

public class LearnByObjectBalanceMessage {
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
