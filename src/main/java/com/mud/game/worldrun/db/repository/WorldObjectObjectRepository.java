package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.WorldObjectObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorldObjectObjectRepository extends MongoRepository<WorldObjectObject, String> {
    boolean existsByDataKey(String dataKey);
    WorldObjectObject findWorldObjectObjectById(String id);
    WorldObjectObject findWorldObjectObjectByDataKey(String dataKey);
}
