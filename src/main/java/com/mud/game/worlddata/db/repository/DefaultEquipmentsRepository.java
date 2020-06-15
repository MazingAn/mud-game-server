package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.DefaultEquipments;
import com.mud.game.worlddata.db.models.DefaultSkills;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DefaultEquipmentsRepository extends PagingAndSortingRepository<DefaultEquipments, Long> {
}
