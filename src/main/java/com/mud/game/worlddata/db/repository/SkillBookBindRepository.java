package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.SkillBookBind;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface SkillBookBindRepository extends SpecificationRepository<SkillBookBind, Long> {
    Iterable<SkillBookBind> findSkillBookBindsBySkillBook(String skillBookKey);
}
