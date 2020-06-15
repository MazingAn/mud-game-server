package com.mud.game.messages;

import com.mud.game.structs.RoomInfo;

public class CurrentLocationMessage {
    private RoomInfo current_location;

    public CurrentLocationMessage(RoomInfo current_location) {
        this.current_location = current_location;
    }

    public RoomInfo getCurrent_location() {
        return current_location;
    }

    public void setCurrent_location(RoomInfo current_location) {
        this.current_location = current_location;
    }
}
