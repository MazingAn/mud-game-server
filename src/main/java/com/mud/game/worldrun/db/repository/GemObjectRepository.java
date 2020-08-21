package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.GemObject;
import com.mud.game.worlddata.db.models.Gem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GemObjectRepository extends MongoRepository<GemObject, String> {
    GemObject findGemObjectById(String id);
    GemObject findGemObjectByDataKeyAndOwner(String objectKey, String ownerId);
    boolean existsByDataKey(String objectKey);

    List<GemObject> findGemByOwnerAndPositionsLike(String id, String positionStr);

    List<GemObject> findGemOjectByOwner(String id);

    List<GemObject> findGemObjectByOwnerAndPositionsLike(String id, String positionStr);

    List<GemObject> findGemObjectByPositionsLike(String positionStr);
}
