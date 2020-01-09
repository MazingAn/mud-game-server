package com.mud.game.object.typeclass;

import com.mud.game.object.supertypeclass.CommonObject;

import java.util.Map;

public class GemObject extends CommonObject {

    //宝石的品级
    private int quality;
    //宝石的属性
    private Map<String, Float> attrs;

    public GemObject() {
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public Map<String, Float> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, Float> attrs) {
        this.attrs = attrs;
    }
}
