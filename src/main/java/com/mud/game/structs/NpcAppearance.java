package com.mud.game.structs;

import com.mud.game.object.typeclass.WorldNpcObject;

public class NpcAppearance extends GameObjectAppearance{

    private String title_name;
    private String school_title_name;
    private String school;

    public NpcAppearance(WorldNpcObject npc) {
        super(npc);
        this.title_name = npc.getTitle();
        this.school_title_name = npc.getSchoolTitle();
        this.school = npc.getSchool();
    }


    public String getTitle_name() {
        return title_name;
    }

    public void setTitle_name(String title_name) {
        this.title_name = title_name;
    }

    public String getSchool_title_name() {
        return school_title_name;
    }

    public void setSchool_title_name(String school_title_name) {
        this.school_title_name = school_title_name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}