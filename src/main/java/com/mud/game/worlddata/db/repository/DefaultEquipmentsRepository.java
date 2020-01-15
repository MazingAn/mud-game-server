package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.DefaultEquipments;
import com.mud.game.worlddata.db.models.DefaultSkills;
import org.springframework.data.repository.CrudRepository;

public interface DefaultEquipmentsRepository extends CrudRepository<DefaultEquipments, Long> {
}
