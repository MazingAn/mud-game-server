package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionChangeStep;
import com.mud.game.worlddata.db.models.ActionLearnSkill;
import org.springframework.data.repository.CrudRepository;

public interface ActionChangeStepRepository extends CrudRepository<ActionChangeStep, Long> {
    ActionChangeStep findActionChangeStepById(Long id);
    ActionChangeStep findActionChangeStepsByEventKey(String dataKey);
}
