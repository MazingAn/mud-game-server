package com.mud.game.structs;


import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.supertypeclass.WorldObject;

public class SimpleObject {
    private String key;
    private String dbref;
    private String name;

    public SimpleObject(WorldObject object) {
        this.key = object.getDataKey();
        this.dbref = object.getId();
        this.name = object.getName();
    }

    public SimpleObject(CommonObject commonObject) {
        this.key = commonObject.getDataKey();
        this.dbref = commonObject.getId();
        this.name = commonObject.getName();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDbref() {
        return dbref;
    }

    public void setDbref(String dbref) {
        this.dbref = dbref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
