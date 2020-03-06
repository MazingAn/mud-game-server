package com.mud.game.messages;

import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.CommonObjectInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BagPackListMessage {

    private ArrayList<Object> inventory;

    public BagPackListMessage(ArrayList<Object> inventory) {
        this.inventory = inventory;
    }

    public ArrayList<Object> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Object> inventory) {
        this.inventory = inventory;
    }
}
