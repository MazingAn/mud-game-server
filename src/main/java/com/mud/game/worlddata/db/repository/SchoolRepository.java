package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.School;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface SchoolRepository extends SpecificationRepository<School, Long> {
    School findSchoolByDataKey(String dataKey);
    boolean existsByDataKey(String dataKey);
}
