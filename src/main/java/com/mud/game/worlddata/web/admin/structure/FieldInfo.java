package com.mud.game.worlddata.web.admin.structure;

public class FieldInfo {
    public String name;
    public String verboseName;
    public boolean nullable;
    public int maxLength;
    public int minLength;
    public Class type;

    public FieldInfo() {
        this.nullable = true;
        this.maxLength = 512;
        this.minLength = 0;
    }
}
