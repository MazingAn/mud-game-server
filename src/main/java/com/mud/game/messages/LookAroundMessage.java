package com.mud.game.messages;

import com.mud.game.object.typeclass.WorldRoomObject;
import com.mud.game.worlddata.db.models.WorldRoom;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class LookAroundMessage {
    private Map look_around;

    public LookAroundMessage(Map look_around) {
        this.look_around = look_around;
    }

    public Map getLook_around() {
        return look_around;
    }

    public void setLook_around(Map look_around) {
        this.look_around = look_around;
    }
}
