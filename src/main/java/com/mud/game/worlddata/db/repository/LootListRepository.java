package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.LootList;
import org.springframework.data.repository.CrudRepository;

public interface LootListRepository extends CrudRepository<LootList, Long> {
    public Iterable<LootList> getLootListsByProvider(String provider);
}
