package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.NpcLearnObjectList;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface NpcLearnObjectListRepository extends SpecificationRepository<NpcLearnObjectList, Long> {
    NpcLearnObjectList getByNpcKey(String npcKey);
}
