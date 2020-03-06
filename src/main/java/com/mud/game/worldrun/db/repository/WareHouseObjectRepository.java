package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.BagpackObject;
import com.mud.game.object.typeclass.WareHouseObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WareHouseObjectRepository extends MongoRepository<WareHouseObject, String> {
    WareHouseObject findWareHouseObjectById(String id);
}
