package com.mud.game.messages;

import com.mud.game.structs.ObjectMoveInfo;

import java.util.Map;

public class ObjectMoveIn {
    private Map<String, ObjectMoveInfo> info;

    public ObjectMoveIn(Map<String, ObjectMoveInfo> info) {
        this.info = info;
    }

    public Map<String, ObjectMoveInfo> getInfo() {
        return info;
    }

    public void setInfo(Map<String, ObjectMoveInfo> info) {
        this.info = info;
    }
}
