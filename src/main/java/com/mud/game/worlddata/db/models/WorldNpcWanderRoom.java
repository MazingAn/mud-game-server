package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Mark(name = "npc游荡区域")
public class WorldNpcWanderRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name = "编号")
    private Long id;

    @Mark(name = "npc", link = "npc")
    private String npcDataKey;

    @Mark(name = "房间", link = "worldRoom")
    private String roomDataKey;

    @Mark(name = "间隔时间")
    private float actionTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNpcDataKey() {
        return npcDataKey;
    }

    public void setNpcDataKey(String npcDataKey) {
        this.npcDataKey = npcDataKey;
    }

    public String getRoomDataKey() {
        return roomDataKey;
    }

    public void setRoomDataKey(String roomDataKey) {
        this.roomDataKey = roomDataKey;
    }

    public float getActionTime() {
        return actionTime;
    }

    public void setActionTime(float actionTime) {
        this.actionTime = actionTime;
    }
}
