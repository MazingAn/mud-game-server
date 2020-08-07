package com.mud.game.messages;

import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.models.ConsignmentInformation;

public class ConsignmentInformationsMsg {
    private String sellerName;
    private String sellerId;
    private String objectName;
    private String objectId;
    private int price;
    private String moneyType;
    private int number;
    private int quality;
    private String category;
    private Long auctionId;
    private String description;

    public ConsignmentInformationsMsg(PlayerCharacter seller, ConsignmentInformation goods) {
        this.sellerName = seller.getName();
        this.sellerId = seller.getId();
        this.objectName = goods.getObjectName();
        this.objectId = goods.getObjectId();
        this.price = goods.getPrice();
        this.moneyType = goods.getMoneyType();
        this.number = goods.getNumber();
        this.quality = goods.getQuality();
        this.category = goods.getCategory();
        this.auctionId = goods.getId();
        this.description = goods.getDescription();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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
}
