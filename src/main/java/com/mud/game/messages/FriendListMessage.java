package com.mud.game.messages;

import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.SimpleCharacter;

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
        for(String key:playerCharacter.getFriends().keySet()){
            passed.add(playerCharacter.getFriends().get(key));
        }
        for(String key:playerCharacter.getFriendRequests().keySet()){
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
