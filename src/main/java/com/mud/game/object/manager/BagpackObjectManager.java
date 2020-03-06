package com.mud.game.object.manager;

import com.mud.game.object.typeclass.BagpackObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.HashMap;

public class BagpackObjectManager {

    public static BagpackObject create(String owner){
        BagpackObject bagpackObject = new BagpackObject();
        bagpackObject.setItems(new HashMap<>());
        bagpackObject.setMaxCellNumber(20);
        bagpackObject.setUsedCellNumber(0);
        bagpackObject.setOwner(owner);
        return bagpackObject;
    }

}
