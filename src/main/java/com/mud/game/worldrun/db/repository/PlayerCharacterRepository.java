package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.PlayerCharacter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlayerCharacterRepository extends MongoRepository<PlayerCharacter, String>{
    boolean existsByName(String name);
    PlayerCharacter findPlayerCharacterById(String id);
    Iterable<PlayerCharacter> findPlayerCharactersBySchool(String school);

    PlayerCharacter findPlayerCharacterByName(String name);

    List<PlayerCharacter> findPlayerCharacterByLocation(String location);
}