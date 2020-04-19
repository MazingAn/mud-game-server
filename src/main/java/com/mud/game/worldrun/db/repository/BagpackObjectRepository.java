package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.BagpackObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BagpackObjectRepository extends MongoRepository<BagpackObject, String> {
    BagpackObject findBagpackObjectById(String id);
}
