package com.mud.game.structs;

import com.mud.game.object.typeclass.PlayerCharacter;

public class PuppetInfo {

    private String dbref;
    private String icon;
    private String gender;
    private String name;

    public PuppetInfo() {
    }

    public PuppetInfo(PlayerCharacter playerCharacter) {
        this.dbref = playerCharacter.getId();
        this.name = playerCharacter.getName();
        this.gender = playerCharacter.getGender();
        this.icon = "";
    }

    public String getDbref() {
        return dbref;
    }

    public void setDbref(String dbref) {
        this.dbref = dbref;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
