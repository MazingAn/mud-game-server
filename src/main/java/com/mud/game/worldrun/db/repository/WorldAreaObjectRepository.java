package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.WorldAreaObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorldAreaObjectRepository extends MongoRepository<WorldAreaObject, String> {
    public WorldAreaObject findWorldAreaObjectByDataKey(String dataKey);
    public boolean existsByDataKey(String dataKey);
}
