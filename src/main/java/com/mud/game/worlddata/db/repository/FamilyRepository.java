package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Family;
import org.springframework.data.repository.CrudRepository;

public interface FamilyRepository extends CrudRepository<Family, Long> {
    Family findFamilyByDataKey(String dataKey);
}
