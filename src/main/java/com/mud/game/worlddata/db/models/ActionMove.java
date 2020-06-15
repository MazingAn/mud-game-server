package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;

@Entity
public class ActionMove {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name="编号")
    private Long id;
    @Mark(name="事件", link = "event")
    @Column(unique = true)
    private String eventKey;
    @Mark(name="房间", link="worldRoom")
    private String roomKey;
    @Mark(name="概率")
    private float odds;
    @Mark(name="描述")
    private String description;

    public ActionMove() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getRoomKey() {
        return roomKey;
    }

    public void setRoomKey(String roomKey) {
        this.roomKey = roomKey;
    }

    public float getOdds() {
        return odds;
    }

    public void setOdds(float odds) {
        this.odds = odds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
