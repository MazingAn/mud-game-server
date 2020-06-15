package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Family;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FamilyRepository extends PagingAndSortingRepository<Family, Long> {
    Family findFamilyByDataKey(String dataKey);
}
