package com.mud.game.messages;

import com.mud.game.object.typeclass.PlayerCharacter;

import java.util.HashMap;
import java.util.Map;

/**
 *  返回信息类： 玩家同意添加好友返回信息
 *  <pre>
 *  返回示例：
 *  {
 *      "friend_apply": {
 *          "dbref" : "好友的id",
 *          "name" : "好友的名称"
 *      }
 *  }
 *  </pre>
 *
 * */
public class AddFriendRequestMessage {
    public Map<String, Object> friend_apply;

    public AddFriendRequestMessage(PlayerCharacter playerCharacter) {
        friend_apply = new HashMap<>();
        friend_apply.put("dbref", playerCharacter.getId());
        friend_apply.put("name", playerCharacter.getName());
    }

    public Map<String, Object> getFriend_apply() {
        return friend_apply;
    }

    public void setFriend_apply(Map<String, Object> friend_apply) {
        this.friend_apply = friend_apply;
    }
}
