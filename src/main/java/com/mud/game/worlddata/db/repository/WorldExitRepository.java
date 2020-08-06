package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.WorldExit;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface WorldExitRepository extends SpecificationRepository<WorldExit, Long> {
    WorldExit deleteWorldExitById(Long id);
}
