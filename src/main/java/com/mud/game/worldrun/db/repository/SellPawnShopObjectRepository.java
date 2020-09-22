package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.SellPawnShopObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SellPawnShopObjectRepository extends MongoRepository<SellPawnShopObject, String> {
    SellPawnShopObject findSellPawnShopObjectByOwner(String id);
}
