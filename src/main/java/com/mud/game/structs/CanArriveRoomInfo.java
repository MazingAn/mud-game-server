package com.mud.game.structs;

import com.mud.game.object.typeclass.WorldExitObject;
import com.mud.game.object.typeclass.WorldRoomObject;

import java.net.URL;

public class CanArriveRoomInfo {
    private String dbref;
    private String area;
    private int[] pos;
    private String name;
    private String icon;
    private ExitInfo key;

    public CanArriveRoomInfo(WorldRoomObject roomObject, WorldExitObject exit) {
        this.dbref = roomObject.getId();
        this.area = roomObject.getLocation();
        this.pos = new int[]{roomObject.getPosition().getX(), roomObject.getPosition().getY()};
        this.name = roomObject.getName();
        this.icon = roomObject.getIcon();
        this.key = new ExitInfo(exit);
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

    public ExitInfo getKey() {
        return key;
    }

    public void setKey(ExitInfo key) {
        this.key = key;
    }

}
