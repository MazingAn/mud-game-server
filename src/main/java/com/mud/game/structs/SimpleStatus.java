package com.mud.game.structs;

public class SimpleStatus {
    private String key;
    private String name;
    private Object value;
    private int order;

    public SimpleStatus(String key, String name, Object value, int order) {
        this.key = key;
        this.name = name;
        this.value = value;
        this.order = order;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
