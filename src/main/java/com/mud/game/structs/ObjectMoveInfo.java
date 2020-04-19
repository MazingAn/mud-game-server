package com.mud.game.structs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectMoveInfo {
    private Map<String, List<Object>> info;

    public ObjectMoveInfo(Map<String, List<Object>> info) {
        this.info = info;
    }

    public ObjectMoveInfo(String objectType, List<Object> objectList) {
        this.info = new HashMap<>();
        this.info.put(objectType, objectList);
    }

    public Map<String, List<Object>> getInfo() {
        return info;
    }

    public void setInfo(Map<String, List<Object>> info) {
        this.info = info;
    }
}
