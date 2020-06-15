package com.mud.game.messages;

import com.mud.game.object.typeclass.GameChatChannel;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.HashMap;
import java.util.Map;

public class PlayerCharacterChannelMessage {
    private Map<String, String> channels;

    public PlayerCharacterChannelMessage(PlayerCharacter playerCharacter) {
        this.channels = new HashMap<>();
        Iterable<GameChatChannel> gcChannels = MongoMapper.gameChatChannelRepository.findAll();
        for(GameChatChannel channel : gcChannels){
            if(channel.getPermission().equals("all")){
                channels.put(channel.getChanelKey(), channel.getName());
            }
            if(channel.getChanelKey().replaceAll("_channel","").equals(playerCharacter.getSchool())){
                channels.put(channel.getChanelKey(), channel.getName());
            }
        }
    }

    public Map<String, String> getChannels() {
        return channels;
    }

    public void setChannels(Map<String, String> channels) {
        this.channels = channels;
    }
}
