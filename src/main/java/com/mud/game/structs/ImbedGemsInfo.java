package com.mud.game.structs;

import com.mud.game.object.typeclass.GemObject;

import java.util.Map;

public class ImbedGemsInfo {

    /**
     * 宝石的名称
     */
    private String name;

    /**
     * 宝石的图标
     */
    private String icon;
    /**
     * 宝石的属性
     */
    Map<String, Map<String, Object>> attr;

    public ImbedGemsInfo(GemObject gem) {
        this.name = gem.getName();
        this.attr = gem.getAttrs();
        this.icon = gem.getIcon();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Map<String, Map<String, Object>> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, Map<String, Object>> attr) {
        this.attr = attr;
    }
}
