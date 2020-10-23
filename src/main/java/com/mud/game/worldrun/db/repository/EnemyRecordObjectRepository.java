package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.EnemyRecordObject;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EnemyRecordObjectRepository extends MongoRepository<EnemyRecordObject, String> {
    List<EnemyRecordObject> findEnemyRecordObjectByPlayerIdAndEnemyId(String id, String dbref);
}
