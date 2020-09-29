package com.mud.game.object.typeclass;

import com.mud.game.object.supertypeclass.WorldObject;
import com.mud.game.structs.GamePosition;
import com.mud.game.utils.modelsutils.Mark;

import java.util.HashSet;
import java.util.Set;


public class WorldRoomObject extends WorldObject {
    private String background;
    private String icon;
    private int level;
    private String hangUpCommand;
    private boolean peaceful;
    private GamePosition position;
    private Set<String> players;
    private Set<String> exits;
    private Set<String> things;
    private Set<String> creators;
    private Set<String> npcs;
    private Set<String> events;
    /**
     * room内是否检测到玩家达到犯罪值npc主动攻击
     */
    private boolean canAttack;
    /**
     * 是否在本房间击杀不增加犯罪值
     */
    @Mark(name = "是否在不增加犯罪值")
    private boolean notLegal;

    public WorldRoomObject() {
        this.players = new HashSet<String>();
        this.exits = new HashSet<String>();
        this.things = new HashSet<String>();
        this.creators = new HashSet<String>();
        this.npcs = new HashSet<String>();
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHangUpCommand() {
        return hangUpCommand;
    }

    public void setHangUpCommand(String hangUpCommand) {
        this.hangUpCommand = hangUpCommand;
    }

    public boolean isPeaceful() {
        return peaceful;
    }

    public void setPeaceful(boolean peaceful) {
        this.peaceful = peaceful;
    }

    public Set<String> getPlayers() {
        return players;
    }

    public void setPlayers(Set<String> players) {
        this.players = players;
    }

    public Set<String> getExits() {
        return exits;
    }

    public void setExits(Set<String> exits) {
        this.exits = exits;
    }

    public Set<String> getThings() {
        return things;
    }

    public void setThings(Set<String> things) {
        this.things = things;
    }

    public Set<String> getNpcs() {
        return npcs;
    }

    public void setNpcs(Set<String> npcs) {
        this.npcs = npcs;
    }

    public GamePosition getPosition() {
        return position;
    }

    public void setPosition(GamePosition position) {
        this.position = position;
    }

    public Set<String> getEvents() {
        return events;
    }

    public void setEvents(Set<String> events) {
        this.events = events;
    }

    public Set<String> getCreators() {
        return creators;
    }

    public void setCreators(Set<String> creators) {
        this.creators = creators;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public boolean isNotLegal() {
        return notLegal;
    }

    public void setNotLegal(boolean notLegal) {
        this.notLegal = notLegal;
    }
}
