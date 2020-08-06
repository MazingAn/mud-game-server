package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionAcceptQuest;
import com.mud.game.worlddata.db.models.DefaultEquipments;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DefaultEquipmentsRepository extends SpecificationRepository<DefaultEquipments, Long> {
    Page<ActionAcceptQuest> findByNameLike(String specName, Pageable pageable);
}
