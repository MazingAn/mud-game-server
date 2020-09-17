package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * 物品基本价格表（考虑锻造影响价格）
 */
@Entity
@Mark(name = "物品价格")
public class ObjectBindPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name = "编号")
    private Long id;

    @Mark(name = "标识")
    @NotNull
    private String dataKey;

    @Mark(name = "价格")
    private int price;

    public Long getId() {
        return id;
    }

    public ObjectBindPrice(Long id, String dataKey, int price) {
        this.id = id;
        this.dataKey = dataKey;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
