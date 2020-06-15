package com.mud.game.structs;

import org.w3c.dom.Attr;

import java.util.Objects;

public class SkillEffect {
    private String position;
    private String attrKey;
    private Object value;

    public SkillEffect(String position, String attrKey, Object value) {
        this.position = position;
        this.attrKey = attrKey;
        this.value = value;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAttrKey() {
        return attrKey;
    }

    public void setAttrKey(String attrKey) {
        this.attrKey = attrKey;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
