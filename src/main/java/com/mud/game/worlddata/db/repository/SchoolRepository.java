package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.School;
import org.springframework.data.repository.CrudRepository;

public interface SchoolRepository extends CrudRepository<School, Long> {
    School findSchoolByDataKey(String dataKey);
}
