package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionChangeStep;
import com.mud.game.worlddata.db.models.ActionLearnSkill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActionChangeStepRepository extends PagingAndSortingRepository<ActionChangeStep, Long> {
    ActionChangeStep findActionChangeStepById(Long id);
    ActionChangeStep findActionChangeStepsByEventKey(String dataKey);
}
