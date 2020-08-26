package com.mud.game.messages;

import com.alibaba.fastjson.JSONArray;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.models.ConsignmentInformation;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsignmentInformationsMsg {
    private String sellerName;
    private String sellerId;
    private String objectName;
    private String objectId;
    private List<Map<String, Object>> price;
    private int number;
    private int quality;
    private String category;
    private Long auctionId;
    private String description;

    public ConsignmentInformationsMsg(PlayerCharacter seller, ConsignmentInformation goods) {
        if (seller.getId().equals(goods.getPalyerId())) {
            this.sellerName = "你";
        } else {
            this.sellerName = goods.getPalyerName();
        }
        this.objectName = goods.getObjectName();
        this.objectId = goods.getObjectId();
        this.number = goods.getNumber();
        this.quality = goods.getQuality();
        this.category = goods.getCategory();
        this.auctionId = goods.getId();
        this.description = goods.getDescription();
        String price = goods.getPrice();
        this.price = new ArrayList<>();
        if (StringUtils.isNotBlank(price)) {
            com.alibaba.fastjson.JSONArray jsonArray = JSONArray.parseArray(price);
            Map<String, Integer> map = new HashMap<>();
            com.alibaba.fastjson.JSONObject jsonObject = null;
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                    map.put(entry.getKey(), Integer.parseInt(entry.getValue().toString()));
                }
            }
            if (map.containsKey("OBJECT_JINYEZI")) {
                this.price.add(new HashMap<String, Object>() {{
                    put("OBJECT_JINYEZI", map.get("OBJECT_JINYEZI"));
                }});
                return;
            }
            if (map.containsKey("OBJECT_JINZI")) {
                this.price.add(new HashMap<String, Object>() {{
                    put("OBJECT_JINZI", map.get("OBJECT_JINZI"));
                }});
            }
            if (map.containsKey("OBJECT_YINLIANG")) {
                this.price.add(new HashMap<String, Object>() {{
                    put("OBJECT_YINLIANG", map.get("OBJECT_YINLIANG"));
                }});
            }
        }
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

    public List<Map<String, Object>> getPrice() {
        return price;
    }

    public void setPrice(List<Map<String, Object>> price) {
        this.price = price;
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
