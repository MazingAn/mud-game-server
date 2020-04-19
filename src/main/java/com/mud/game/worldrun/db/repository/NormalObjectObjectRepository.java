package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.NormalObjectObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NormalObjectObjectRepository extends MongoRepository<NormalObjectObject, String> {
    NormalObjectObject findNormalObjectObjectByDataKey(String dataKey);
    NormalObjectObject findNormalObjectObjectByDataKeyAndOwner(String dataKey, String ownerId);
}
