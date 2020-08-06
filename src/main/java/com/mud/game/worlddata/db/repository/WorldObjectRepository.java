package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.WorldObject;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface WorldObjectRepository extends SpecificationRepository<WorldObject, Long> {
    Iterable<WorldObject> findWorldObjectsByTypeClass(String typeClass);
    WorldObject deleteWorldObjectById(Long id);
}
