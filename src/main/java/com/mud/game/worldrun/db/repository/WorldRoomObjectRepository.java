package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.WorldRoomObject;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WorldRoomObjectRepository extends MongoRepository<WorldRoomObject, String> {
    WorldRoomObject findWorldRoomObjectByDataKey(String dataKey);
    WorldRoomObject findWorldRoomObjectById(String roomId);
    boolean existsByDataKey(String dataKey);

    List<WorldRoomObject> findWorldRoomObjectByLocation(String location);
}
