package com.mud.game.structs;

import com.mud.game.object.supertypeclass.BaseGameObject;
import com.mud.game.object.typeclass.WorldObjectObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameObjectAppearance {
    private String dbref;
    private String name;
    private String desc;
    private List<EmbeddedCommand> cmds;

    public GameObjectAppearance(BaseGameObject obj) {
        this.dbref = obj.getId();
        this.name = obj.getName();
        this.desc = obj.getDescription();
        this.cmds = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDbref() {
        return dbref;
    }

    public void setDbref(String dbref) {
        this.dbref = dbref;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<EmbeddedCommand> getCmds() {
        return cmds;
    }

    public void setCmds(List<EmbeddedCommand> cmds) {
        this.cmds = cmds;
    }
}
