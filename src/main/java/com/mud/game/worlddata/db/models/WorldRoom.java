package com.mud.game.worlddata.db.models;

import com.mud.game.worlddata.db.models.supermodel.BaseObject;

import javax.persistence.*;

@Entity
public class WorldRoom extends BaseObject {
    private boolean peaceful;
    private String location;
    private String position;
    @Column(columnDefinition = "int default 1")
    private int level;
    private String hangUpCommand;
    @Column(columnDefinition = "bool default false")
    private boolean needUpdate;
    private String icon;
    private String background;


    public boolean isPeaceful() {
        return peaceful;
    }

    public void setPeaceful(boolean peaceful) {
        this.peaceful = peaceful;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getHangUpCommand() {
        return hangUpCommand;
    }

    public void setHangUpCommand(String hangUpCommand) {
        this.hangUpCommand = hangUpCommand;
    }

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
