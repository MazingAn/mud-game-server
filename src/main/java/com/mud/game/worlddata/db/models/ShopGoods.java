package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;

@Entity
@Mark(name = "商品")
public class ShopGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name = "编号")
    private Long id;

    @Mark(name = "标识")
    private String dataKey;

    @Mark(name = "名称")
    private String name;

    @Mark(name = "描述")
    private String description;

    @Mark(name = "商店", link = "shop")
    private String shop;

    @Mark(name = "商品", link = "allObjects")
    private String goods;

    @Mark(name = "数量")
    private int number;

    @Mark(name = "价格")
    private int price;

    @Mark(name = "货币单位", link = "moneyType")
    private String unit;

    @Mark(name = "购买条件")
    private String buyCondition;

    @Mark(name = "图标")
    private String icon;

    @Mark(name = "分类")
    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBuyCondition() {
        return buyCondition;
    }

    public void setBuyCondition(String buyCondition) {
        this.buyCondition = buyCondition;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
