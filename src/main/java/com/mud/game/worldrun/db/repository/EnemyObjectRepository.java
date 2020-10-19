package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.EnemyObject;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EnemyObjectRepository extends MongoRepository<EnemyObject, String> {
    List<EnemyObject> findListEnemyObjectByEnemyId(String id);

    boolean existsByEnemyId(String enemyId);

    List<EnemyObject> findListEnemyObjectByPlayerId(String id);

    boolean existsByPlayerIdAndEnemyId(String playerId, String enemyId);

    EnemyObject findByPlayerIdAndEnemyId(String id, String enemyId);

    void deleteEnemyObjectByEnemyIdAndPlayerId(String friendId, String id);
}
