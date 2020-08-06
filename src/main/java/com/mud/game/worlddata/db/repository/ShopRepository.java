package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Shop;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface ShopRepository extends SpecificationRepository<Shop, Long> {
    Shop findShopByDataKey(String dataKey);
    Shop findShopBySystemShop(boolean sysShop);
}
