package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.SkillBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SkillBookRepository extends PagingAndSortingRepository<SkillBook, Long> {
    SkillBook findSkillBookByDataKey(String dataKey);
    boolean existsByDataKey(String dataKey);
}
