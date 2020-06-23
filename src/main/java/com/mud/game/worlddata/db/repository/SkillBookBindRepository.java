package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.Skill;
import com.mud.game.worlddata.db.models.SkillBookBind;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SkillBookBindRepository extends PagingAndSortingRepository<SkillBookBind, Long> {
    Iterable<SkillBookBind> findSkillBookBindsBySkillBook(String skillBookKey);
}
