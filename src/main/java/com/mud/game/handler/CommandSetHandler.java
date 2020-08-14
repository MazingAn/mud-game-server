package com.mud.game.handler;

import com.mud.game.commands.account.CharCreate;
import com.mud.game.commands.account.Puppet;
import com.mud.game.commands.character.*;
import com.mud.game.commands.common.Idle;
import com.mud.game.commands.unlogin.Connect;
import com.mud.game.commands.unlogin.Create;

import java.util.HashMap;
import java.util.Map;

/**
 * 命令集Handler类
 *
 * <pre>
 * 通道中的所有命令都有一个对应的caller
 * 在没有登陆的状态下：caller默认为UnLoginCaller
 * 在登陆状态下：caller为Account
 * 在进入游戏的状态下： caller为PlayerCharacter
 * 对与NPC/Monster和Boss来讲：caller永远为NPC/Monster/Boss
 * </pre>
 */
public class CommandSetHandler {

    /**
     * 未登录状态下的可操作命令字典  key为字符串类型  value为对应的处理类的路径
     */
    public static Map<String, Class> unLoginCommandSet = new HashMap<>();
    /**
     * 登陆后账户的命令集合 key为字符串类型  value为对应的处理类的路径
     */
    public static Map<String, Class> accountCommandSet = new HashMap<>();
    /**
     * 玩家的命令集合  key为字符串类型  value为对应的处理类的路径
     */
    public static Map<String, Class> playerCharacterCommandSet = new HashMap<>();
    /**
     * 玩家的死亡之后可执行命令集合  key为字符串类型  value为对应的处理类的路径
     */
    public static Map<String, Class> playerCharacterDieCommandSet = new HashMap<>();


    /**
     * 初始化 未登录玩家的命令集
     */
    public static void initUnLoginCommandSet() {
        unLoginCommandSet.put("idle", Idle.class);
        unLoginCommandSet.put("create", Create.class);
        unLoginCommandSet.put("connect", Connect.class);
    }

    /**
     * 初始化 游戏账户身份状态下（登录游戏但未进入游戏世界）的命令集
     */
    public static void initAccountCommandSet() {
        accountCommandSet.put("idle", Idle.class);
        accountCommandSet.put("char_create", CharCreate.class);
        accountCommandSet.put("puppet", Puppet.class);
    }

    /**
     * 初始化 游戏内部玩家的命令集
     */
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
        playerCharacterCommandSet.put("equip_equipment", EquipEquipment.class);
        playerCharacterCommandSet.put("take_off_equipment", TakeOffEquipment.class);
        playerCharacterCommandSet.put("attack", Attack.class);
        playerCharacterCommandSet.put("castskill", CastSkill.class);
        playerCharacterCommandSet.put("talk", Talk.class);
        playerCharacterCommandSet.put("dialogue", CmdDialogue.class);
        playerCharacterCommandSet.put("quests", CmdQuests.class);
        playerCharacterCommandSet.put("say", CmdSay.class);
        playerCharacterCommandSet.put("shopping", CmdShopping.class);
        playerCharacterCommandSet.put("sys_shopping", CmdSysShopping.class);
        playerCharacterCommandSet.put("buy", CmdBuy.class);
        playerCharacterCommandSet.put("trans", CmdTrans.class);
        playerCharacterCommandSet.put("meditate", HangUpMeditate.class);
        playerCharacterCommandSet.put("use", CmdUse.class);
        playerCharacterCommandSet.put("add_auction", SellObjects.class);
        playerCharacterCommandSet.put("buyout", SellBuyObject.class);
        playerCharacterCommandSet.put("sellCancel", SellCancel.class);
        playerCharacterCommandSet.put("auction", Auction.class);
        playerCharacterCommandSet.put("sellPut", SellPut.class);
        playerCharacterCommandSet.put("compositeList", CompositeList.class);
        playerCharacterCommandSet.put("composite", Composite.class);
        playerCharacterCommandSet.put("check_strengthen", CheckStrengthen.class);
        playerCharacterCommandSet.put("strength", Strengthen.class);
    }

    /**
     * 初始化 游戏内部玩家的命令集 玩家死亡的时候可以执行的命令
     */
    public static void initPlayerCharacterDieCommandSet() {
        playerCharacterDieCommandSet.put("idle", Idle.class);
        playerCharacterDieCommandSet.put("friends", LoadFriends.class);
        playerCharacterDieCommandSet.put("add_friend", RequestFriend.class);
        playerCharacterDieCommandSet.put("accept_friend", AcceptFriend.class);
        playerCharacterDieCommandSet.put("friend_chat", FriendChat.class);
        playerCharacterDieCommandSet.put("find_teacher", FindTeacher.class);
        playerCharacterDieCommandSet.put("reborn_here", RebornHere.class);
        playerCharacterDieCommandSet.put("reborn_home", RebornHome.class);
        playerCharacterCommandSet.put("quests", CmdQuests.class);
        playerCharacterCommandSet.put("say", CmdSay.class);
    }

}
