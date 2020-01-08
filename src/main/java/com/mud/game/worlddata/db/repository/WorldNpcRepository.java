package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.WorldNpc;
import org.springframework.data.repository.CrudRepository;

public interface WorldNpcRepository extends CrudRepository<WorldNpc, Long> {
    public WorldNpc findWorldNpcByDataKey(String dataKey);
}
