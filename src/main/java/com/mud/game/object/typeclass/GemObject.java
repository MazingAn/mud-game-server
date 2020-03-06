package com.mud.game.object.typeclass;

import com.mud.game.object.supertypeclass.CommonObject;

import java.util.Map;

public class GemObject extends CommonObject {

    //宝石的属性
    private Map<String, Map<String, Object>> attrs;

    public GemObject() {
    }


    public Map<String, Map<String, Object>> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, Map<String, Object>> attrs) {
        this.attrs = attrs;
    }
}
