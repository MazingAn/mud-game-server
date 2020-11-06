package com.mud.game.worldrun.db.repository;

import com.mud.game.object.account.WeiXinPlayer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeiXinPlayerRepository extends MongoRepository<WeiXinPlayer, String> {
    boolean existsByUnionId(String unionID);

    WeiXinPlayer findWeiXinPlayerByUnionId(String unionID);

    WeiXinPlayer findWeiXinPlayerById(String playerId);
}
