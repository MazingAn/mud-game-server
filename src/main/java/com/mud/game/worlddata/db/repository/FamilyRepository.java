package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Family;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface FamilyRepository extends SpecificationRepository<Family, Long> {
    Family findFamilyByDataKey(String dataKey);
}
