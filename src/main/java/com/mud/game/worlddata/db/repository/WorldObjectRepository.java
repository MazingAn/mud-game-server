package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.WorldObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WorldObjectRepository extends PagingAndSortingRepository<WorldObject, Long> {
    Iterable<WorldObject> findWorldObjectsByTypeClass(String typeClass);
    WorldObject deleteWorldObjectById(Long id);
}
