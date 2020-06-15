package com.mud.game.messages;

import com.mud.game.structs.ObjectMoveInfo;

import java.util.List;
import java.util.Map;

public class ObjectMoveOutMessage {
    private Map<String, List<Object>> obj_moved_out;

    public ObjectMoveOutMessage() {
    }

    public ObjectMoveOutMessage(ObjectMoveInfo info) {
        this.obj_moved_out = info.getInfo();
    }

    public Map<String, List<Object>> getObj_moved_out() {
        return obj_moved_out;
    }

    public void setObj_moved_out(Map<String, List<Object>> obj_moved_out) {
        this.obj_moved_out = obj_moved_out;
    }
}
