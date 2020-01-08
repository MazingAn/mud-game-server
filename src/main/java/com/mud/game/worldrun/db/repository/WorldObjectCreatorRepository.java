package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.WorldObjectCreator;
import com.mud.game.object.typeclass.WorldObjectObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorldObjectCreatorRepository extends MongoRepository<WorldObjectCreator, String> {
    public boolean existsByDataKey(String dataKey);
    public WorldObjectCreator findWorldObjectCreatorById(String id);
    public WorldObjectCreator findWorldObjectCreatorByDataKey(String dataKey);
}
