package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Mark(name = "NPC传送表")
public class TransList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name = "标识")
    private long id;

    @Mark(name="传送NPC", link = "transfer")
    private String npc;

    @Mark(name="传送目的地", link = "worldRoom")
    private String room;

    @Mark(name="需要的物品", link = "allObject")
    private String needObject;

    @Mark(name = "需要的物品数量")
    private int number;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNpc() {
        return npc;
    }

    public void setNpc(String npc) {
        this.npc = npc;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getNeedObject() {
        return needObject;
    }

    public void setNeedObject(String needObject) {
        this.needObject = needObject;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
