package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.SkillBookObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SkillBookObjectRepository extends MongoRepository<SkillBookObject, String> {
    SkillBookObject findSkillBookObjectById(String id);
}
