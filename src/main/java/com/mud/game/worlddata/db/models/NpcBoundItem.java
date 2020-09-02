package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Mark(name = "npc掉落物品绑定")
public class NpcBoundItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name = "编号")
    private Long id;

    @Mark(name = "npc标识")
    private String npcDataKey;

    @Mark(name = "物品标识")
    private String objectDataKey;

    @Mark(name = "获取概率")
    private double acquisitionProbability;

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

    public String getObjectDataKey() {
        return objectDataKey;
    }

    public void setObjectDataKey(String objectDataKey) {
        this.objectDataKey = objectDataKey;
    }

    public double getAcquisitionProbability() {
        return acquisitionProbability;
    }

    public void setAcquisitionProbability(double acquisitionProbability) {
        this.acquisitionProbability = acquisitionProbability;
    }
}
