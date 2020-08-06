package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Gem;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface GemRepository extends SpecificationRepository<Gem, Long> {
    Gem findGemByDataKey(String dataKey);
    boolean existsByDataKey(String dataKey);
}
