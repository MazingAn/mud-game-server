package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.WorldNpc;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WorldNpcRepository extends PagingAndSortingRepository<WorldNpc, Long> {
    WorldNpc findWorldNpcByDataKey(String dataKey);
    WorldNpc deleteWorldNpcById(Long id);
    Iterable<WorldNpc> findWorldNpcsByLocation(String location);
}
