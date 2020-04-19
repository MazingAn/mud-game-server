package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.WorldObjectCreator;
import com.mud.game.object.typeclass.WorldObjectObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorldObjectCreatorRepository extends MongoRepository<WorldObjectCreator, String> {
    boolean existsByDataKey(String dataKey);
    WorldObjectCreator findWorldObjectCreatorById(String id);
    WorldObjectCreator findWorldObjectCreatorByDataKey(String dataKey);
}
