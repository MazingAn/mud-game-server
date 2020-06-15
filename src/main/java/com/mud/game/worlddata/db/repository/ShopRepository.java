package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Shop;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShopRepository extends PagingAndSortingRepository<Shop, Long> {
    Shop findShopByDataKey(String dataKey);
    Shop findShopBySystemShop(boolean sysShop);
}
