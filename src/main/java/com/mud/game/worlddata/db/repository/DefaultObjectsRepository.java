package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.DefaultObjects;
import com.mud.game.worlddata.db.models.DefaultSkills;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DefaultObjectsRepository extends SpecificationRepository<DefaultObjects, Long> {
    Iterable<DefaultObjects> findDefaultObjectsByTarget(String targetKey);
}
