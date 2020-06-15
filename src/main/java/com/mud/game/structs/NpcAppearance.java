package com.mud.game.structs;

import com.mud.game.object.typeclass.WorldNpcObject;

public class NpcAppearance extends GameObjectAppearance{

    private String title_name;
    private String school_title_name;
    private String school;
    private int hp;
    private int max_hp;
    private int mp;
    private int max_mp;

    public NpcAppearance(WorldNpcObject npc) {
        super(npc);
        this.title_name = npc.getTitle();
        this.school_title_name = npc.getSchoolTitle();
        this.school = npc.getSchool();
        this.hp = npc.getHp();
        this.mp = npc.getMp();
        this.max_hp = npc.getMax_hp();
        this.max_mp = npc.getMax_mp();
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMax_hp() {
        return max_hp;
    }

    public void setMax_hp(int max_hp) {
        this.max_hp = max_hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getMax_mp() {
        return max_mp;
    }

    public void setMax_mp(int max_mp) {
        this.max_mp = max_mp;
    }
}