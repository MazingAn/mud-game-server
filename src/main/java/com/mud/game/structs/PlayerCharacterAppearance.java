package com.mud.game.structs;

import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.PlayerTitle;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

public class PlayerCharacterAppearance {

    private String desc;
    private String title;
    private String school;
    private String name;
    private int hp;
    private int max_hp;
    private int limit_hp;
    private int mp;
    private int max_mp;
    private int limit_mp;
    private boolean is_player;
    private List<EmbeddedCommand> cmds;


    public PlayerCharacterAppearance(PlayerCharacter playerCharacter) {
        this.hp = playerCharacter.getHp();
        this.max_hp = playerCharacter.getMax_hp();
        this.limit_hp = playerCharacter.getLimit_hp();
        this.mp = playerCharacter.getMp();
        this.max_mp = playerCharacter.getMax_mp();
        this.limit_mp = playerCharacter.getLimit_mp();
        this.is_player = true;
        this.name = playerCharacter.getName();
        if (StringUtils.isNotBlank(playerCharacter.getSchool())) {
            this.school = DbMapper.schoolRepository.findSchoolByDataKey(playerCharacter.school).getName();
        } else {
            this.school = "";
        }
        if (StringUtils.isNotBlank(playerCharacter.getTitle())) {
            this.title = DbMapper.playerTitleRepository.findPlayerTitleByDataKey(playerCharacter.title).getName();
        } else {
            this.title = "";
        }
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getLimit_hp() {
        return limit_hp;
    }

    public void setLimit_hp(int limit_hp) {
        this.limit_hp = limit_hp;
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

    public int getLimit_mp() {
        return limit_mp;
    }

    public void setLimit_mp(int limit_mp) {
        this.limit_mp = limit_mp;
    }

    public boolean isIs_player() {
        return is_player;
    }

    public void setIs_player(boolean is_player) {
        this.is_player = is_player;
    }

    public List<EmbeddedCommand> getCmds() {
        return cmds;
    }

    public void setCmds(List<EmbeddedCommand> cmds) {
        this.cmds = cmds;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    private String getTitleDisplay(String titleKey) {
        if (titleKey != null && !(titleKey.trim().equals(""))) {
            PlayerTitle title = DbMapper.playerTitleRepository.findPlayerTitleByDataKey(titleKey);
            return title.getName();
        }
        return "";
    }
}
