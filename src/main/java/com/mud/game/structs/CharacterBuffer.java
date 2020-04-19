package com.mud.game.structs;

public class CharacterBuffer {
    private String name;
    private float duringTime;
    private int addedCount;

    public CharacterBuffer() {
    }

    public CharacterBuffer(String name, float duringTime, int addedCount) {
        this.name = name;
        this.duringTime = duringTime;
        this.addedCount = addedCount;
    }
}
