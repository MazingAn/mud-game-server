package com.mud.game.structs;

import com.mud.game.object.manager.CommonItemContainerManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.SkillBookObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SkillBookObjectAppearance {
    /*
     * @ 返回给客户端的装备信息的数据结构
     */
    private String dbref;
    private String name;
    private String desc;
    private List<EmbeddedCommand> cmds;
    private String icon;
    private int quality;
    private int number;

    public SkillBookObjectAppearance(SkillBookObject skillBookObject, PlayerCharacter playerCharacter) {
        this.dbref = skillBookObject.getId();
        this.name = skillBookObject.getName();
        this.desc = skillBookObject.getDescription();
        this.cmds = new ArrayList<>();
        this.icon = skillBookObject.getIcon();
        this.quality = skillBookObject.getQuality();
        if (skillBookObject.getOwner().equals(playerCharacter.getId())) {
            this.number = CommonItemContainerManager.findObjectNumberById(playerCharacter, skillBookObject.getId());
        } else {
            this.number = 0;
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
