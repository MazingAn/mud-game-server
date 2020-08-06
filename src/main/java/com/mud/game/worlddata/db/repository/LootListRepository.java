package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.LootList;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface LootListRepository extends SpecificationRepository<LootList, Long> {
    Iterable<LootList> getLootListsByProvider(String provider);
}
