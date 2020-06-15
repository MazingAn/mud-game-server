package com.mud.game.structs;

import com.mud.game.object.typeclass.NormalObjectObject;
import com.mud.game.worlddata.db.models.NormalObject;

import java.util.ArrayList;
import java.util.List;

public class NormalObjectAppearance {
    /*
    * @ 返回给客户端的装备信息的数据结构
    */
    private String dbref;
    private String name;
    private String desc;
    private List<EmbeddedCommand> cmds;
    private String icon;
    private int quality;

    public NormalObjectAppearance(NormalObjectObject normalObjectObject) {
        this.dbref = normalObjectObject.getId();
        this.name = normalObjectObject.getName();
        this.desc = normalObjectObject.getDescription();
        this.cmds = new ArrayList<>();
        this.icon = normalObjectObject.getIcon();
        this.quality = normalObjectObject.getQuality();
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}
