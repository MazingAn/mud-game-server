package com.mud.game.worldrun.db.repository;

import com.mud.game.object.manager.GameChannelManager;
import com.mud.game.object.typeclass.GameChatChannel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameChatChannelRepository extends MongoRepository<GameChatChannel, String> {
    GameChatChannel findGameChatChannelByChanelKey(String channelKey);
    boolean existsByChanelKey(String channelKey);
}
