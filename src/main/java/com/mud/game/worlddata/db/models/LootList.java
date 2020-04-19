package com.mud.game.worlddata.db.models;


import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"provider", "object"})})
public class LootList {
    /*
    * 物品生成表： 物品生成器与生成的物品之间的对应关系
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String provider;
    private String object;
    private int number;
    private float odds;
    private String dependQuest;
    private String lootCondition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getOdds() {
        return odds;
    }

    public void setOdds(float odds) {
        this.odds = odds;
    }

    public String getDependQuest() {
        return dependQuest;
    }

    public void setDependQuest(String dependQuest) {
        this.dependQuest = dependQuest;
    }

    public String getLootCondition() {
        return lootCondition;
    }

    public void setLootCondition(String lootCondition) {
        this.lootCondition = lootCondition;
    }
}
