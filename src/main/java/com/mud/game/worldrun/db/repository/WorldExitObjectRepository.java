package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.WorldExitObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorldExitObjectRepository extends MongoRepository<WorldExitObject, String> {
    public WorldExitObject findWorldExitObjectByDataKey(String dataKey);
    public WorldExitObject findWorldExitObjectById(String id);
    public boolean existsByDataKey(String dataKey);
}
