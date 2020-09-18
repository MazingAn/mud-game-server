package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.GameSetting;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

import java.util.List;

public interface GameSettingRepository extends SpecificationRepository<GameSetting, Long> {
    GameSetting findGameSettingById(Long id);
    GameSetting deleteGameSettingById(Long id);


    GameSetting findFirstByOrderByIdDesc();
}
