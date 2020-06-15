package com.mud.game.worldrun.db.repository;

import com.mud.game.object.account.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepository extends MongoRepository<Player, String> {
    Player findPlayerById(String id);
    boolean existsByUsername(String username);
    Player findByUsername(String username);
}
