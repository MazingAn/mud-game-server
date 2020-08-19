package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Gem;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

import java.util.List;

public interface GemRepository extends SpecificationRepository<Gem, Long> {
    Gem findGemByDataKey(String dataKey);
    boolean existsByDataKey(String dataKey);
    List<Gem> findGemByPositions(String positionStr);

    List<Gem> findGemByPositionsLike(String positionStr);
}
