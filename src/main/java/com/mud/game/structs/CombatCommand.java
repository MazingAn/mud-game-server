package com.mud.game.structs;

public class CombatCommand {
    private String name;
    private String key;
    private String icon;

    public CombatCommand(String name, String key, String icon) {
        this.name = name;
        this.key = key;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
