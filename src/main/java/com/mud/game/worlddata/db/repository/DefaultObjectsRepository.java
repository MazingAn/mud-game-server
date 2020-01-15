package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.DefaultObjects;
import com.mud.game.worlddata.db.models.DefaultSkills;
import org.springframework.data.repository.CrudRepository;

public interface DefaultObjectsRepository extends CrudRepository<DefaultObjects, Long> {
}
