package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.PlayerCharacter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerCharacterRepository extends MongoRepository<PlayerCharacter, String>{
    boolean existsByName(String name);
    PlayerCharacter findPlayerCharacterById(String id);
}