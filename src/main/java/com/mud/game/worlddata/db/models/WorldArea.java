package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;
import com.mud.game.worlddata.db.models.supermodel.BaseObject;

import javax.persistence.Entity;

@Entity
@Mark(name="世界区域")
public class WorldArea extends BaseObject {

    @Mark(name = "区域复活点")
    private String areaHome;
    @Mark(name = "区域背景")
    private String background;
    @Mark(name = "描述名称")
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getAreaHome() {
        return areaHome;
    }

    public void setAreaHome(String areaHome) {
        this.areaHome = areaHome;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}