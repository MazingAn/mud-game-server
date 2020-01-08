package com.mud.game.messages;

import java.util.Map;

public class RevealedMapMessage {
    private Map<String, Object> revealed_map;

    public RevealedMapMessage(Map<String, Object> revealed_map) {
        this.revealed_map = revealed_map;
    }

    public Map<String, Object> getRevealed_map() {
        return revealed_map;
    }

    public void setRevealed_map(Map<String, Object> revealed_map) {
        this.revealed_map = revealed_map;
    }
}
