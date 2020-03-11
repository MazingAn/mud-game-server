package com.mud.game.object.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.handler.ConditionHandler;
import com.mud.game.handler.SkillTypeHandler;
import com.mud.game.messages.*;
import com.mud.game.net.session.CallerType;
import com.mud.game.net.session.GameSessionService;
import com.mud.game.object.account.Player;
import com.mud.game.object.algorithm.CommonAlgorithm;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.*;
import com.mud.game.server.ServerManager;
import com.mud.game.structs.*;
import com.mud.game.utils.jsonutils.Attr2Map;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.resultutils.GameWords;
import com.mud.game.utils.resultutils.UserOptionCode;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.CharacterModel;
import com.mud.game.worlddata.db.models.DefaultObjects;
import com.mud.game.worlddata.db.models.Skill;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.yeauty.pojo.Session;

import java.util.*;

public class PlayerCharacterManager {
    /*
    * @ 角色管理
    * @ 负责角色的创建，销毁，进入游戏，状态返回
    * */

    public static PlayerCharacter create(String name, String gender, int arm, int bone, int body, int smart, Session session)  {
        /*
         * @ 创建一个玩家角色
         * */
        if(MongoMapper.playerCharacterRepository.existsByName(name)){
            session.sendText(JsonResponse.JsonStringResponse(new AlertMessage(UserOptionCode.USERNAME_EXIST_ERROR)));
            return null;
        }else{
            PlayerCharacter playerCharacter = new PlayerCharacter();
            // 设置角色归属
            String playerId = GameSessionService.getCallerIdBySessionId(session.id());
            playerCharacter.setPlayer(playerId);
            // 根据注册的信息设置角色信息
            playerCharacter.setName(name);
            playerCharacter.setGender(gender);
            playerCharacter.setArm(arm);
            playerCharacter.setBody(body);
            playerCharacter.setBone(bone);
            playerCharacter.setSmart(smart);
            playerCharacter.setLooks(20);
            playerCharacter.setLucky(20);
            // 从玩家模版加载初始化信息
            String playerTemplateKey = ServerManager.gameSetting.getDefaultPlayerTemplate();
            CharacterModel playerTemplate = DbMapper.characterModelRepository.findCharacterModelByDataKey(playerTemplateKey);
            playerCharacter.setCustomerAttr(Attr2Map.characterAttrTrans(playerTemplate.getAttrs()));
            playerCharacter.setHp(playerTemplate.getHp());
            playerCharacter.setMax_hp(playerTemplate.getHp());
            playerCharacter.setLimit_hp(playerTemplate.getHp());
            playerCharacter.setMp(playerTemplate.getMp());
            playerCharacter.setMax_mp(playerTemplate.getMp());
            playerCharacter.setLimit_mp(playerTemplate.getMp());
            // 玩家信息的初始化设置
            playerCharacter.setAfter_arm(0);
            playerCharacter.setAfter_body(0);
            playerCharacter.setAfter_bone(0);
            playerCharacter.setAfter_looks(0);
            playerCharacter.setAfter_lucky(0);
            playerCharacter.setAfter_smart(0);
            playerCharacter.setTeacher("");
            playerCharacter.setTili(1000);
            playerCharacter.setSkills(new HashSet<>());
            playerCharacter.setEquipments(new ArrayList<>());
            playerCharacter.setEquippedEquipments(new HashMap<>());
            playerCharacter.setEquippedSkills(new HashMap<>());
            // 根据先天属性计算角色的初始属性
            CommonAlgorithm.resetInbornAttrs(playerCharacter);
            MongoMapper.playerCharacterRepository.save(playerCharacter);
            // 初始化玩家的背包和仓库
            initBagPack(playerCharacter);
            initWareHouse(playerCharacter);
            // 加载玩家默认物品
            loadDefaultObjects(playerCharacter);
            // 同时更新player信息
            Player player = MongoMapper.playerRepository.findPlayerById(playerId);
            Set<SimplePlayerCharacter> infos = player.getPlayerCharacters();
            infos.add(new SimplePlayerCharacter(playerCharacter));
            player.setPlayerCharacters(infos);
            MongoMapper.playerRepository.save(player);
            return playerCharacter;
        }
    }

    public static void puppet(String playerCharacterId, Session session)  {
        /*
        * 把一个玩家角色放入游戏世界
        * */
        try{
            PlayerCharacter playerCharacter = MongoMapper.playerCharacterRepository.findPlayerCharacterById(playerCharacterId);
            WorldRoomObject location = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(playerCharacter.getLocation());
            if(location == null){
                location = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(ServerManager.gameSetting.getStartLocation());
            }
            // 玩家上线之后 正式进入游戏，转换身份
            String newCallerId = playerCharacter.getId();
            String oldCallerId = GameSessionService.getCallerIdBySessionId(session.id());
            if(GameSessionService.callerId2SessionMap.containsKey(newCallerId)){
                // 如果使用了同一个账号重复登录，先踢掉线上的玩家
                PlayerCharacterManager.clearSession(newCallerId);
            }
            GameSessionService.updateCallerId(oldCallerId, newCallerId, CallerType.CHARACTER);

            // 获取地图，进入对应位置
            PlayerCharacterManager.pushMap(playerCharacter, location.getLocation(), session);
            PlayerCharacterManager.moveTo(playerCharacter, location.getDataKey(), session);
            // 玩家上线之后，还要通过房间通知上线锁在房间内的其他玩家，玩家上线
            Map<String, Object> onlineMessage = new HashMap<>();
            ObjectMoveInfo moveInfo = new ObjectMoveInfo("players", Arrays.asList(new SimplePlayerCharacter[]{new SimplePlayerCharacter(playerCharacter)}));
            onlineMessage.put("obj_moved_in", moveInfo.getInfo());
            onlineMessage.put("msg", String.format(GameWords.PLAYER_ONLINE, playerCharacter.getName()));
            WorldRoomObjectManager.broadcast(location, onlineMessage, playerCharacterId);
            // 发送玩家的属性信息
            PlayerCharacterManager.showStatus(playerCharacter, session);
            // 发送玩家的装备信息
            PlayerCharacterManager.returnEquippedEquipments(playerCharacter, session);
            // 发送玩家的技能信息
            PlayerCharacterManager.returnAllSkills(playerCharacter, session);
            // 发送玩家的背包信息
            PlayerCharacterManager.returnBagpack(playerCharacter, session);
            // 发送玩家当前的状态
            session.sendText(JsonResponse.JsonStringResponse(new PlayerCharacterStateMessage(playerCharacter.getState())));
        }catch (Exception e){
            e.printStackTrace();
            session.sendText(JsonResponse.JsonStringResponse(new AlertMessage("进入游戏失败，请稍后重试")));
        }
    }

    public static void clearSession(String playerCharacterId)  {
        /*
        * @ 玩家角色进入游戏的时候，要清空掉原来的session信息
        * */
        Session session = GameSessionService.getSessionByCallerId(playerCharacterId);
        if(session != null){
            session.sendText(JsonResponse.JsonStringResponse(new AlertMessage("你的帐号已在别处上线，你已被强制下线！")));
            session.close();
        }
        GameSessionService.removeSessionBySessionId(session.id());
    }

    public static void lookAround(PlayerCharacter playerCharacter, Session session) {
        /* ******************************************************************************
         * 进入某一个地方之后： 返回这个地方的所有信息
         *  @ 玩家查看周围的信息
         * @ 周围信息包含当前房间的基本信息 dbref, name, background, peaceful, desc, icon
         * @ 房间内可以看到的物体 things
         * @ 房间内可以看到的NPC npcs
         * @ 房间内可以用的出口  exits
         * @ 房间内可以执行的命令 commands
         * @ 房间内的其他玩家  players
         ******************************************************************************/
        WorldRoomObject location = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(playerCharacter.getLocation());
        // 查看周围信息之前，先看一眼，先解锁一下地图
        PlayerCharacterManager.revealMap(playerCharacter, location, session, false);
        // 拼接返回信息 数据太多，如果直接把mongodb的文档返回过去倒是很省事，但是流量很贵，我们要为老板考虑！:)
        // 所以只能这样自己根据前端需求 手动拼装JSON数据格式 这个工作一点都不好玩 :(
        Map<String, Object> location_info = new HashMap<String, Object>();
        //基本信息设置
        location_info.put("name", location.getName());
        location_info.put("dbref", location.getId());
        location_info.put("background", location.getBackground());
        location_info.put("peaceful", location.isPeaceful());
        location_info.put("desc", location.getDescription());
        location_info.put("icon", location.getIcon());
        // 可以看到的物体和物品生成器
        List<SimpleObject> objects = new ArrayList<SimpleObject>();
        // 可以看到的物体
        for(String objectKey : location.getThings()){
            WorldObjectObject object = MongoMapper.worldObjectObjectRepository.findWorldObjectObjectByDataKey(objectKey);
            if(GameWorldManager.isVisibleForPlayerCharacter(object, playerCharacter)) {
                objects.add(new SimpleObject(object));
            }
        }
        // 可以看到的物品生成器
        for(String objectKey : location.getCreators()){
            WorldObjectCreator creator = MongoMapper.worldObjectCreatorRepository.findWorldObjectCreatorByDataKey(objectKey);
            if(GameWorldManager.isVisibleForPlayerCharacter(creator, playerCharacter)) {
                objects.add(new SimpleObject(creator));
            }
        }
        location_info.put("things", objects);
        // 可以看到的出口
        List<SimpleObject> exits = new ArrayList<SimpleObject>();
        for (String exitKey : location.getExits()){
            WorldExitObject exit = MongoMapper.worldExitObjectRepository.findWorldExitObjectByDataKey(exitKey);
            if(GameWorldManager.isVisibleForPlayerCharacter(exit, playerCharacter)){
                exits.add(new SimpleObject(exit));
            }
        }
        location_info.put("exits",exits);
        // 可以看到的玩家
        List<SimplePlayerCharacter> playerCharacters = new ArrayList<SimplePlayerCharacter>();
        for (String playerId: location.getPlayers()){
            PlayerCharacter otherPlayerCharacter = MongoMapper.playerCharacterRepository.findPlayerCharacterById(playerId);
            if(PlayerCharacterManager.isVisibleForOtherPlayerCharacter(playerCharacter, otherPlayerCharacter)){
                playerCharacters.add(new SimplePlayerCharacter(otherPlayerCharacter));
            }
        }
        location_info.put("players", playerCharacters);
        // 可以看到的NPC
        List<SimplePlayerCharacter> npcs = new ArrayList<>();
        for(String npcDataKey: location.getNpcs()){
            WorldNpcObject npc = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectByDataKey(npcDataKey);
            if(GameWorldManager.isNpcVisibleForPlayerCharacter(npc, playerCharacter)){
                npcs.add(new SimplePlayerCharacter(npc));
            }
        }
        location_info.put("npcs", npcs);
        // 可以执行的命令
        location_info.put("cmds", WorldRoomObjectManager.getAvailableCommands(location, playerCharacter));
        session.sendText(JsonResponse.JsonStringResponse(new LookAroundMessage(location_info)));
        session.sendText(JsonResponse.JsonStringResponse(new CurrentLocationMessage(new RoomInfo(location))));
    }

    public static void gotoRoom(PlayerCharacter playerCharacter, String exitId, Session session) {
        /*
        * 玩家移动到一个房间，这个过程中不能单纯的把玩家放到这个房间
        * 而是要在程序内部通过出口，检查出口能否通过
        * 根据出口的情况，然后才更进行移动
        * */
        WorldExitObject exit = MongoMapper.worldExitObjectRepository.findWorldExitObjectById(exitId);
        // STEP1： 检查出口是不是锁定的，然后看玩家是不是已经解锁
        if(exit.isLocked() && !playerCharacter.unlockedExit.contains(exitId)){
            //没有解锁，根据解锁条件判断能否解锁
            if(WorldExitObjectManager.playerCharacterCanTranverse(playerCharacter, exit)){
                // 能解锁，显示解锁交互
                session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(exit.getUnlockDescription())));
                PlayerCharacterManager.moveTo(playerCharacter, exit.getDestination(), session);
            }else{
                // 不能解锁，显示提示信息
                session.sendText(JsonResponse.JsonStringResponse(new AlertMessage(exit.getLockDescription())));
            }
        }else{
            // 直接通过
            PlayerCharacterManager.moveTo(playerCharacter, exit.getDestination(), session);
        }
    }

    public static void moveTo(PlayerCharacter playerCharacter, String roomKey, Session session) {
        /*
        * @ 玩家移动到一个新的房间
        * @ 本质上就是更新玩家的位置
        * @ 额外的工作室负责两个房间要分别广播玩家进入和离开的消息，并更新房间玩家变动信息给客户端
        * */
        WorldRoomObject oldRoom = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(playerCharacter.getLocation());
        WorldRoomObject newRoom = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(roomKey);
        WorldRoomObjectManager.removeOfflinePlayer(oldRoom);
        WorldRoomObjectManager.removeOfflinePlayer(newRoom);
        //移动
        playerCharacter.setLocation(newRoom.getDataKey());
        PlayerCharacterManager.lookAround(playerCharacter, session);
        //变更房间内部的玩家信息
        if(oldRoom!=null){
            Set<String> playersInOldRoom = oldRoom.getPlayers();
            playersInOldRoom.remove(playerCharacter.getId());
            oldRoom.setPlayers(playersInOldRoom);
            Set<String> playersInNewRoom = newRoom.getPlayers();
            playersInNewRoom.add(playerCharacter.getId());
            newRoom.setPlayers(playersInNewRoom);
            MongoMapper.worldRoomObjectRepository.save(oldRoom);
            MongoMapper.worldRoomObjectRepository.save(newRoom);
            MongoMapper.playerCharacterRepository.save(playerCharacter);
            //房间广播玩家动向
            WorldRoomObjectManager.onPlayerCharacterMove(playerCharacter, oldRoom, newRoom);
        }
        //事件监测
        WorldRoomObjectManager.triggerArriveAction(newRoom, playerCharacter, session);
    }

    public static void revealMap(PlayerCharacter playerCharacter, WorldRoomObject location, Session session, boolean forceUpdateMap)  {
        /* ****************************************************************************
        * @ 解锁新的地图
        * 把当前的地图，放入已经解锁的地图Set里面
        * 根据当前地图包含的出口，拿到当前地图的邻居
        * 最终，给客户端返回当前区域已经解锁的所有地图
        *
        * 如果已经被解锁则跳过
        * 如果没有被解锁则要解锁新的地图并发送给客户端
        * revealedMap的是一个字典，key为区域的标识，内容为当前区域已经解锁地图的Set
        * @WARNING: 性能瓶颈！ 如果房间特别多，解锁房间和出口信息，会有一个n*1到n*4的循环
        * @优化之后： STEP2中的地图信息不用每一次都发送到客户端，而是在有解锁操作之后才更新
        * 每次玩家新进入游戏的时候客户端是没有玩家地图的，需要强制生成已经解锁的地图信息并推送
        ***************************************************************************/

        // STEP1: 先检查需不需要解锁，如果需要的话顺便初始化数据
        String areaKey = location.getLocation();
        Map<String, Set<String>> revealedMap = playerCharacter.getRevealedMap();
        //当前区域不存在，增加当前区域
        if(!revealedMap.containsKey(areaKey)){
            revealedMap.put(areaKey, new HashSet<>());
        }
        //当前区域存在，但是房间未解锁
        if(!revealedMap.get(areaKey).contains(location.getDataKey()) || forceUpdateMap) {
            Set<String> revealedRoomSet = revealedMap.get(areaKey);
            revealedRoomSet.add(location.getDataKey());
            revealedMap.put(areaKey, revealedRoomSet);
            playerCharacter.setRevealedMap(revealedMap);
            // STEP2：解锁完成之后，发送当前区域的可用地图和出口给玩家
            PlayerCharacterManager.pushMap(playerCharacter, areaKey, session);
            // 数据持久化
            MongoMapper.playerCharacterRepository.save(playerCharacter);
        }
    }

    public static void pushMap(PlayerCharacter playerCharacter,String areaKey, Session session)  {
        /*
        * @ 针对于一个区域，推送玩家在这个区域内解锁的所有地图和出口
        * */
        Map<String, Object> rooms = new HashMap<>();
        Map<String, ExitInfo> exits = new HashMap<>();
        if(!playerCharacter.getRevealedMap().containsKey(areaKey)){
            Map<String, Set<String>> revealedMap = playerCharacter.getRevealedMap();
            revealedMap.put(areaKey, new HashSet<>());
            playerCharacter.setRevealedMap(revealedMap);
        }
        for (String roomKey : playerCharacter.getRevealedMap().get(areaKey)) {
            // 增加房间到 房间集合
            WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(roomKey);
            RoomInfo roomInfo = new RoomInfo(room);
            rooms.put(roomKey, roomInfo);
            // 增加房间包含的出口到出口集合
            for (String exitKey : room.getExits()) {
                WorldExitObject exit = MongoMapper.worldExitObjectRepository.findWorldExitObjectByDataKey(exitKey);
                ExitInfo exitInfo = new ExitInfo(exit);
                exits.put(exit.getDataKey(), exitInfo);
                //拿到出口之后还要根据出口拿到房间的邻居,如果出口对于玩家是不可见的，则不用显示
                if (GameWorldManager.isVisibleForPlayerCharacter(exit, playerCharacter)) {
                    WorldRoomObject neighbor = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(exit.getDestination());
                    rooms.put(neighbor.getDataKey(), new CanArriveRoomInfo(neighbor, exit));
                }
            }
        }
        // 返回信息到客户端
        Map<String, Object> allRevealedMapInArea = new HashMap<>();
        allRevealedMapInArea.put("rooms", rooms);
        allRevealedMapInArea.put("exits", exits);
        session.sendText(JsonResponse.JsonStringResponse(new RevealedMapMessage(allRevealedMapInArea)));
    }

    public static void showStatus(PlayerCharacter playerCharacter, Session session)  {
        /*
        * @ 显示玩家的状态，包含玩家所有的属性
        * @ 玩的属性详情见客户点或：PlayerCharacterStatus
        * */
        session.sendText(JsonResponse.JsonStringResponse(new PlayerCharacterStatus(playerCharacter)));
    }

    public static void onPlayerLook(PlayerCharacter target, PlayerCharacter caller, Session session)  {
        /*
         * @ 当玩家查看游戏世界内的物体生成器的时候返回物体信息和可执行的命令（操作）
         * */
        Map<String, Object> lookMessage = new HashMap<>();
        PlayerCharacterAppearance appearance = new PlayerCharacterAppearance(target);
        // 设置玩家可以对此物体执行的命令
        appearance.setCmds(getAvailableCommands(caller, target));
        lookMessage.put("look_obj", appearance);
        session.sendText(JsonResponse.JsonStringResponse(lookMessage));
    }

    private static List<Map<String, Object>> getAvailableCommands(PlayerCharacter caller, PlayerCharacter target){
        /*
         * @ 获取玩家对当前对象的可操作命令
         * @ 对于玩家之间来说 包含的命令有 加好友，秘语，切磋，攻击命令
         * */
        List<Map<String, Object>> cmds = new ArrayList<>();
        // 添加好友命令
        if(!target.getFriends().containsKey(caller.getId())){
            Map<String, Object> cmd = new HashMap<>();
            cmd.put("cmd", "add_friend");
            cmd.put("name", "结交");
            cmd.put("args", target.getId());
            cmds.add(cmd);
        }
        return cmds;
    }

    public static boolean isVisibleForOtherPlayerCharacter(PlayerCharacter self, PlayerCharacter other){
        /*
        * 玩家是否可以被其他玩家看到
        * */
        // 首先自己不能看到自己，在这一块儿客户端自己有处理，后台不需要多此一举把玩家自己显示到房间中
        if(other != null){
            return !self.getId().equals(other.getId());
        }
        return true;
    }

    public static void findTeacher(PlayerCharacter playerCharacter, String targetId, Session session)  {
        /*
        * 玩家拜师
        * 首先玩家必须没有师傅
        * */
        WorldNpcObject teacher = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(targetId);
        if(playerCharacter.getTeacher().trim().equals("")){
            if(teacher != null && teacher.getLocation().equals(playerCharacter.getLocation())){
                // 师傅必须存在而且师傅必须和自己在一个房间
                playerCharacter.setTeacher(teacher.getDataKey());
                MongoMapper.playerCharacterRepository.save(playerCharacter);
                PlayerCharacterManager.showStatus(playerCharacter, session);
                session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(String.format(GameWords.PLAYER_FINDED_TEACHER, teacher.getName(), teacher.getName(), teacher.getName()))));
            }else{
                session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(String.format(GameWords.TEACHER_NOT_FOUND, teacher.getName()))));
            }
        }else{
            session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(String.format(GameWords.PLAYER_MUST_LEAVE_OLD_TEACHER, teacher.getName()))));
        }
    }

    public static void requestFriend(PlayerCharacter playerCharacter, String targetId, Session session)  {
        /*
        * @ 玩家通过另一个玩家的ID发出好友申请
        * */
        PlayerCharacter target =  MongoMapper.playerCharacterRepository.findPlayerCharacterById(targetId);
        Session targetSession = GameSessionService.getSessionByCallerId(targetId);
        if(target.getFriendRequests().containsKey(playerCharacter.getId())){
            // 已经发送过的请求
            session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(String.format(GameWords.PLAYER_REPEAT_REQUEST_FRIEND, target.getName()))));
        }else{
            // 对端添加好友申请信息
            Map<String, SimplePlayerCharacter> friendRequests = target.getFriendRequests();
            friendRequests.put(playerCharacter.getId(), new SimplePlayerCharacter(playerCharacter));
            target.setFriendRequests(friendRequests);
            MongoMapper.playerCharacterRepository.save(target);
            // 对端显示好友申请信息
            targetSession.sendText(JsonResponse.JsonStringResponse(new AddFriendRequestMessage(playerCharacter)));
            targetSession.sendText(JsonResponse.JsonStringResponse(new MsgMessage(String.format(GameWords.PLAYER_RECEIVE_FRIEND_REQUEST, playerCharacter.getName()))));
            targetSession.sendText(JsonResponse.JsonStringResponse(new FriendListMessage(target)));
            // 己端显示申请成功信息
            session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(String.format(GameWords.PLAYER_REQUEST_FRIEND, target.getName()))));
        }
    }

    public static void acceptFriendRequest(PlayerCharacter playerCharacter, String friendId, Session session)  {
        /*
        * @ 同意好友的请求
        * @ 把好友信息从申请列表移动到已通过列表
        * @ 给对方的好友列表里添加自己的信息，如果对方的申请列表里面也有自己的信息则删除
        * */
        PlayerCharacter friend = MongoMapper.playerCharacterRepository.findPlayerCharacterById(friendId);
        // 把好友请求从 requestFriends 移动到 friends 里面
        Map<String, SimplePlayerCharacter> friends =  playerCharacter.getFriends();
        Map<String, SimplePlayerCharacter> friendRequests = playerCharacter.getFriendRequests();
        SimplePlayerCharacter simpleFriendInfo = friendRequests.get(friendId);
        friends.put(friendId, simpleFriendInfo);
        friendRequests.remove(friendId);
        playerCharacter.setFriendRequests(friendRequests);
        playerCharacter.setFriends(friends);
        // 把对面好友的 requestFriends 和 friends 两个Map进行同步
        Map<String, SimplePlayerCharacter> targetsFriends = friend.getFriends();
        Map<String, SimplePlayerCharacter> targetsFriendRequests = friend.getFriendRequests();
        targetsFriendRequests.remove(playerCharacter.getId());
        targetsFriends.put(playerCharacter.getId(), new SimplePlayerCharacter(playerCharacter));
        friend.setFriends(targetsFriends);
        friend.setFriendRequests(targetsFriendRequests);
        // 持久化
        MongoMapper.playerCharacterRepository.save(playerCharacter);
        MongoMapper.playerCharacterRepository.save(friend);
        // 发送新的好友列表的给己方客户端
        session.sendText(JsonResponse.JsonStringResponse(new FriendListMessage(playerCharacter)));
        // 发送新的好友列表给对方客户端
        Session friendsSession = GameSessionService.getSessionByCallerId(friendId);
        friendsSession.sendText(JsonResponse.JsonStringResponse(new FriendListMessage(friend)));
        // 发送好友添加之后的消息给双方
        session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(String.format(GameWords.PLAYER_APPLY_FRIEND_REQUEST, friend.getName()))));
        friendsSession.sendText(JsonResponse.JsonStringResponse(new MsgMessage(String.format(GameWords.PLAYER_BE_APPLIED_FRIEND_REQUEST, playerCharacter.getName()))));
    }

    public static void sendMessageToOtherPlayer(PlayerCharacter playerCharacter, String targetId, String message, Session selfSession)  {
        /*
        * @发送消息给其他玩家
        * */
        PlayerCharacter target = MongoMapper.playerCharacterRepository.findPlayerCharacterById(targetId);
        Session targetSession = GameSessionService.getSessionByCallerId(targetId);
        if(targetSession != null){
            selfSession.sendText(JsonResponse.JsonStringResponse(new SayMessage(message, playerCharacter, target)));
            targetSession.sendText(JsonResponse.JsonStringResponse(new SayMessage(message, playerCharacter, target)));
        }else{
            selfSession.sendText(JsonResponse.JsonStringResponse(new MsgMessage("对方可能不在线")));
        }
    }

    public static Runnable learnSkillFromTeacher(PlayerCharacter playerCharacter, String skillKey, String teacherId, Session session)  {
        /*
        * @ 玩家从师傅那里学习技能
        * @ 这个技能本身是一个定时器
        * @ 第一步： 参数检查： 检查传入的teacherId是否是与玩家的数据一致
        * @ 第二步： 参数检查： 检查是否有没有这个技能，这个技能能不能教授给徒弟
        * @ 第三步： 条件判定： 检查玩家是否满足学习这个技能的条件;
        * @ 第四步： 潜能检查： 检查玩家是不是足够的潜能来学习技能
        * @ 上述四层检查全都通过，则返回一个runnable放在定时任务里面运行，并在runnable里面继续检查，如果不满足则跳出runnable
        * */
        WorldNpcObject teacher = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(teacherId);
        Skill skillTemplate = DbMapper.skillRepository.findSkillByDataKey(skillKey);
        if(!teacher.getDataKey().equals(playerCharacter.getTeacher())){
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.ERROR_TEACHER, teacher.getName()))));
            return null;
        }
        else if(!GameCharacterManager.characterHasSkill(teacher, skillKey)){
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.TEACHER_HAS_NO_SKILL, teacher.getName()))));
            return null;
        }
        else if(!ConditionHandler.matchCondition(skillTemplate.getLearnCondition(), playerCharacter)){
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.CAN_NOT_LEARN_SKILL, teacher.getName()))));
            return null;
        }
        else if(playerCharacter.getPotential() < 100){
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(GameWords.NO_ENOUGH_POTENTIAL)));
            return null;
        }else{
            playerCharacter.setState(CharacterState.STATE_LEARN_SKILL);
            MongoMapper.playerCharacterRepository.save(playerCharacter);
            session.sendText(JsonResponse.JsonStringResponse(new PlayerCharacterStateMessage(playerCharacter.getState())));
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Session updatedSession = GameSessionService.getSessionByCallerId(playerCharacter.getId());
                    PlayerCharacterManager.learnSkill(playerCharacter, skillKey, updatedSession, teacher);
                }
            };
            return runnable;
        }

    }

    public static Runnable learnSkillByObject(PlayerCharacter playerCharacter, String skillKey, String teacherId, Session session)  {
        /*
         * @ 玩家通过物品充值潜能学习技能
         * @ 这个技能本身是一个定时器
         * @ 第一步： 检查玩家是在当前NPC处的潜能是否足够
         * @ 第二步： 检查NP技能信息： 检查是否有没有这个技能，这个技能能不能教授给徒弟
         * @ 第三步： 条件判定： 检查玩家是否满足学习这个技能的条件;
         * @ 第四步： 潜能检查： 检查玩家是不是足够的潜能来学习技能（这个潜能包括玩家自身的潜能和充值的潜能）
         * @ 上述四层检查全都通过，则返回一个runnable放在定时任务里面运行，并在runnable里面继续检查，如果不满足则跳出runnable
         * */
        WorldNpcObject teacher = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(teacherId);
        Skill skillTemplate = DbMapper.skillRepository.findSkillByDataKey(skillKey);
        // 检查NPC学习余额是否足够
        if(!playerCharacter.getLearnByObjectRecord().containsKey(teacher.getName()) || (playerCharacter.getLearnByObjectRecord().get(teacher.getName()) <= 0)){
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.NO_ENOUGH_POTENTIAL_BALANCE, teacher.getName()))));
            return null;
        }
        // 检查NPC是否有这个技能
        else if(!GameCharacterManager.characterHasSkill(teacher, skillKey)){
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.TEACHER_HAS_NO_SKILL, teacher.getName()))));
            return null;
        }
        // 检查玩家能否学习这个技能
        else if(!ConditionHandler.matchCondition(skillTemplate.getLearnCondition(), playerCharacter)){
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.CAN_NOT_LEARN_SKILL, teacher.getName()))));
            return null;
        }
        // 检查玩家自身潜能是否还足够(如果是知识类技能的学习则不检查这一项)
        else if(playerCharacter.getPotential() <= 0 && !skillTemplate.getCategoryType().equals("SCT_ZHISHI")){
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(GameWords.NO_ENOUGH_POTENTIAL)));
            return null;
        }else{
            playerCharacter.setState(CharacterState.STATE_LEARN_SKILL);
            MongoMapper.playerCharacterRepository.save(playerCharacter);
            session.sendText(JsonResponse.JsonStringResponse(new PlayerCharacterStateMessage(playerCharacter.getState())));
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Session updatedSession = GameSessionService.getSessionByCallerId(playerCharacter.getId());
                    // 充值的潜能点是否用完检查，如果已经用完则停止学习
                    if((playerCharacter.getLearnByObjectRecord().get(teacher.getName()) <= 0)){
                        updatedSession.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.NO_ENOUGH_POTENTIAL_BALANCE, teacher.getName()))));
                        PlayerScheduleManager.shutdownExecutorByCallerId(playerCharacter.getId());
                    }else{
                        PlayerCharacterManager.learnSkill(playerCharacter, skillKey, updatedSession, teacher);
                    }
                }
            };
            return runnable;
        }
    }

    public static SkillObject learnSkill(PlayerCharacter playerCharacter, String skillKey, Session updatedSession, Object learnTarget)  {
        /*
        * @ 玩家学习技能的逻辑实现
        * @ 如果玩家已经有了这个技能，则不需要新建，进行升级操作
        * @ 如果玩家没有这个技能，则进行新建工作
        * @ 返回玩家学习的技能
        * */
        Skill template = DbMapper.skillRepository.findSkillByDataKey(skillKey);
        SkillObject skillObject = GameCharacterManager.getCharacterSkillByDataKey(playerCharacter, template.getDataKey());
        if(skillObject != null){
            // 给技能充能
            SkillObjectManager.chargeSkill(skillObject, playerCharacter, updatedSession, learnTarget);
        }else{
            // 没有学会技能 新建技能
            skillObject = SkillObjectManager.create(skillKey);
            skillObject.setOwner(playerCharacter.getId());
            skillObject.setLevel(1);
            SkillObjectManager.calculusEffects(playerCharacter, null, skillObject);
            MongoMapper.skillObjectRepository.save(skillObject);
            playerCharacter.getSkills().add(skillObject.getId());
            // 如果是知识类技能，默认装备并生效
            if(skillObject.getCategoryType().equals("SCT_ZHISHI")){
                skillObject.getEquippedPositions().add("zhishi");
                if(!playerCharacter.getEquippedSkills().containsKey("zhishi")){
                   playerCharacter.getEquippedSkills().put("zhishi", new HashSet<>());
                }
                playerCharacter.getEquippedSkills().get("zhishi").add(skillObject.getId());
                MongoMapper.skillObjectRepository.save(skillObject);
            }
            MongoMapper.playerCharacterRepository.save(playerCharacter);
            updatedSession.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.LEARNED_SKILL,  skillObject.getName()))));
        }

        returnAllSkills(playerCharacter, updatedSession);
        showStatus(playerCharacter, updatedSession);
        return skillObject;
    }

    public static void returnAllSkills(PlayerCharacter playerCharacter, Session session)  {
        /*
        * @ 获得玩家所有技能
        * */

        Map<String, Map<String, SimpleSkill>> skills = new HashMap<>();
        // 玩家技能分类
        for(String categoryTypeKey : SkillTypeHandler.categoryTypeMapping.keySet()){
            skills.put(categoryTypeKey, new HashMap<>());
        }
        // 挨个加入玩家技能
        for(String skillId : playerCharacter.getSkills()){
            SkillObject skillObject = MongoMapper.skillObjectRepository.findSkillObjectById(skillId);
            if(skillObject.isPassive()){
                SimpleSkill skillInfo = new SimpleSkill(skillObject);
                // 获得技能可以执行的命令
                skillInfo.setCmds(SkillObjectManager.getAvailableCommands(skillObject, playerCharacter));
                skills.get(skillObject.getCategoryType()).put(skillObject.getDataKey(), skillInfo);
            }
        }
        session.sendText(JsonResponse.JsonStringResponse(new PlayerCharacterSkills(skills)));
    }

    public static void getSkillsByPosition(PlayerCharacter playerCharacter, String position, Session session)  {
        /*
         * @ 获得玩家某一个位置的所有技能的key，分为已经装备的和没有装备的
         * @ 最终返回给客户端的数据包含两个字符串集合，used 和 can_replace
         * */

        Map<String, Set<String>> skillsInPosition = new HashMap<>();
        Set<String> usedSkills = new HashSet<>();
        Set<String> canReplacedSkills = new HashSet<>();
        for(String skillId : playerCharacter.getSkills()){
            SkillObject skillObject = MongoMapper.skillObjectRepository.findSkillObjectById(skillId);
            // 只取得当前位置可用的技能
            if(skillObject.isPassive() && skillObject.getPositions().contains(position)){
                if(skillObject.getEquippedPositions().contains(position)){
                    //该技能装备到了这个位置
                    usedSkills.add(skillObject.getDataKey());
                }else{
                    //该技能可以装备到这个位置，但是当前没有装备
                    canReplacedSkills.add(skillObject.getDataKey());
                }
            }
        }
        session.sendText(JsonResponse.JsonStringResponse(new PositionSkillsMessage(usedSkills, canReplacedSkills)));
    }

    public static void returnBagpack(PlayerCharacter playerCharacter, Session session)  {
        /*返回玩家的背包信息*/
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(playerCharacter.getBagpack());
        Collection<CommonObjectInfo> values = bagpackObject.getItems().values();
        session.sendText(JsonResponse.JsonStringResponse(new BagPackListMessage(new ArrayList<CommonObjectInfo>(values))));
    }

    public static void returnEquippedEquipments(PlayerCharacter playerCharacter, Session session)  {
        session.sendText(JsonResponse.JsonStringResponse(new EquipmentMessage(playerCharacter)));
    }

    // TODO：重构！ 物品创造好之后要调用放入背包操作
    public static boolean receiveObjectToBagpack(PlayerCharacter playerCharacter, CommonObject commonObject, int number, Session session)  {
        /*
        * 接受物品到背包
        * 把物品放入背包
        * */
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(playerCharacter.getBagpack());
        if(CommonItemContainerManager.addItem(bagpackObject, commonObject, number)){
            commonObject.setTotalNumber(commonObject.getTotalNumber() + number);
            MongoMapper.bagpackObjectRepository.save(bagpackObject);
            CommonObjectBuilder.save(commonObject);
            if(session!=null){
                session.sendText(JsonResponse.JsonStringResponse(new GettingObjectMessage(commonObject, number)));
            }
            return true;
        }else{
            if(session!=null){
                session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(String.format(GameWords.CAN_NOT_GET_OBJECT, commonObject.getName()))));
            }
            return false;
        }
    }

    public static void loadDefaultObjects(PlayerCharacter playerCharacter)  {
        /*
        * 初始化玩家的默认物品
        * */
        Iterable<DefaultObjects> defaultObjects = DbMapper.defaultObjectsRepository.findDefaultObjectsByTarget("player");
        for(DefaultObjects defaultObject : defaultObjects){
            int number = defaultObject.getNumber();
            String objectKey = defaultObject.getCommonObject();
            CommonObject commonObject = CommonObjectBuilder.buildCommonObject(objectKey);
            if(commonObject != null){
                commonObject.setOwner(playerCharacter.getId());
                // 持久化物品
                CommonObjectBuilder.save(commonObject);
                // 玩家背包接受物品
                receiveObjectToBagpack(playerCharacter, commonObject, number, null);
            }else{
                System.out.println("加载默认物品的时候，没有找到 : " + defaultObject.getCommonObject());
            }
        }
    }

    /**
     * 初始化玩家的背包
     * @param playerCharacter  玩家对象
     * */
    public static void initBagPack(PlayerCharacter playerCharacter){
        BagpackObject bagpackObject = BagpackObjectManager.create(playerCharacter.getId());
        MongoMapper.bagpackObjectRepository.save(bagpackObject);
        playerCharacter.setBagpack(bagpackObject.getId());
        MongoMapper.playerCharacterRepository.save(playerCharacter);
    }

    /**
     * 初始化玩家的仓库
     * @param playerCharacter 玩家对象
     * */
    public static void initWareHouse(PlayerCharacter playerCharacter){
        /*
        * 初始化玩家的背包
        * */
        WareHouseObject wareHouseObject = WareHouseObjectManager.create(playerCharacter.getId());
        MongoMapper.wareHouseObjectRepository.save(wareHouseObject);
        playerCharacter.setWareHouse(wareHouseObject.getId());
        MongoMapper.playerCharacterRepository.save(playerCharacter);
    }

    /**
     * 从背包移除物品
     *
     * @param playerCharacter 玩家对象
     * @param removedObject 要移除的物品对象
     * @param number 要移除的数量
     * @param session  消息传送通道
     *
     * @return boolean 最终是否成功移除
     * */
    public static boolean removeObjectsFromBagpack(PlayerCharacter playerCharacter, CommonObject removedObject, int number, Session session) {
        String bagpackId = playerCharacter.getBagpack();
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(bagpackId);
        if(CommonItemContainerManager.removeItem(bagpackObject, removedObject, number)) {
            MongoMapper.bagpackObjectRepository.save(bagpackObject);
            Collection<CommonObjectInfo> values = bagpackObject.getItems().values();
            session.sendText(JsonResponse.JsonStringResponse(new BagPackListMessage(new ArrayList<CommonObjectInfo>(values))));
            return true;
        }else {
            session.sendText(JsonResponse.JsonStringResponse(
                    new AlertMessage(String.format(GameWords.CAN_NOT_REMOVE_FROM_BAGPACK,
                    number, removedObject.getUnitName(), removedObject.getName()))));
            return false;
        }
    }

    /**
     * 往背包添加物品
     *
     * @param playerCharacter 玩家对象
     * @param removedObject 要添加的物品对象
     * @param number 要添加的数量
     * @param session  消息传送通道
     *
     * @return boolean 最终是否成功放入背包
     * */
    public static boolean addObjectsToBagpack(PlayerCharacter playerCharacter, CommonObject removedObject, int number, Session session) {
        String bagpackId = playerCharacter.getBagpack();
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(bagpackId);
        if(CommonItemContainerManager.addItem(bagpackObject, removedObject, number)) {
            MongoMapper.bagpackObjectRepository.save(bagpackObject);
            Collection<CommonObjectInfo> values = bagpackObject.getItems().values();
            session.sendText(JsonResponse.JsonStringResponse(new BagPackListMessage(new ArrayList<CommonObjectInfo>(values))));
            return true;
        }else {
            session.sendText(JsonResponse.JsonStringResponse(
                    new AlertMessage(String.format(GameWords.CAN_NOT_REMOVE_FROM_BAGPACK,
                            number, removedObject.getUnitName(), removedObject.getName()))));
            return false;
        }
    }

}
