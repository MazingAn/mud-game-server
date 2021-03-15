package com.mud.game.messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DivideMessage {
    private List<Map<String, String>> divide_list;

    public DivideMessage(List<Map<String, String>> divideList) {
        if (divideList == null) {
            return;
        }
        divide_list = new ArrayList<>();
        Map<String, String> divideMap = null;
        for (Map<String, String> map : divideList) {
            divideMap = new HashMap<>();
            divideMap.put("divideName", map.get("divideName"));
            divideMap.put("divide", map.get("divide"));
            divideMap.put("divideUrl", map.get("divideUrl"));
            this.divide_list.add(divideMap);
        }
    }

    public List<Map<String, String>> getDivide_list() {
        return divide_list;
    }

    public void setDivide_list(List<Map<String, String>> divide_list) {
        this.divide_list = divide_list;
    }
}
