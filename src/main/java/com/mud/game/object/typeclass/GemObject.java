package com.mud.game.object.typeclass;

import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.utils.modelsutils.Mark;

import java.util.Map;

public class GemObject extends CommonObject {

    //宝石的属性
    private Map<String, Map<String, Object>> attrs;
    //宝石可以镶嵌在什么位置的装备上
    private String positions;

    public GemObject() {
    }


    public Map<String, Map<String, Object>> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, Map<String, Object>> attrs) {
        this.attrs = attrs;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }
}
