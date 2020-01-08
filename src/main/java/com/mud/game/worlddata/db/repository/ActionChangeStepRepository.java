package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ActionChangeStep;
import com.mud.game.worlddata.db.models.ActionLearnSkill;
import org.springframework.data.repository.CrudRepository;

public interface ActionChangeStepRepository extends CrudRepository<ActionChangeStep, Long> {
    public ActionChangeStep findActionChangeStepById(Long id);
    public ActionChangeStep findActionChangeStepsByEventKey(String dataKey);
}
