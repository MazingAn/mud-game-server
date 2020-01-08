package com.mud.game.worlddata.db.models;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"hangupType", "target"}))
public class HangupObject {
    /*
    游戏挂机时的奖励
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String hangupType;
    private String target;
    private float odds;
    private String description;



    public String getHangupType() {
        return hangupType;
    }

    public void setHangupType(String hangupType) {
        this.hangupType = hangupType;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public float getOdds() {
        return odds;
    }

    public void setOdds(float odds) {
        this.odds = odds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
