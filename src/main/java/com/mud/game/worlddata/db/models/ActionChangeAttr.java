package com.mud.game.worlddata.db.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ActionChangeAttr {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String eventKey;
    private String attrName;
    private String changedValue;
    private float odds;
    private String description;

    public ActionChangeAttr() {
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

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public float getOdds() {
        return odds;
    }

    public void setOdds(float odds) {
        this.odds = odds;
    }

    public String getChangedValue() {
        return changedValue;
    }

    public void setChangedValue(String changedValue) {
        this.changedValue = changedValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
