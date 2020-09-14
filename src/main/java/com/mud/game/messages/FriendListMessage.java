package com.mud.game.messages;

import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.SimpleCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FriendListMessage {
    public Map<String, Set<SimpleCharacter>> friends;

    public FriendListMessage(PlayerCharacter playerCharacter) {
        this.friends = new HashMap<>();
        Set<SimpleCharacter> apply = new HashSet<>();
        Set<SimpleCharacter> passed = new HashSet<>();
        Boolean isMessage = false;
        for (String key : playerCharacter.getFriends().keySet()) {
            if (playerCharacter.getFriends().get(key) == null) {
                playerCharacter.getFriends().put(key, new SimpleCharacter(MongoMapper.playerCharacterRepository.findPlayerCharacterById(key)));
                isMessage = true;
            }
            passed.add(playerCharacter.getFriends().get(key));
        }
        if (isMessage) {
            MongoMapper.playerCharacterRepository.save(playerCharacter);
        }
        for (String key : playerCharacter.getFriendRequests().keySet()) {
            apply.add(playerCharacter.getFriendRequests().get(key));
        }
        friends.put("apply", apply);
        friends.put("passed", passed);
    }


    public Map<String, Set<SimpleCharacter>> getFriends() {
        return friends;
    }

    public void setFriends(Map<String, Set<SimpleCharacter>> friends) {
        this.friends = friends;
    }
}
