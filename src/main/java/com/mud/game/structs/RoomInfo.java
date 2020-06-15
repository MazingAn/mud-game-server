package com.mud.game.structs;

import com.mud.game.object.typeclass.WorldRoomObject;

import java.net.URL;
import java.util.List;

public class RoomInfo {
    private String dbref;
    private String area;
    private int[] pos;
    private String name;
    private String icon;
    private String key;

    public RoomInfo(WorldRoomObject roomObject) {
        this.dbref = roomObject.getId();
        this.area = roomObject.getLocation();
        this.pos = new int[]{roomObject.getPosition().getX(), roomObject.getPosition().getY()};
        this.name = roomObject.getName();
        this.icon = roomObject.getIcon();
        this.key = roomObject.getDataKey();
    }

    public String getDbref() {
        return dbref;
    }

    public void setDbref(String dbref) {
        this.dbref = dbref;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public int[] getPos() {
        return pos;
    }

    public void setPos(int[] pos) {
        this.pos = pos;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
