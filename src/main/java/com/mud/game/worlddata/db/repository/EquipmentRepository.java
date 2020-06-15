package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Equipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EquipmentRepository extends PagingAndSortingRepository<Equipment, Long> {
    Equipment findEquipmentByDataKey(String dataKey);
    boolean existsByDataKey(String dataKey);
}
