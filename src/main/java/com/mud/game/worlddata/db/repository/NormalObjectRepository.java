package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.NormalObject;
import org.springframework.data.repository.CrudRepository;

public interface NormalObjectRepository extends CrudRepository<NormalObject, Long> {
    NormalObject findNormalObjectByDataKey(String dataKey);
}
