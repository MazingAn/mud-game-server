package com.mud.game.messages;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Shop;
import com.mud.game.worlddata.db.models.ShopGoods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenShopMessage {
    private Map<String, Object> shop;

    public OpenShopMessage() {
        Shop shopRecord = DbMapper.shopRepository.findShopBySystemShop(true);
        this.shop = new HashMap<>();
        shop.put("dbref", shopRecord.getDataKey());
        shop.put("name",  shopRecord.getName());
        shop.put("desc", shopRecord.getDescription());
        shop.put("icon", shopRecord.getIcon());
        List<ShopGoodsStatus> goodsList = new ArrayList<>();
        for(ShopGoods shopGood : DbMapper.shopGoodsRepository.findShopGoodsByShop(shopRecord.getDataKey())){
            goodsList.add(new ShopGoodsStatus(shopGood));
        }
        shop.put("goods", goodsList);
    }

    public OpenShopMessage(String shopKey) {
        this.shop = new HashMap<>();
        Shop shopRecord = DbMapper.shopRepository.findShopByDataKey(shopKey);
        shop.put("dbref", shopKey);
        shop.put("name",  shopRecord.getName());
        shop.put("desc", shopRecord.getDescription());
        shop.put("icon", shopRecord.getIcon());
        List<ShopGoodsStatus> goodsList = new ArrayList<>();
        for(ShopGoods shopGood : DbMapper.shopGoodsRepository.findShopGoodsByShop(shopKey)){
            goodsList.add(new ShopGoodsStatus(shopGood));
        }
        shop.put("goods", goodsList);
    }

    public Map<String, Object> getShop() {
        return shop;
    }

    public void setShop(Map<String, Object> shop) {
        this.shop = shop;
    }

    class ShopGoodsStatus{
        private String dbref;
        private String name;
        private String desc;
        private int number;
        private int price;
        private String unit;
        private String icon;

        public ShopGoodsStatus(ShopGoods record) {
            this.dbref = record.getDataKey();
            this.name = record.getName();
            this.desc = record.getDescription();
            this.number = record.getNumber();
            this.price = record.getPrice();
            this.unit = record.getUnit();
            this.icon = record.getIcon();
        }

        public String getDbref() {
            return dbref;
        }

        public void setDbref(String dbref) {
            this.dbref = dbref;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
