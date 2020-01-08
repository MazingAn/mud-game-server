package com.mud.game.object.typeclass;

import com.mud.game.object.supertypeclass.WorldObject;
import com.mud.game.worlddata.db.models.LootList;

import java.util.Set;

public class WorldObjectCreator extends WorldObjectObject {
    private String actionName;
    private int lootNumber;
    private boolean lootOnce;
    private float refreshTime;
    private Set<LootList> lootLists;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getLootNumber() {
        return lootNumber;
    }

    public void setLootNumber(int lootNumber) {
        this.lootNumber = lootNumber;
    }

    public boolean isLootOnce() {
        return lootOnce;
    }

    public void setLootOnce(boolean lootOnce) {
        this.lootOnce = lootOnce;
    }

    public float getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(float refreshTime) {
        this.refreshTime = refreshTime;
    }

    public Set<LootList> getLootLists() {
        return lootLists;
    }

    public void setLootLists(Set<LootList> lootLists) {
        this.lootLists = lootLists;
    }
}
