package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.WorldNpc;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface WorldNpcRepository extends SpecificationRepository<WorldNpc, Long> {
    WorldNpc findWorldNpcByDataKey(String dataKey);

    WorldNpc deleteWorldNpcById(Long id);

    Iterable<WorldNpc> findWorldNpcsByLocation(String location);

    Iterable<WorldNpc> findWorldNpcsByTransfer(boolean isTransfer);
}
