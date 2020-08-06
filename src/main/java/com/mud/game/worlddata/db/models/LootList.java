package com.mud.game.worlddata.db.models;


import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"provider", "object"})})
public class LootList {
    /*
    * 物品生成表： 物品生成器与生成的物品之间的对应关系
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name="编号")
    private Long id;
    @Mark(name="物品生成器", link = "worldObjectCreator")
    private String provider;
    @Mark(name="生成物品", link = "allObjects")
    private String object;
    @Mark(name="数量")
    private int number;
    @Mark(name="概率")
    private float odds;
    @Mark(name="依赖任务", link = "quest")
    private String dependQuest;
    @Mark(name="拾取条件")
    private String lootCondition;

    @Mark(name = "描述名称")
    private String name;
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
