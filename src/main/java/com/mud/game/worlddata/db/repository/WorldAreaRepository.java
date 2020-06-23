package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.WorldArea;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WorldAreaRepository extends PagingAndSortingRepository<WorldArea, Long> {
    WorldArea deleteWorldAreaById(Long id);
    WorldArea findWorldAreaByDataKey(String dataKey);
}
