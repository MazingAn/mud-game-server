package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.NormalObject;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NormalObjectRepository extends SpecificationRepository<NormalObject, Long> {
    NormalObject findNormalObjectByDataKey(String dataKey);

    Iterable<NormalObject> findNormalObjectsByCategory(String category);

    boolean existsByDataKey(String dataKey);
}
