package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ShopGoods;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShopGoodsRepository extends PagingAndSortingRepository<ShopGoods, Long> {
    Iterable<ShopGoods> findShopGoodsByShop(String shopKey);
    ShopGoods findShopGoodsByDataKey(String dataKey);
}
