package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.PlayerTitle;
import org.springframework.data.repository.CrudRepository;

public interface PlayerTitleRepository extends CrudRepository<PlayerTitle, Long> {
    PlayerTitle findPlayerTitleByDataKey(String dataKey);
}
