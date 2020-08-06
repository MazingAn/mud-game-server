package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ShopGoods;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface ShopGoodsRepository extends SpecificationRepository<ShopGoods, Long> {
    Iterable<ShopGoods> findShopGoodsByShop(String shopKey);
    ShopGoods findShopGoodsByDataKey(String dataKey);
}
