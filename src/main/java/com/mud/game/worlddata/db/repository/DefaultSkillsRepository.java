package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.DefaultSkills;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface DefaultSkillsRepository extends SpecificationRepository<DefaultSkills, Long> {
    Iterable<DefaultSkills> findDefaultSkillsByTarget(String targetKey);

    Iterable<DefaultSkills> findDefaultSkillsByTargetAndEquipped(String dataKey, boolean b);

    Iterable<DefaultSkills> findDefaultSkillsByTargetAndEquippedAndSkillKeyLike(String dataKey, boolean b, String sct_juezhao);
}
