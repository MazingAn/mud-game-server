package com.mud.game.worldrun.db.repository;

import com.mud.game.object.account.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepository extends MongoRepository<Player, String> {
    public Player findPlayerById(String id);
    public boolean existsByUsername(String username);
    public Player findByUsername(String username);
}
