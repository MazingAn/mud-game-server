package com.mud.game.structs;

import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.PlayerCharacter;

public class CharacterCombatStatus {

    private int mp;
    private int limit_mp;
    private int max_mp;
    private int hp;
    private int limit_hp;
    private int max_hp;
    private String dbref;

    public CharacterCombatStatus() {
    }

    public CharacterCombatStatus(CommonCharacter character) {
        this.mp = character.getMp();
        this.limit_mp = character.getLimit_mp();
        this.max_mp = character.getMax_mp();
        this.hp = character.getHp();
        this.limit_hp = character.getLimit_hp();
        this.max_hp = character.getMax_hp();
        this.dbref = character.getId();
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getLimit_mp() {
        return limit_mp;
    }

    public void setLimit_mp(int limit_mp) {
        this.limit_mp = limit_mp;
    }

    public int getMax_mp() {
        return max_mp;
    }

    public void setMax_mp(int max_mp) {
        this.max_mp = max_mp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getLimit_hp() {
        return limit_hp;
    }

    public void setLimit_hp(int limit_hp) {
        this.limit_hp = limit_hp;
    }

    public int getMax_hp() {
        return max_hp;
    }

    public void setMax_hp(int max_hp) {
        this.max_hp = max_hp;
    }

    public String getDbref() {
        return dbref;
    }

    public void setDbref(String dbref) {
        this.dbref = dbref;
    }
}
