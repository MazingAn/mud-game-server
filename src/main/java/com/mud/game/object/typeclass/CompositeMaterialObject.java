package com.mud.game.object.typeclass;

import com.mud.game.worlddata.db.models.CompositeMaterial;

import java.util.List;

public class CompositeMaterialObject {
    // 物品信息
    private String name;
    private String dataKey;
    private String description;
    private String icon;
    private String attrs;
    //  合成材料信息
    private List<CompositeMaterial> compositeMaterialList;

    public String getName() {
        return name;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public List<CompositeMaterial> getCompositeMaterialList() {
        return compositeMaterialList;
    }

    public void setCompositeMaterialList(List<CompositeMaterial> compositeMaterialList) {
        this.compositeMaterialList = compositeMaterialList;
    }
}
