package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.WorldNpcObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorldNpcObjectRepository extends MongoRepository<WorldNpcObject, String> {
    boolean existsByDataKey(String dataKey);
    WorldNpcObject findWorldNpcObjectById(String id);
    WorldNpcObject findWorldNpcObjectByDataKey(String dataKey);
}
