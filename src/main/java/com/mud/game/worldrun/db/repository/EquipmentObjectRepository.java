package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.EquipmentObject;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EquipmentObjectRepository extends MongoRepository<EquipmentObject, String> {
    EquipmentObject findEquipmentObjectById(String id);
    EquipmentObject findEquipmentObjectByDataKeyAndOwner(String objectKey, String ownerId);
    List<EquipmentObject> findEquipmentListObjectByDataKeyAndOwner(String objectKey, String ownerId);
}
