package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"hangup_type", "target"}))
@Mark(name="挂机奖励")
public class HangupObject {
    /*
    游戏挂机时的奖励
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name="编号")
    private Long id;
    @Column(name="hangup_type")
    @Mark(name="挂机类型", link = "hangupType")
    private String hangupType;
    @Mark(name="目标", link = "allObjects")
    @Column(name="target")
    private String target;
    @Mark(name="概率")
    private float odds;
    @Mark(name="描述")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
