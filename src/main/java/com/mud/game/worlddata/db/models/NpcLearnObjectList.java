package com.mud.game.worlddata.db.models;

import javax.persistence.*;

@Entity
public class NpcLearnObjectList {
    /*
     * NPC通过道具教授技能所需要的道具
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // npc的key
    @Column(unique = true)
    private String npcKey;
    // 需要的物品
    private String neededObject;
    // 没提交一个object 可以兑换的能量
    private int perPotential;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNpcKey() {
        return npcKey;
    }

    public void setNpcKey(String npcKey) {
        this.npcKey = npcKey;
    }

    public String getNeededObject() {
        return neededObject;
    }

    public void setNeededObject(String neededObject) {
        this.neededObject = neededObject;
    }

    public int getPerPotential() {
        return perPotential;
    }

    public void setPerPotential(int perPotential) {
        this.perPotential = perPotential;
    }
}
