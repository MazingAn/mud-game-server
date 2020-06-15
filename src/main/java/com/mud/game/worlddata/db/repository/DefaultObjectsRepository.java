package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.DefaultObjects;
import com.mud.game.worlddata.db.models.DefaultSkills;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DefaultObjectsRepository extends PagingAndSortingRepository<DefaultObjects, Long> {
    Iterable<DefaultObjects> findDefaultObjectsByTarget(String targetKey);
}
