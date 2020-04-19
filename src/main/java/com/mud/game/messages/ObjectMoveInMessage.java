package com.mud.game.messages;

import com.mud.game.structs.ObjectMoveInfo;

import java.util.List;
import java.util.Map;

public class ObjectMoveInMessage {
    private Map<String, List<Object>> obj_moved_in;

    public ObjectMoveInMessage() {
    }

    public ObjectMoveInMessage(ObjectMoveInfo info) {
        this.obj_moved_in = info.getInfo();
    }

    public Map<String, List<Object>> getObj_moved_in() {
        return obj_moved_in;
    }

    public void setObj_moved_in(Map<String, List<Object>> obj_moved_in) {
        this.obj_moved_in = obj_moved_in;
    }
}
