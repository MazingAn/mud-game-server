package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.NpcShop;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NpcShopRepository extends PagingAndSortingRepository<NpcShop, Long> {
    Iterable<NpcShop> findNpcShopsByNpc(String npcKey);
}
