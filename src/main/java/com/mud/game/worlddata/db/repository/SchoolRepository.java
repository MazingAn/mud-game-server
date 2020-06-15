package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.School;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SchoolRepository extends PagingAndSortingRepository<School, Long> {
    School findSchoolByDataKey(String dataKey);
    boolean existsByDataKey(String dataKey);
}
