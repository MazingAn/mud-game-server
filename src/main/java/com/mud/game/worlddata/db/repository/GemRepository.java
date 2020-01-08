package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Gem;
import org.springframework.data.repository.CrudRepository;

public interface GemRepository extends CrudRepository<Gem, Long> {
}
