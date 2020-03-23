package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.GameSetting;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GameSettingRepository extends PagingAndSortingRepository<GameSetting, Long> {
    GameSetting findGameSettingById(Long id);
    GameSetting deleteGameSettingById(Long id);
}
