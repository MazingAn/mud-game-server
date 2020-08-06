package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.SkillBook;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface SkillBookRepository extends SpecificationRepository<SkillBook, Long> {
    SkillBook findSkillBookByDataKey(String dataKey);
    boolean existsByDataKey(String dataKey);
}
