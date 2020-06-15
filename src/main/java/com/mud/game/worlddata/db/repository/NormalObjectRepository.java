package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.NormalObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NormalObjectRepository extends PagingAndSortingRepository<NormalObject, Long> {
    NormalObject findNormalObjectByDataKey(String dataKey);
    Iterable<NormalObject> findNormalObjectsByCategory(String category);
    boolean existsByDataKey(String dataKey);
}
