package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.NpcDangPu;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;

public interface NpcDangPuRepository extends SpecificationRepository<NpcDangPu, Long> {
    NpcDangPu findNpcDangPuByNpc(String dataKey);
}
