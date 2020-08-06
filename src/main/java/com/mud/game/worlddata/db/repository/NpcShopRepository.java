package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.NpcShop;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface NpcShopRepository extends SpecificationRepository<NpcShop, Long> {
    Iterable<NpcShop> findNpcShopsByNpc(String npcKey);
}
