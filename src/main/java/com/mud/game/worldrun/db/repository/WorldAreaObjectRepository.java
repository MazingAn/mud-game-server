package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.WorldAreaObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorldAreaObjectRepository extends MongoRepository<WorldAreaObject, String> {
    WorldAreaObject findWorldAreaObjectByDataKey(String dataKey);
    boolean existsByDataKey(String dataKey);
}
