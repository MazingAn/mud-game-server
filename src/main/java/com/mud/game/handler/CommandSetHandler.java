package com.mud.game.handler;

import com.mud.game.commands.account.CharCreate;
import com.mud.game.commands.account.Puppet;
import com.mud.game.commands.character.*;
import com.mud.game.commands.common.Idle;
import com.mud.game.commands.unlogin.Connect;
import com.mud.game.commands.unlogin.Create;

import java.util.HashMap;
import java.util.Map;

/*
* 通道中的所有命令都有一个对应的caller
* 在没有登陆的状态下：caller默认为UnLoginCaller
* 在登陆状态下：caller为Account
* 在进入游戏的状态下： caller为PlayerCharacter
* 对与NPC/Monster和Boss来讲：caller永远为NPC/Monster和Boss
* */
public class CommandSetHandler {

    //未登录状态下的命令集合
    public static Map<String, Class> unLoginCommandSet = new HashMap<>();
    //登陆后账户的命令集合
    public static Map<String, Class> accountCommandSet = new HashMap<>();
    //玩家的命令集合
    public static Map<String, Class> playerCharacterCommandSet = new HashMap<>();

    public static void initUnLoginCommandSet(){
        unLoginCommandSet.put("idle", Idle.class);
        unLoginCommandSet.put("create", Create.class);
        unLoginCommandSet.put("connect", Connect.class);
    }

    public static void initAccountCommandSet(){
        accountCommandSet.put("idle", Idle.class);
        accountCommandSet.put("char_create", CharCreate.class);
        accountCommandSet.put("puppet", Puppet.class);
    }

    public static void initPlayerCharacterCommandSet() {
        playerCharacterCommandSet.put("idle", Idle.class);
        playerCharacterCommandSet.put("goto", GotoRoom.class);
        playerCharacterCommandSet.put("look", Look.class);
        playerCharacterCommandSet.put("chose_action", ChoseAction.class);
        playerCharacterCommandSet.put("loot", Loot.class);
        playerCharacterCommandSet.put("friends", LoadFriends.class);
        playerCharacterCommandSet.put("add_friend", RequestFriend.class);
        playerCharacterCommandSet.put("accept_friend", AcceptFriend.class);
        playerCharacterCommandSet.put("friend_chat", FriendChat.class);
        playerCharacterCommandSet.put("find_teacher", FindTeacher.class);
        playerCharacterCommandSet.put("learn_from_teacher", LearnFromTeacher.class);
        playerCharacterCommandSet.put("learn_skill_from_teacher", LearnSkillFromTeacher.class);
        playerCharacterCommandSet.put("mining", HangUpMining.class);
        playerCharacterCommandSet.put("fishing", HangUpFishing.class);
        playerCharacterCommandSet.put("collect", HangUpCollect.class);
        playerCharacterCommandSet.put("cure", HangUpCure.class);
        playerCharacterCommandSet.put("stop", HangUpStop.class);
        playerCharacterCommandSet.put("learn_by_object", LearnSkillByObjectCheck.class);
        playerCharacterCommandSet.put("give_learn_object", LearnSkillByObjectCharge.class);
        playerCharacterCommandSet.put("learn_skill_by_object", LearnSkillByObject.class);
        playerCharacterCommandSet.put("goto_room", DriveTo.class);
        playerCharacterCommandSet.put("skill_by_position", GetSkillsByPosition.class);
        playerCharacterCommandSet.put("equip_skill", EquipSkill.class);
        playerCharacterCommandSet.put("take_off_skill", TakeOffSkill.class);
    }

}
