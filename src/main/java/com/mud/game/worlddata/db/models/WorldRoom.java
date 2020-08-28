package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;
import com.mud.game.worlddata.db.models.supermodel.BaseObject;

import javax.persistence.*;

@Entity
@Mark(name = "世界房间")
public class WorldRoom extends BaseObject {

    @Mark(name = "和平区域")
    private boolean peaceful;

    @Mark(name = "所属区域", link = "area")
    private String location;

    @Mark(name = "位置坐标")
    private String position;

    @Column(columnDefinition = "int default 1")
    @Mark(name = "房间等级")
    private int level;

    @Mark(name = "交互命令")
    private String hangUpCommand;

    @Column(columnDefinition = "bool default false")
    @Mark(name = "需要更新")
    private boolean needUpdate;

    @Mark(name = "图标")
    private String icon;

    @Mark(name = "背景")
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
