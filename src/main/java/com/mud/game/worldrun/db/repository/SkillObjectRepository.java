package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.SkillObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SkillObjectRepository extends MongoRepository<SkillObject, String> {
    SkillObject findSkillObjectById(String id);
    SkillObject findSkillObjectByDataKeyAndOwner(String dataKey, String ownerId);
    Iterable<SkillObject> findSkillObjectListByDataKey(String dataKey);
    void removeSkillObjectsByOwner(String ownerId);
}
