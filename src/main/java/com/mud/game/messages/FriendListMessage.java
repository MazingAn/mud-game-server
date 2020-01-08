package com.mud.game.messages;

import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.SimplePlayerCharacter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FriendListMessage {
    public Map<String, Set<SimplePlayerCharacter>> friends;

    public FriendListMessage(PlayerCharacter playerCharacter) {
        this.friends = new HashMap<>();
        Set<SimplePlayerCharacter> apply = new HashSet<>();
        Set<SimplePlayerCharacter> passed = new HashSet<>();
        for(String key:playerCharacter.getFriends().keySet()){
            passed.add(playerCharacter.getFriends().get(key));
        }
        for(String key:playerCharacter.getFriendRequests().keySet()){
            apply.add(playerCharacter.getFriendRequests().get(key));
        }
        friends.put("apply", apply);
        friends.put("passed", passed);
    }


    public Map<String, Set<SimplePlayerCharacter>> getFriends() {
        return friends;
    }

    public void setFriends(Map<String, Set<SimplePlayerCharacter>> friends) {
        this.friends = friends;
    }
}
