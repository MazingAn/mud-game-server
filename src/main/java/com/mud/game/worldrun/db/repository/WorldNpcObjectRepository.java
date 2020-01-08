package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.WorldNpcObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorldNpcObjectRepository extends MongoRepository<WorldNpcObject, String> {
    public boolean existsByDataKey(String dataKey);
    public WorldNpcObject findWorldNpcObjectById(String id);
    public WorldNpcObject findWorldNpcObjectByDataKey(String dataKey);
}
