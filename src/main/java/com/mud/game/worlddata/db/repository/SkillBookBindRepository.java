package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Skill;
import com.mud.game.worlddata.db.models.SkillBookBind;
import org.springframework.data.repository.CrudRepository;

public interface SkillBookBindRepository extends CrudRepository<SkillBookBind, Long> {
    Iterable<SkillBookBind> findSkillBookBindsBySkillBook(String skillBookKey);
}
