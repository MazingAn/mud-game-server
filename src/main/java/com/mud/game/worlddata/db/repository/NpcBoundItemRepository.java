package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.NpcBoundItem;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

import java.util.List;

public interface NpcBoundItemRepository extends SpecificationRepository<NpcBoundItem, Long> {
    List<NpcBoundItem> findNpcBoundItemByNpcDataKey(String npcDataKey);
}
