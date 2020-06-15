package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.GemObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GemObjectRepository extends MongoRepository<GemObject, String> {
    GemObject findGemObjectById(String id);
}
