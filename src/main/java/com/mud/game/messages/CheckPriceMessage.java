package com.mud.game.messages;

public class CheckPriceMessage {
    private int price;
    private String dataKey;

    public CheckPriceMessage(String dataKey,int price) {
        this.dataKey = dataKey;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }
}
