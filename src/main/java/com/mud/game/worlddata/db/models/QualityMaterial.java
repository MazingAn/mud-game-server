package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;

@Entity
@Mark(name = "进阶材料")
public class QualityMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name = "编号")
    private Long id;

    @Mark(name = "装备标识")
    private String dataKey;

    @Mark(name = "数量")
    private int number;

    @Mark(name = "进阶等级")
    private int quality;

    @Mark(name = "需要的材料", link = "allObjects")
    private String dependency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }
}
