package com.mud.game.object.manager;

import com.mud.game.object.typeclass.BagpackObject;
import com.mud.game.object.typeclass.WareHouseObject;

import java.util.HashMap;

public class WareHouseObjectManager {
    public static WareHouseObject create(String owner){
        WareHouseObject wareHouseObject = new WareHouseObject();
        wareHouseObject.setItems(new HashMap<>());
        wareHouseObject.setMaxCellNumber(20);
        wareHouseObject.setUsedCellNumber(0);
        wareHouseObject.setOwner(owner);
        wareHouseObject.setLocked(false);
        wareHouseObject.setPassword(null);
        return wareHouseObject;
    }
}
