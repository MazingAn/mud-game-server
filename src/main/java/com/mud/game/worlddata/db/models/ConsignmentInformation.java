package com.mud.game.worlddata.db.models;

import com.mud.game.utils.modelsutils.Mark;

import javax.persistence.*;

/**
 * 寄售信息
 */
@Entity
@Mark(name = "寄售信息")
public class ConsignmentInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Mark(name = "编号")
    private Long id;
    @Mark(name = "用户主键")
    private String palyerId;
    @Mark(name = "商品主键")
    private String objectId;
    @Mark(name = "商品名称")
    private String objectName;
    @Mark(name = "查询名称")
    private String name;
    @Mark(name = "品级")
    private int quality;
    @Mark(name = "价格")
    private int price;
    @Mark(name = "单位（如：金子的单位是  锭）")
    private String moneyType;
    @Mark(name = "创建时间")
    private String createTime;
    @Mark(name = "数量")
    private int number;
    @Mark(name = "分类")
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPalyerId() {
        return palyerId;
    }

    public void setPalyerId(String palyerId) {
        this.palyerId = palyerId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
        this.name = objectName;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
