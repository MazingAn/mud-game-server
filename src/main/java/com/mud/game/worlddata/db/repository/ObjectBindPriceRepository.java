package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ObjectBindPrice;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface ObjectBindPriceRepository extends SpecificationRepository<ObjectBindPrice, Long> {
    ObjectBindPrice findObjectBindPriceByDataKey(String dataKey);
}
