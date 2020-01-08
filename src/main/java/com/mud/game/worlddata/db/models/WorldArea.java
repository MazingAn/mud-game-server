package com.mud.game.worlddata.db.models;

import com.mud.game.worlddata.db.models.supermodel.BaseObject;

import javax.persistence.Entity;

@Entity
public class WorldArea extends BaseObject {

    private String areaHome;
    private String background;

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
