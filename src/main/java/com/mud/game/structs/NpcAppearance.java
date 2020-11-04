package com.mud.game.structs;

import com.mud.game.object.typeclass.WorldNpcObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;

public class NpcAppearance extends GameObjectAppearance {

    private String title_name;
    private String school_title_name;
    private int hp;
    private int max_hp;
    private int mp;
    private int max_mp;
    private boolean is_player;
    private CharacterState state;

    public NpcAppearance(WorldNpcObject npc) {
        super(npc);
        if (StringUtils.isNotBlank(npc.getTitle()) && !"None".equals(npc.getTitle())) {
            this.title_name = npc.getTitle();
        } else {
            this.title_name = "";
        }
        if (StringUtils.isNotBlank(npc.getSchoolTitle()) && !"None".equals(npc.getSchoolTitle())) {
            this.school_title_name = npc.getSchoolTitle();
        } else {
            this.school_title_name = "";
        }

        this.hp = npc.getHp();
        this.mp = npc.getMp();
        this.max_hp = npc.getMax_hp();
        this.max_mp = npc.getMax_mp();
        this.is_player = false;
        this.state = npc.getState();
        if (npc.getState().equals(CharacterState.STATE_DEATH)) {
            this.setDesc(StringUtils.isEmpty(npc.getDeathDesc()) ? "此npc暂无死亡描述" : npc.getDeathDesc());
        }
    }

    public CharacterState getState() {
        return state;
    }

    public void setState(CharacterState state) {
        this.state = state;
    }

    public boolean isIs_player() {
        return is_player;
    }

    public void setIs_player(boolean is_player) {
        this.is_player = is_player;
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