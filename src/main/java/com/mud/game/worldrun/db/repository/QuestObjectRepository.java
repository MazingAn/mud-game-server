package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.QuestObject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface QuestObjectRepository extends MongoRepository<QuestObject, String> {
    QuestObject findQuestObjectById(String id);
}
