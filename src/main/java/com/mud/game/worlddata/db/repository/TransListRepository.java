package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.TransList;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransListRepository extends PagingAndSortingRepository<TransList, Long> {
    Iterable<TransList> findTransListByNpc(String npcKey);
    TransList findTransListByNpcAndRoom(String npcKey, String roomKey);
}
