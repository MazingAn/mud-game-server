package com.mud.game.object.manager;

import com.mud.game.object.typeclass.GameChatChannel;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import com.mud.game.worldrun.db.repository.PlayerCharacterRepository;

public class GameChannelManager {
    public static GameChatChannel create(String channelKey, String name, String permission){
        GameChatChannel channel;
        if(!MongoMapper.gameChatChannelRepository.existsByChanelKey(channelKey)){
            channel = new GameChatChannel();
            channel.setChanelKey(channelKey);
            channel.setName(name);
            channel.setPermission(permission);
            MongoMapper.gameChatChannelRepository.save(channel);
        }else{
            channel = MongoMapper.gameChatChannelRepository.findGameChatChannelByChanelKey(channelKey);
        }
        return channel;
    }

    public static void msgContents(PlayerCharacter sender, String channelKey, String message){
        GameChatChannel channel = MongoMapper.gameChatChannelRepository.findGameChatChannelByChanelKey(channelKey);
        if(channel != null){
            String permission = channel.getPermission();
            Iterable<PlayerCharacter> receivers;
            if(permission.equals("all")){
                receivers = MongoMapper.playerCharacterRepository.findAll();
            }else{
                receivers = MongoMapper.playerCharacterRepository.findPlayerCharactersBySchool(permission);
            }
            if(receivers != null){
                for(PlayerCharacter receiver : receivers){
                    message = "【" + channel.getName() +"】" + sender.getName() + ":" + message;
                    receiver.msg(message);
                }
            }else{
                sender.msg("{你还没有在此频道发言的权限{n");
            }
        }else{
            sender.msg("{r不存在的频道{n");
        }
    }
}
