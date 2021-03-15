package com.mud.game.worldrun.db.repository;

import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WorldNpcObjectRepository extends MongoRepository<WorldNpcObject, String> {
    boolean existsByDataKey(String dataKey);
    WorldNpcObject findWorldNpcObjectById(String id);
    WorldNpcObject findWorldNpcObjectByDataKey(String dataKey);

    List<WorldNpcObject> findListWorldNpcObjectByCanWanderRoom(boolean b);

    boolean existsByName(String name);

    CommonCharacter findWorldNpcObjectByName(String name);
}
