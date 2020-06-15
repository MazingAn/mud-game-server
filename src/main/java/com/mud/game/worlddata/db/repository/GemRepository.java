package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Gem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GemRepository extends PagingAndSortingRepository<Gem, Long> {
    Gem findGemByDataKey(String dataKey);
    boolean existsByDataKey(String dataKey);
}
