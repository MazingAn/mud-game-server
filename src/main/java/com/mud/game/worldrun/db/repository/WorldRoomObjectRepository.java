package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.WorldRoomObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorldRoomObjectRepository extends MongoRepository<WorldRoomObject, String> {
    WorldRoomObject findWorldRoomObjectByDataKey(String dataKey);
    WorldRoomObject findWorldRoomObjectById(String roomId);
    boolean existsByDataKey(String dataKey);
}
