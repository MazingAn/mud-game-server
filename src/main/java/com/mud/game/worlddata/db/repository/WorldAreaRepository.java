package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.WorldArea;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface WorldAreaRepository extends SpecificationRepository<WorldArea, Long> {
    WorldArea deleteWorldAreaById(Long id);
    WorldArea findWorldAreaByDataKey(String dataKey);
}
