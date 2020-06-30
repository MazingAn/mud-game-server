package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.EquipmentObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EquipmentObjectRepository extends MongoRepository<EquipmentObject, String> {
    EquipmentObject findEquipmentObjectById(String id);
    EquipmentObject findEquipmentObjectByDataKeyAndOwner(String objectKey, String ownerId);
}
