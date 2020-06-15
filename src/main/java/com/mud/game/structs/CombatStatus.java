package com.mud.game.structs;

import com.mud.game.object.supertypeclass.CommonCharacter;

import java.util.Map;
import java.util.Set;

public class CombatStatus {
    private int max_hp;
    private int limit_hp;
    private int hp;
    private int max_mp;
    private int mp;
    private int limit_mp;
    private Map<String, Map<String, Object>> buffers;

    public CombatStatus() {
    }

    public CombatStatus(CommonCharacter character) {
        this.max_hp = character.getMax_hp();
        this.limit_hp = character.getLimit_hp();
        this.hp = character.getHp();
        this.max_mp = character.getMax_mp();
        this.mp = character.getMp();
        this.limit_mp = character.getLimit_mp();
        this.buffers = new BufferInfo(character).getBuffers();
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMax_mp() {
        return max_mp;
    }

    public void setMax_mp(int max_mp) {
        this.max_mp = max_mp;
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

    public Map<String, Map<String, Object>> getBuffers() {
        return buffers;
    }

    public void setBuffers(Map<String, Map<String, Object>> buffers) {
        this.buffers = buffers;
    }
}
