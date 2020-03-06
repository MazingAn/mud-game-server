package com.mud.game.object.supertypeclass;

import com.mud.game.structs.CommonObjectInfo;
import org.springframework.data.annotation.Id;

import java.util.Map;

public class CommonItemContainer {
    @Id
    private String id;
    private int maxCellNumber;
    private int usedCellNumber;
    private Map<String, Object> items;
    private String owner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaxCellNumber() {
        return maxCellNumber;
    }

    public void setMaxCellNumber(int maxCellNumber) {
        this.maxCellNumber = maxCellNumber;
    }

    public int getUsedCellNumber() {
        return usedCellNumber;
    }

    public void setUsedCellNumber(int usedCellNumber) {
        this.usedCellNumber = usedCellNumber;
    }

    public Map<String, Object> getItems() {
        return items;
    }

    public void setItems(Map<String, Object> items) {
        this.items = items;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
