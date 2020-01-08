package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.GameSetting;
import org.springframework.data.repository.CrudRepository;

public interface GameSettingRepository extends CrudRepository<GameSetting, Long> {
}
