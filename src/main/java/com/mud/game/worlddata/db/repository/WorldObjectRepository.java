package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.WorldObject;
import org.springframework.data.repository.CrudRepository;

public interface WorldObjectRepository extends CrudRepository<WorldObject, Long> {
    public Iterable<WorldObject> findWorldObjectsByTypeClass(String typeClass);
}
