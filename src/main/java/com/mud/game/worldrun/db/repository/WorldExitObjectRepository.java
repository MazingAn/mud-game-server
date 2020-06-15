package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.WorldExitObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorldExitObjectRepository extends MongoRepository<WorldExitObject, String> {
    WorldExitObject findWorldExitObjectByDataKey(String dataKey);
    WorldExitObject findWorldExitObjectById(String id);
    boolean existsByDataKey(String dataKey);
}
