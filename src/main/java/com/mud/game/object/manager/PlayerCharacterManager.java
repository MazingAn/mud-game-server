package com.mud.game.object.manager;

import com.mud.game.algorithm.CommonAlgorithm;
import com.mud.game.combat.CombatSense;
import com.mud.game.combat.NormalCombat;
import com.mud.game.handler.CombatHandler;
import com.mud.game.handler.ConditionHandler;
import com.mud.game.handler.ObjectFunctionHandler;
import com.mud.game.handler.SkillTypeHandler;
import com.mud.game.messages.*;
import com.mud.game.net.session.CallerType;
import com.mud.game.net.session.GameSessionService;
import com.mud.game.object.account.Player;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.*;
import com.mud.game.server.ServerManager;
import com.mud.game.structs.*;
import com.mud.game.utils.collections.ListUtils;
import com.mud.game.utils.jsonutils.Attr2Map;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.resultutils.GameWords;
import com.mud.game.utils.resultutils.UserOptionCode;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.*;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.mud.game.constant.PostConstructConstant.CRIME_VALUE_ATTACK;
import static com.mud.game.utils.resultutils.GameWords.ENEMY_ONLINE_REMINDER;
import static com.mud.game.utils.resultutils.GameWords.FRIEND_ONLINE_REMINDER;

public class PlayerCharacterManager {

    /**
     * 角色管理
     * 负责角色的创建，销毁，进入游戏，状态返回
     */
    public static PlayerCharacter create(String name, String gender, int arm, int bone, int body, int smart, Session session) {
        /*
         * @ 创建一个玩家角色
         * */
        if (MongoMapper.playerCharacterRepository.existsByName(name)) {
            session.sendText(JsonResponse.JsonStringResponse(new AlertMessage(UserOptionCode.USERNAME_EXIST_ERROR)));
            return null;
        } else {
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
            //初始化房间
            GameSetting gameSetting = DbMapper.gameSettingRepository.findFirstByOrderByIdDesc();
            playerCharacter.setLocation(gameSetting.getStartLocation());
            MongoMapper.playerCharacterRepository.save(playerCharacter);
            //  配置随机天赋
            List<Family> familyList = DbMapper.familyRepository.findAll();
            if (familyList != null && familyList.size() > 0) {
                Family family = ListUtils.randomChoice(familyList);
                playerCharacter.setFamily(family.getDataKey());
                //天赋加成
                String[] functionSplited = family.getEffect().split("\\(");
                String key = functionSplited[0].replaceAll("\"", "").replaceAll("\\'", "");
                String[] args = functionSplited[1].replaceAll("\\)", "").replaceAll("\\'", "").replaceAll("\"", "").split(",");
                //函数
                Class clazz = ObjectFunctionHandler.objectFunctionMap.get(key);
                try {
                    Constructor c = clazz.getConstructor(CommonCharacter.class, CommonCharacter.class, NormalObjectObject.class, String.class, String[].class);
                    c.newInstance(playerCharacter, playerCharacter, null, key, args);
                } catch (Exception e) {
                    System.out.println(String.format("玩家在执行天赋函数%s的时候触发了异常", key));
                    e.printStackTrace();
                }
            }
            // 初始化玩家的背包和仓库
            initBagPack(playerCharacter);
            initWareHouse(playerCharacter);
            // 加载玩家默认物品
            loadDefaultObjects(playerCharacter);
            // 同时更新player信息
            Player player = MongoMapper.playerRepository.findPlayerById(playerId);
            Set<SimpleCharacter> infos = player.getPlayerCharacters();
            infos.add(new SimpleCharacter(playerCharacter));
            player.setPlayerCharacters(infos);
            MongoMapper.playerRepository.save(player);
            return playerCharacter;
        }
    }

    /**
     * 角色进入游戏后的操作
     * 角色信息，地图信息，角色位置信息和状态重置 都在这里处理
     *
     * @param playerCharacterId String 玩家角色的ID
     * @param session           Session 玩家角色的session
     */
    public static void puppet(String playerCharacterId, Session session) {
        /*
         * 把一个玩家角色放入游戏世界
         * */
        try {

            // 查找玩家信息和玩家位置信息
            PlayerCharacter playerCharacter = MongoMapper.playerCharacterRepository.findPlayerCharacterById(playerCharacterId);
            WorldRoomObject location = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(playerCharacter.getLocation());

            // 重置玩家位置
            if (location == null) {
                location = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(ServerManager.gameSetting.getStartLocation());
            }

            // 玩家上线之后 正式进入游戏，转换身份
            String newCallerId = playerCharacter.getId();
            String oldCallerId = GameSessionService.getCallerIdBySessionId(session.id());
            if (GameSessionService.callerId2SessionMap.containsKey(newCallerId)) {
                // 如果使用了同一个账号重复登录，先踢掉线上的玩家
                clearSession(newCallerId);
            }
            GameSessionService.updateCallerId(oldCallerId, newCallerId, CallerType.CHARACTER);

            // 获取地图，进入对应位置
            pushMap(playerCharacter, location.getLocation());
            moveTo(playerCharacter, location.getDataKey());

            // 玩家上线之后，还要通过房间通知上线锁在房间内的其他玩家，玩家上线
            playerCharacter.msg(String.format(GameWords.PLAYER_ONLINE, playerCharacter.getName()));
            GameCharacterManager.characterMoveIn(playerCharacter);

            // 发送玩家的属性信息
            showStatus(playerCharacter);
            // 发送玩家的装备信息
            returnEquippedEquipments(playerCharacter);
            // 发送玩家的技能信息
            returnAllSkills(playerCharacter);
            //发送玩家的称号信息
            playerCharacter.msg(new TitlesMessage(playerCharacter));
            // 发送玩家的背包信息
            showBagpack(playerCharacter);
            // 发送玩家当前的状态
            playerCharacter.msg(new PlayerCharacterStateMessage(playerCharacter.getState()));
            // 发送进入游戏状态
            playerCharacter.msg(new PuppetMessage(new PuppetInfo(playerCharacter)));
            // 发送频道信息
            showChannels(playerCharacter);
            //如果在战斗中
            CombatSense combatSense = CombatHandler.getCombatSense(playerCharacter.getId());
            if (combatSense != null) {
                NormalCombat normalCombat = new NormalCombat();
                normalCombat.init(combatSense);
                normalCombat.startCombat(combatSense);
            }
            // 如果死亡发送复活命令
            if (playerCharacter.getHp() <= 0) {
                GameSessionService.updateCallerType(playerCharacterId, CallerType.DIE);
                playerCharacter.msg(new RebornCommandsMessage(playerCharacter));
            }
            //当前用户接受邮件列表
            List<MailObject> recipientList = MongoMapper.mailObjectRepository.findMailObjectListByRecipientId(playerCharacterId);
            if (recipientList != null && recipientList.size() > 0) {
                playerCharacter.msg(new MailObjectMessage(playerCharacter, recipientList));
            }

            //给好友发送上线信息
            Map<String, SimpleCharacter> friendMap = playerCharacter.getFriends();
            for (String id : friendMap.keySet()) {
                Session targetSession = null;
                targetSession = GameSessionService.getSessionByCallerId(id);
                if (targetSession != null) {
                    targetSession.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(FRIEND_ONLINE_REMINDER, playerCharacter.getName()))));
                }
            }
            //仇人上线提醒
            Map<String, SimpleCharacter> enemyMap = playerCharacter.getEnemys();
            for (String id : enemyMap.keySet()) {
                Session targetSession = null;
                targetSession = GameSessionService.getSessionByCallerId(id);
                WorldRoomObject worldRoomObject = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(playerCharacter.getLocation());
                if (targetSession != null) {
                    targetSession.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(ENEMY_ONLINE_REMINDER, playerCharacter.getName(), worldRoomObject.getName()))));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.sendText(JsonResponse.JsonStringResponse(new AlertMessage("进入游戏失败，请稍后重试")));
        }
    }

    /**
     * 玩家角色进入游戏的时候，要清空掉原来的session信息
     *
     * @param playerCharacterId 玩家的id
     */
    public static void clearSession(String playerCharacterId) {
        Session session = GameSessionService.getSessionByCallerId(playerCharacterId);
        if (session != null) {
            session.sendText(JsonResponse.JsonStringResponse(new AlertMessage("你的帐号已在别处上线，你已被强制下线！")));
            session.close();
        }
        GameSessionService.removeSessionBySessionId(session.id());
    }

    /**
     * 进入某一个地方之后： 返回这个地方的所有信息
     * <pre>
     *  玩家查看周围的信息
     *  周围信息包含当前房间的基本信息 dbref, name, background, peaceful, desc, icon
     *  房间内可以看到的物体 things
     *  房间内可以看到的NPC npcs
     *  房间内可以用的出口  exits
     *  房间内可以执行的命令 commands
     *  房间内的其他玩家  players
     * </pre>
     *
     * @param playerCharacter PlayerCharacter 玩家角色
     ******************************************************************************/
    public static void lookAround(PlayerCharacter playerCharacter) {
        WorldRoomObject location = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(playerCharacter.getLocation());
        // 查看周围信息之前，先解锁一下地图,如果当前区域已经解锁，则不用强制更新地图 如果是第一次进入某个区域则需要强制更新地图
        boolean needForceUpdateMap = !playerCharacter.getRevealedMap().containsKey(location.getLocation());
        revealMap(playerCharacter, location, needForceUpdateMap);
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
        for (String objectKey : location.getThings()) {
            WorldObjectObject object = MongoMapper.worldObjectObjectRepository.findWorldObjectObjectByDataKey(objectKey);
            if (GameWorldManager.isVisibleForPlayerCharacter(object, playerCharacter)) {
                objects.add(new SimpleObject(object));
            }
        }
        // 可以看到的物品生成器
        for (String objectKey : location.getCreators()) {
            WorldObjectCreator creator = MongoMapper.worldObjectCreatorRepository.findWorldObjectCreatorByDataKey(objectKey);
            if (GameWorldManager.isVisibleForPlayerCharacter(creator, playerCharacter)) {
                objects.add(new SimpleObject(creator));
            }
        }
        location_info.put("things", objects);
        // 可以看到的出口
        List<SimpleObject> exits = new ArrayList<SimpleObject>();
        for (String exitKey : location.getExits()) {
            WorldExitObject exit = MongoMapper.worldExitObjectRepository.findWorldExitObjectByDataKey(exitKey);
            if (GameWorldManager.isVisibleForPlayerCharacter(exit, playerCharacter)) {
                exits.add(new SimpleObject(exit));
            }
        }
        location_info.put("exits", exits);
        // 可以看到的玩家
        List<SimpleCharacter> playerCharacters = new ArrayList<SimpleCharacter>();
        for (String playerId : location.getPlayers()) {
            PlayerCharacter otherPlayerCharacter = MongoMapper.playerCharacterRepository.findPlayerCharacterById(playerId);
            if (isVisibleForOtherPlayerCharacter(playerCharacter, otherPlayerCharacter)) {
                playerCharacters.add(new SimpleCharacter(otherPlayerCharacter));
            }
        }
        location_info.put("players", playerCharacters);
        // 可以看到的NPC
        List<CommonCharacter> commonCharacterList = new ArrayList<>();
        List<SimpleCharacter> npcs = new ArrayList<>();
        for (String npcDataKey : location.getNpcs()) {
            WorldNpcObject npc = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectByDataKey(npcDataKey);
            if (GameWorldManager.isNpcVisibleForPlayerCharacter(npc, playerCharacter)) {
                SimpleCharacter simpleCharacter = new SimpleCharacter(npc);
                simpleCharacter.setProvide_quest(WorldNpcObjectManager.canProvideQuest(npc, playerCharacter));
                simpleCharacter.setComplete_quest(WorldNpcObjectManager.canTurnInQuest(npc, playerCharacter));
                npcs.add(simpleCharacter);
                if (npc.getHp() > 0) {
                    commonCharacterList.add(npc);
                }
            }
        }
        //犯罪值大于阈值，触发npc战斗
        if (location.isCanAttack() && playerCharacter.getCrimeValue() >= CRIME_VALUE_ATTACK && commonCharacterList.size() > 0) {
            getAttack(playerCharacter, commonCharacterList);
        }
        location_info.put("npcs", npcs);
        // 可以执行的命令
        location_info.put("cmds", WorldRoomObjectManager.getAvailableCommands(location, playerCharacter));
        playerCharacter.msg(new LookAroundMessage(location_info));
        playerCharacter.msg(new CurrentLocationMessage(new RoomInfo(location)));
    }

    /**
     * 自动发起战斗
     */
    public static void getAttack(PlayerCharacter caller, List<CommonCharacter> commonCharacterList) {
        CombatSense combatSense = CombatHandler.getCombatSense(caller.getId());
        if (combatSense == null) {
            ArrayList<CommonCharacter> redTeam = new ArrayList<>();
            ArrayList<CommonCharacter> blueTeam = new ArrayList<>();
            redTeam.add(caller);
            blueTeam.addAll(commonCharacterList);
            combatSense = new CombatSense(redTeam, blueTeam, 0);
        } else {
            combatSense.getBlueTeam().addAll(commonCharacterList);
        }
        for (CommonCharacter commonCharacter : commonCharacterList) {
            CombatHandler.addCombatSense(commonCharacter.getId(), combatSense);
        }
        CombatHandler.addCombatSense(caller.getId(), combatSense);

        NormalCombat normalCombat = new NormalCombat();
        normalCombat.init(combatSense);
        normalCombat.startCombat(combatSense);
    }

    /**
     * 前往一个房间
     * 玩家移动到一个房间，这个过程中不能单纯的把玩家放到这个房间
     * 而是要在程序内部通过出口，检查出口能否通过
     * 根据出口的情况，然后才进行移动
     *
     * @param playerCharacter 移动的玩家主体
     * @param exitId          出口的ID
     * @param session         通信通道
     */
    public static void gotoRoom(PlayerCharacter playerCharacter, String exitId, Session session) {
        WorldExitObject exit = MongoMapper.worldExitObjectRepository.findWorldExitObjectById(exitId);
        // STEP1： 检查出口是不是锁定的，然后看玩家是不是已经解锁
        if (exit.isLocked() && !playerCharacter.unlockedExit.contains(exitId)) {
            //没有解锁，根据解锁条件判断能否解锁
            if (WorldExitObjectManager.playerCharacterCanTranverse(playerCharacter, exit)) {
                // 能解锁，显示解锁交互
                session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(exit.getUnlockDescription())));
                moveTo(playerCharacter, exit.getDestination());
            } else {
                // 不能解锁，显示提示信息
                session.sendText(JsonResponse.JsonStringResponse(new AlertMessage(exit.getLockDescription())));
            }
        } else {
            // 直接通过
            moveTo(playerCharacter, exit.getDestination());
        }
    }

    /**
     * 玩家移动到一个新的房间
     * 本质上就是更新玩家的位置
     * 额外的工作室负责两个房间要分别广播玩家进入和离开的消息，并更新房间玩家变动信息给客户端
     */
    public static void moveTo(PlayerCharacter playerCharacter, String roomKey) {

        WorldRoomObject oldRoom = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(playerCharacter.getLocation());
        WorldRoomObject newRoom = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(roomKey);
        WorldRoomObjectManager.removeOfflinePlayer(oldRoom);
        WorldRoomObjectManager.removeOfflinePlayer(newRoom);
        playerCharacter.setRoomStep(0);
        //移动
        playerCharacter.setLocation(newRoom.getDataKey());
        if (oldRoom != null && !newRoom.getLocation().equals(oldRoom.getLocation())) {
            revealMap(playerCharacter, newRoom, true);
        }
        lookAround(playerCharacter);
        //变更房间内部的玩家信息
        if (oldRoom != null) {
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
        playerCharacter.msg("你来到了{g" + newRoom.getName() + "{n");
        //事件监测
        WorldRoomObjectManager.triggerArriveAction(newRoom, playerCharacter);
    }

    /**
     * 解锁新的地图
     * <pr>
     * 把当前的地图，放入已经解锁的地图Set里面
     * 根据当前地图包含的出口，拿到当前地图的邻居
     * 最终，给客户端返回当前区域已经解锁的所有地图
     * <p>
     * 如果已经被解锁则跳过
     * 如果没有被解锁则要解锁新的地图并发送给客户端
     * revealedMap的是一个字典，key为区域的标识，内容为当前区域已经解锁地图的Set
     * WARNING: 性能瓶颈！ 如果房间特别多，解锁房间和出口信息，会有一个n*1到n*4的循环
     * 优化之后： STEP2中的地图信息不用每一次都发送到客户端，而是在有解锁操作之后才更新
     * 每次玩家新进入游戏的时候客户端是没有玩家地图的，需要强制生成已经解锁的地图信息并推送
     * </pr>
     *
     * @param playerCharacter PlayerCharacter 角色
     * @param location        WorldRoomObject 所在位置
     * @param forceUpdateMap  boolean 是否强制更新
     ***************************************************************************/
    public static void revealMap(PlayerCharacter playerCharacter, WorldRoomObject location, boolean forceUpdateMap) {
        // STEP1: 先检查需不需要解锁，如果需要的话顺便初始化数据
        String areaKey = location.getLocation();
        Map<String, Set<String>> revealedMap = playerCharacter.getRevealedMap();
        //当前区域不存在，增加当前区域
        if (!revealedMap.containsKey(areaKey)) {
            revealedMap.put(areaKey, new HashSet<>());
        }
        //当前区域存在，但是房间未解锁，或者需要强制更新
        if (!revealedMap.get(areaKey).contains(location.getDataKey()) || forceUpdateMap) {
            Set<String> revealedRoomSet = revealedMap.get(areaKey);
            revealedRoomSet.add(location.getDataKey());
            revealedMap.put(areaKey, revealedRoomSet);
            playerCharacter.setRevealedMap(revealedMap);
            // STEP2：解锁完成之后，发送当前区域的可用地图和出口给玩家
            pushMap(playerCharacter, areaKey);
            // 数据持久化
            MongoMapper.playerCharacterRepository.save(playerCharacter);
        }
    }

    /**
     * 针对于一个区域，推送玩家在这个区域内解锁的所有地图和出口
     *
     * @param playerCharacter PlayerCharacter 角色
     * @param areaKey         所在区域的key
     */
    public static void pushMap(PlayerCharacter playerCharacter, String areaKey) {

        Map<String, Object> rooms = new HashMap<>();
        Map<String, ExitInfo> exits = new HashMap<>();
        if (!playerCharacter.getRevealedMap().containsKey(areaKey)) {
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
        playerCharacter.msg(new RevealedMapMessage(allRevealedMapInArea));
    }

    /**
     * 显示玩家的状态，包含玩家所有的属性
     * 玩的属性详情见客户点或：PlayerCharacterStatus
     *
     * @param playerCharacter 玩家主体
     */
    public static void showStatus(PlayerCharacter playerCharacter) {
        playerCharacter.msg(new PlayerCharacterStatus(playerCharacter));
    }

    /**
     * 显示玩家的频道信息
     */
    public static void showChannels(PlayerCharacter playerCharacter) {
        playerCharacter.msg(new PlayerCharacterChannelMessage(playerCharacter));
    }

    /**
     * 当玩家查看游戏世界内的物体生成器的时候返回物体信息和可执行的命令（操作）
     *
     * @param target  被查看的角色
     * @param caller  查看者角色
     * @param session 通信通道
     */
    public static void onPlayerLook(PlayerCharacter target, PlayerCharacter caller, Session session) {

        Map<String, Object> lookMessage = new HashMap<>();
        PlayerCharacterAppearance appearance = new PlayerCharacterAppearance(target);
        // 设置玩家可以对此物体执行的命令
        appearance.setCmds(getAvailableCommands(caller, target));
        appearance.setDesc(descriptionForTarget(caller, target));
        lookMessage.put("look_obj", appearance);
        session.sendText(JsonResponse.JsonStringResponse(lookMessage));
    }

    /**
     * 为另一个玩家生成对应的描述信息
     */
    public static String descriptionForTarget(PlayerCharacter playerCharacter, PlayerCharacter target) {
        String desc = "";
        String he = "";
        String title = "";
        if (playerCharacter.getId().equals(target.getId())) {
            he = "你";
        } else {
            switch (playerCharacter.getGender()) {
                case "男":
                    he = "他";
                    title = "大侠";
                    break;
                case "女":
                    he = "她";
                    title = "侠女";
                    break;
                case "不男不女":
                    he = "这位";
                    title = "公共";
                    break;
            }
        }
        if (target.getSchool() != null && !target.getSchool().equals("无门无派")) {
            School school = DbMapper.schoolRepository.findSchoolByDataKey(target.getSchool());
            if (null != school) {
                StringBuffer stringBuffer = new StringBuffer();
                // 称号未完善，暂时展示玩家名称
                if (StringUtils.isNotBlank(target.getTitle())) {
                    stringBuffer.append(DbMapper.playerTitleRepository.findPlayerTitleByDataKey(target.getTitle()).getName());
                }
                stringBuffer.append(target.getName());
                title = stringBuffer.toString();
                desc = String.format("%s是来自{g%s{n的{g%s{n", he, school.getName(), title);
            } else {
                desc = String.format("这人无门无派，浮萍一根，没有什么值得关注的。", he, title);
            }
        } else {
            desc = String.format("这人无门无派，浮萍一根，没有什么值得关注的。", he, title);
        }
        return desc;
    }

    /**
     * 与NPC展开对话
     */
    public static void talkToNpc(PlayerCharacter playerCharacter, WorldNpcObject npc) {
        if (npc.getDialogues().isEmpty()) {
            playerCharacter.msg(new MsgMessage(String.format("%s没有什么想和你说的", npc.getName())));
        } else {
            playerCharacter.msg(new DialogueListMessage(playerCharacter, npc));
        }
    }

    /**
     * 玩家接受任务
     */
    public static void acceptQuest(PlayerCharacter playerCharacter, String questKey) {
        Set<String> currentQuest = playerCharacter.getCurrentQuests();
        if (currentQuest == null) {
            currentQuest = new HashSet<>();
        }
        QuestObject questObject = GameQuestManager.create(playerCharacter, questKey);
        if (questObject != null) {
            currentQuest.add(questObject.getId());
            playerCharacter.msg(new MsgMessage(String.format("接受任务：{c%s{n", questObject.getName())));
        }
        playerCharacter.setCurrentQuests(currentQuest);
        GameCharacterManager.saveCharacter(playerCharacter);
        showQuests(playerCharacter);
        lookAround(playerCharacter);
    }

    /**
     * 玩家提交任务
     */
    public static void turnInQuest(PlayerCharacter playerCharacter, String questKey) {
        Set<String> newCurrentQuests = playerCharacter.getCurrentQuests();
        Set<String> newfinishedQuests = playerCharacter.getFinishedQuests();
        for (String questId : playerCharacter.getCurrentQuests()) {
            QuestObject questObject = MongoMapper.questObjectRepository.findQuestObjectById(questId);
            if (questObject.getDataKey().equals(questKey)) {
                newCurrentQuests.remove(questId);
                newfinishedQuests.add(questId);
                // 获取任务奖励
                for (QuestRewardList reward : questObject.getRewards()) {
                    String objectKey = reward.getObject();
                    CommonObject object = WorldObjectCreatorManager.createObject(objectKey);
                    receiveObjectToBagpack(playerCharacter, object, reward.getNumber());
                }
            }
        }
        playerCharacter.setCurrentQuests(newCurrentQuests);
        playerCharacter.setFinishedQuests(newfinishedQuests);
        GameCharacterManager.saveCharacter(playerCharacter);
        showQuests(playerCharacter);
        lookAround(playerCharacter);
    }

    /**
     * 显示玩家任务  发送任务列表到前端
     *
     * @param playerCharacter 玩家
     */
    public static void showQuests(PlayerCharacter playerCharacter) {
        playerCharacter.msg(new QuestMessage(playerCharacter));
    }

    /**
     * 另一个玩家可对此玩家执行的操作
     *
     * @param caller 另一个玩家
     * @param target 被查看的玩家
     * @return List 命令列表
     */
    private static List<EmbeddedCommand> getAvailableCommands(PlayerCharacter caller, PlayerCharacter target) {
        /*
         * @ 获取玩家对当前对象的可操作命令
         * @ 对于玩家之间来说 包含的命令有 加好友，秘语，切磋，攻击命令
         * */
        List<EmbeddedCommand> cmds = new ArrayList<>();
        if (!caller.getId().equals(target.getId())) {
            // 添加好友命令
            if (!target.getFriends().containsKey(caller.getId())) {
                cmds.add(new EmbeddedCommand("结交", "add_friend", target.getId()));
            }
            // 添加攻击命令
            cmds.add(new EmbeddedCommand("攻击", "attack", target.getId()));
            // 添加切磋命令
            cmds.add(new EmbeddedCommand("切磋", "learn_from_friend", target.getId()));
        }
        return cmds;
    }

    /**
     * 另一个玩家是否能看到自己
     */
    public static boolean isVisibleForOtherPlayerCharacter(PlayerCharacter self, PlayerCharacter other) {
        /*
         * 玩家是否可以被其他玩家看到
         * */
        // 首先自己不能看到自己，在这一块儿客户端自己有处理，后台不需要多此一举把玩家自己显示到房间中
        if (other != null) {
            return !self.getId().equals(other.getId());
        }
        return true;
    }

    /**
     * 拜师
     */
    public static void findTeacher(PlayerCharacter playerCharacter, String targetId, Session session) {
        /*
         * 玩家拜师
         * 首先玩家必须没有师傅
         * */
        WorldNpcObject teacher = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(targetId);
        if (playerCharacter.getTeacher().trim().equals("")) {
            if (teacher != null && teacher.getLocation().equals(playerCharacter.getLocation())) {
                // 师傅必须存在而且师傅必须和自己在一个房间
                playerCharacter.setTeacher(teacher.getDataKey());
                // 如果师傅有门派，则设置角色的门派和师傅一致
                playerCharacter.setSchool(teacher.getSchool());
                MongoMapper.playerCharacterRepository.save(playerCharacter);
                showStatus(playerCharacter);
                showChannels(playerCharacter);
                session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(String.format(GameWords.PLAYER_FINDED_TEACHER, teacher.getName(), teacher.getName(), teacher.getName()))));
            } else {
                session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(String.format(GameWords.TEACHER_NOT_FOUND, teacher.getName()))));
            }
        } else {
            session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(String.format(GameWords.PLAYER_MUST_LEAVE_OLD_TEACHER, teacher.getName()))));
        }
    }

    /**
     * 通过好友申请
     */
    public static void requestFriend(PlayerCharacter playerCharacter, String targetId, Session session) {
        /*
         * @ 玩家通过另一个玩家的ID发出好友申请
         * */
        PlayerCharacter target = MongoMapper.playerCharacterRepository.findPlayerCharacterById(targetId);
        Session targetSession = GameSessionService.getSessionByCallerId(targetId);
        if (target.getFriendRequests().containsKey(playerCharacter.getId())) {
            // 已经发送过的请求
            session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(String.format(GameWords.PLAYER_REPEAT_REQUEST_FRIEND, target.getName()))));
        } else {
            // 对端添加好友申请信息
            Map<String, SimpleCharacter> friendRequests = target.getFriendRequests();
            friendRequests.put(playerCharacter.getId(), new SimpleCharacter(playerCharacter));
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

    /**
     * 请求添加好友
     */
    public static void acceptFriendRequest(PlayerCharacter playerCharacter, String friendId, Session session) {
        /*
         * @ 同意好友的请求
         * @ 把好友信息从申请列表移动到已通过列表
         * @ 给对方的好友列表里添加自己的信息，如果对方的申请列表里面也有自己的信息则删除
         * */
        PlayerCharacter friend = MongoMapper.playerCharacterRepository.findPlayerCharacterById(friendId);
        // 把好友请求从 requestFriends 移动到 friends 里面
        Map<String, SimpleCharacter> friends = playerCharacter.getFriends();
        Map<String, SimpleCharacter> friendRequests = playerCharacter.getFriendRequests();
        SimpleCharacter simpleFriendInfo = friendRequests.get(friendId);
        friends.put(friendId, simpleFriendInfo);
        friendRequests.remove(friendId);
        playerCharacter.setFriendRequests(friendRequests);
        playerCharacter.setFriends(friends);
        // 把对面好友的 requestFriends 和 friends 两个Map进行同步
        Map<String, SimpleCharacter> targetsFriends = friend.getFriends();
        Map<String, SimpleCharacter> targetsFriendRequests = friend.getFriendRequests();
        targetsFriendRequests.remove(playerCharacter.getId());
        targetsFriends.put(playerCharacter.getId(), new SimpleCharacter(playerCharacter));
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

    /**
     * 删除/拒绝好友请求
     *
     * @param caller
     * @param friendId
     * @param session
     */
    public static void rejectFriendRequest(PlayerCharacter caller, String friendId, Session session) {
        /*
         * @ 拒绝好友的请求/删除好友
         * @ 把好友信息从申请列表移除/好友列表移除
         * @ 给对方发送拒绝信息/送双方好友列表中删除
         * */
        PlayerCharacter friend = MongoMapper.playerCharacterRepository.findPlayerCharacterById(friendId);
        // 好友列表
        Map<String, SimpleCharacter> friends = caller.getFriends();
        // 请求列表
        Map<String, SimpleCharacter> friendRequests = caller.getFriendRequests();
        //对方session
        Session friendsSession = GameSessionService.getSessionByCallerId(friendId);
        //删除好友
        if (friends.containsKey(friendId)) {
            friends.remove(friendId);
            //对方删除好友信息
            friend.getFriends().remove(caller.getId());
            // 持久化
            MongoMapper.playerCharacterRepository.save(friend);
            if (friendsSession != null) {
                // 发送新的好友列表给对方客户端
                friendsSession.sendText(JsonResponse.JsonStringResponse(new FriendListMessage(friend)));
            }
            // 发送好友删除之后的消息
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.DELETE_BE_APPLIED_FRIEND_REQUEST, friend.getName()))));
        }
        //删除好友请求
        if (friendRequests.containsKey(friendId)) {
            friendRequests.remove(friendId);
            if (friendsSession != null) {
                friendsSession.sendText(JsonResponse.JsonStringResponse(new MsgMessage(String.format(GameWords.PLAYER_BE_REFUSED_FRIEND_REQUEST, caller.getName()))));
            }
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.PLAYER_REFUSE_FRIEND_REQUEST, friend.getName()))));
        }
        // 发送新的好友列表的给己方客户端
        session.sendText(JsonResponse.JsonStringResponse(new FriendListMessage(caller)));
        MongoMapper.playerCharacterRepository.save(caller);
    }

    /**
     * 发送消息给其他玩家
     */
    public static void sendMessageToOtherPlayer(PlayerCharacter playerCharacter, String name, String message, Session selfSession, String type) {
        /*
         * @发送消息给其他玩家
         * */
        PlayerCharacter target = MongoMapper.playerCharacterRepository.findPlayerCharacterByName(name.trim());
        if (null == target) {
            selfSession.sendText(JsonResponse.JsonStringResponse(new MsgMessage("发送失败!")));
            return;
        }
        Session targetSession = GameSessionService.getSessionByCallerId(target.getId());
        if (targetSession != null) {
            if (type.equals("friend_chat")) {
                //给发送好友的人返回数据
                selfSession.sendText(JsonResponse.JsonStringResponse(new FriendMessage(message, playerCharacter, target, true)));
                //给接受好友的人发送数据
                targetSession.sendText(JsonResponse.JsonStringResponse(new FriendMessage(message, playerCharacter, target, false)));
            } else if (type.equals("private_chat")) {
                //给发送私聊的人返回数据
                selfSession.sendText(JsonResponse.JsonStringResponse(new SayMessage(message, target, playerCharacter, true)));
                //给接受私聊的人发送数据
                targetSession.sendText(JsonResponse.JsonStringResponse(new SayMessage(message, target, playerCharacter, false)));

            }
        } else {
            selfSession.sendText(JsonResponse.JsonStringResponse(new MsgMessage("对方可能不在线")));
        }
    }

    /**
     * 访问商店
     */
    public static void visitShop(PlayerCharacter playerCharacter, String shopKey, boolean sysShop) {
        if (sysShop) {
            playerCharacter.msg(new OpenShopMessage());
        } else {
            playerCharacter.msg(new OpenShopMessage(shopKey));
        }
    }

    /**
     * 玩家从师傅那里学习技能
     *
     * @param playerCharacter 玩家
     * @param skillKey        要学习的技能的key
     * @param teacherId       师傅的ID
     * @param session         通信通道
     * @return Runnable 挂机执行主体
     */
    public static Runnable learnSkillFromTeacher(PlayerCharacter playerCharacter, String skillKey, String teacherId, Session session) {
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
        // 师傅合法性检测
        if (!teacher.getDataKey().equals(playerCharacter.getTeacher())) {
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.ERROR_TEACHER, teacher.getName()))));
            return null;
        }
        // 检测师傅是否有对应技能
        else if (!GameCharacterManager.hasSkill(teacher, skillKey)) {
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.TEACHER_HAS_NO_SKILL, teacher.getName()))));
            return null;
        }
        // 检测学习条件
        else if (!ConditionHandler.matchCondition(skillTemplate.getLearnCondition(), playerCharacter)) {
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.CAN_NOT_LEARN_SKILL, teacher.getName()))));
            return null;
        }
        // 检测玩家潜能
        else if (playerCharacter.getPotential() < 100) {
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(GameWords.NO_ENOUGH_POTENTIAL)));
            return null;
        }
        // 检测子技能是否满足条件
        else if (!SkillObjectManager.SatisfyBasicSkill(skillKey, playerCharacter)) {
            Skill skill = DbMapper.skillRepository.findSkillByDataKey(skillKey);
            Skill basicSkill = DbMapper.skillRepository.findSkillByDataKey(skill.getBasicSkill());
            if (null == basicSkill) {
                session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.BASIC_SKILL_LEVEL_GT, skill.getName(), "此技能"))));
            } else {
                session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.BASIC_SKILL_LEVEL_GT, skill.getName(), basicSkill.getName()))));
            }

            return null;
        }
        //检查npc技能等级是否满足条件
        else if (!SkillObjectManager.SatisfyNpcSkill(teacher, playerCharacter, skillKey)) {
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.NPC_SKILL_LEVEL_GT))));
            return null;
        } else {
            playerCharacter.msg(String.format(GameWords.START_LEARNED_SKILL, teacher.getName(), skillTemplate.getName()));
            playerCharacter.setState(CharacterState.STATE_LEARN_SKILL);
            MongoMapper.playerCharacterRepository.save(playerCharacter);
            session.sendText(JsonResponse.JsonStringResponse(new PlayerCharacterStateMessage(playerCharacter.getState())));
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Session updatedSession = GameSessionService.getSessionByCallerId(playerCharacter.getId());
                    learnSkill(playerCharacter, skillKey, updatedSession, teacher);
                }
            };
            return runnable;
        }

    }

    /**
     * 玩家从技能书那里学习技能
     *
     * @param playerCharacter 玩家
     * @param skillBookObject 技能书
     * @return Runnable 挂机执行主体
     */
    public static Runnable learnSkillBySkillBook(PlayerCharacter playerCharacter, SkillBookObject skillBookObject) {
        /*
         * @ 玩家从技能书这里学习技能
         * @ 这个技能本身是一个定时器
         * @ 第一步： 参数检查： 获取技能书支持学习的技能
         * @ 第三步： 条件判定： 检查玩家是否满足学习这个技能的条件
         * @ 第四步： 潜能检查： 检查学习技能书是否需要潜能,如果需要的话潜能是否足够
         * @ 上述四层检查全都通过，则返回一个runnable放在定时任务里面运行，并在runnable里面继续检查，如果不满足则跳出runnable
         * */
        // 获取技能书内部的技能
        String skillKey = SkillBookObjectManager.getCurrentSkill(skillBookObject);
        if (skillKey == null) {
            playerCharacter.msg(new ToastMessage("这是一本空的技能书!"));
            return null;
        }

        // 检测学习条件
        if (DbMapper.skillRepository.existsByDataKey(skillKey)) {
            Skill skill = DbMapper.skillRepository.findSkillByDataKey(skillKey);
            if (!ConditionHandler.matchCondition(skill.getLearnCondition(), playerCharacter)) {
                playerCharacter.msg(new ToastMessage(String.format("你无法学习技能%s", skill.getName())));
                return null;
            }
        }

        // 檢查玩家技能是否大於技能書最大等級
        SkillObject characterSkillObject = GameCharacterManager.findSkillBySKillKey(playerCharacter, skillBookObject.getCurrentSkill());
        if (characterSkillObject != null && skillBookObject.getMax_level() <= characterSkillObject.getLevel()) {
            playerCharacter.msg(new ToastMessage(String.format("这本书上记载的{g%s{n对你来说太粗浅了", characterSkillObject.getName())));
            return null;
        }

        // 检测玩家潜能
        if (skillBookObject.isUse_potential() && playerCharacter.getPotential() < 100) {
            playerCharacter.msg(new ToastMessage(GameWords.NO_ENOUGH_POTENTIAL));
            return null;
        } else {
            playerCharacter.setState(CharacterState.STATE_LEARN_SKILL);
            MongoMapper.playerCharacterRepository.save(playerCharacter);
            playerCharacter.msg(new PlayerCharacterStateMessage(playerCharacter.getState()));
            playerCharacter.msg(String.format(GameWords.START_LEARNED_SKILL_BOOK, skillBookObject.getName()));
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Session updatedSession = GameSessionService.getSessionByCallerId(playerCharacter.getId());
                    SkillObject skillObject = learnSkill(playerCharacter, skillKey, updatedSession, skillBookObject);
                    if (skillBookObject.getMax_level() <= skillObject.getLevel()) {
                        playerCharacter.msg(new MsgMessage("这上面记载的武学已经被你掌握纯属了，在钻研下去恐怕也是浪费时间啊！"));
                        PlayerScheduleManager.shutdownExecutorByCallerId(playerCharacter.getId());
                    }
                    if (playerCharacter.getPotential() <= 0 && skillBookObject.isUse_potential()) {
                        playerCharacter.msg(new MsgMessage("你的潜能已经用尽，无法继续研读了"));
                        PlayerScheduleManager.shutdownExecutorByCallerId(playerCharacter.getId());
                    }
                }
            };
            return runnable;
        }

    }

    /**
     * 玩家触发学习技能事件
     *
     * @param playerCharacter 玩家角色
     * @param skillKey        技能Key
     * @param record          学习技能事件的记录数据
     */
    public static Runnable learnSkillByEvent(PlayerCharacter playerCharacter, String skillKey, ActionLearnSkill record) {
        /*
         * @ 玩家通过事件学习技能
         * @ 这是一个定时器
         * @ 不需要检查潜能 直接执行学习即可
         * @ 需要检查技能等级 一旦技能等级大于等于maxLevel则退出
         * */
        Skill skillTemplate = DbMapper.skillRepository.findSkillByDataKey(skillKey);
        SkillObject skillObject = GameCharacterManager.findSkillBySKillKey(playerCharacter, skillKey);
        if (skillObject != null && skillObject.getLevel() >= record.getMaxLevel()) {
            playerCharacter.msg(new MsgMessage("这里面所记录的武学对你来说{g太粗浅{n了。"));
            return null;
        } else {
            playerCharacter.setState(CharacterState.STATE_LEARN_SKILL);
            MongoMapper.playerCharacterRepository.save(playerCharacter);
            playerCharacter.msg(new PlayerCharacterStateMessage(playerCharacter.getState()));
            Runnable runnable = new Runnable() {
                SkillObject updatedSkillObject = skillObject;

                @Override
                public void run() {
                    Session updatedSession = GameSessionService.getSessionByCallerId(playerCharacter.getId());
                    if (updatedSkillObject != null && updatedSkillObject.getLevel() >= record.getMaxLevel()) {
                        playerCharacter.msg(new MsgMessage("你{c钻研{n了很久，发现这里面记录的武学已经{r没有研究的价值{n了。"));
                        PlayerScheduleManager.shutdownExecutorByCallerId(playerCharacter.getId());
                    } else {
                        updatedSkillObject = learnSkill(playerCharacter, skillKey, updatedSession, record);
                    }
                }
            };
            return runnable;
        }
    }

    /**
     * 玩家通过物品交换学习技能
     *
     * @param skillKey        要学习的技能的key
     * @param playerCharacter 要学修技能的角色
     * @param session         通信通道
     * @param teacherId       教授技能的老师
     * @return Runnable 学习技能的逻辑
     */
    public static Runnable learnSkillByObject(PlayerCharacter playerCharacter, String skillKey, String teacherId, Session session) {
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
        if (!playerCharacter.getLearnByObjectRecord().containsKey(teacher.getName()) || (playerCharacter.getLearnByObjectRecord().get(teacher.getName()) <= 0)) {
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.NO_ENOUGH_POTENTIAL_BALANCE, teacher.getName()))));
            return null;
        }
        // 检查NPC是否有这个技能
        else if (!GameCharacterManager.hasSkill(teacher, skillKey)) {
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.TEACHER_HAS_NO_SKILL, teacher.getName()))));
            return null;
        }
        // 检查玩家能否学习这个技能
        else if (!ConditionHandler.matchCondition(skillTemplate.getLearnCondition(), playerCharacter)) {
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.CAN_NOT_LEARN_SKILL, teacher.getName()))));
            return null;
        }
        // 检查玩家自身潜能是否还足够(如果是知识类技能的学习则不检查这一项)
        else if (playerCharacter.getPotential() <= 0 && !skillTemplate.getCategoryType().equals("SCT_ZHISHI")) {
            session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(GameWords.NO_ENOUGH_POTENTIAL)));
            return null;
        } else {
            playerCharacter.setState(CharacterState.STATE_LEARN_SKILL);
            MongoMapper.playerCharacterRepository.save(playerCharacter);
            session.sendText(JsonResponse.JsonStringResponse(new PlayerCharacterStateMessage(playerCharacter.getState())));
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Session updatedSession = GameSessionService.getSessionByCallerId(playerCharacter.getId());
                    // 充值的潜能点是否用完检查，如果已经用完则停止学习
                    if ((playerCharacter.getLearnByObjectRecord().get(teacher.getName()) <= 0)) {
                        updatedSession.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.NO_ENOUGH_POTENTIAL_BALANCE, teacher.getName()))));
                        PlayerScheduleManager.shutdownExecutorByCallerId(playerCharacter.getId());
                    } else {
                        learnSkill(playerCharacter, skillKey, updatedSession, teacher);
                    }
                }
            };
            return runnable;
        }
    }

    /**
     * 玩家学习技能的逻辑实现
     * 如果玩家已经有了这个技能，则不需要新建，进行升级操作
     * 如果玩家没有这个技能，则新创建
     * 返回玩家学习的技能
     *
     * @param playerCharacter 玩家
     * @param skillKey        技能key
     * @param updatedSession  通信通道
     * @param learnTarget     学习对象
     */
    public static SkillObject learnSkill(PlayerCharacter playerCharacter, String skillKey, Session updatedSession, Object learnTarget) {
        Skill template = DbMapper.skillRepository.findSkillByDataKey(skillKey);
        SkillObject skillObject = GameCharacterManager.getCharacterSkillByDataKey(playerCharacter, template.getDataKey());
        if (skillObject != null) {
            // 给技能充能
            SkillObjectManager.chargeSkill(skillObject, playerCharacter, updatedSession, learnTarget);
        } else {
            // 没有学会技能 新建技能
            skillObject = SkillObjectManager.create(skillKey);
            skillObject.setOwner(playerCharacter.getId());
            skillObject.setLevel(1);
            SkillObjectManager.calculusEffects(playerCharacter, null, skillObject);
            MongoMapper.skillObjectRepository.save(skillObject);

            // 如果是知识类技能，默认装备并生效
            if (skillObject.getCategoryType().equals("SCT_ZHISHI")) {
                skillObject.getEquippedPositions().add("zhishi");
                if (!playerCharacter.getEquippedSkills().containsKey("zhishi")) {
                    playerCharacter.getEquippedSkills().put("zhishi", new HashSet<>());
                }
                playerCharacter.getEquippedSkills().get("zhishi").add(skillObject.getId());
                MongoMapper.skillObjectRepository.save(skillObject);
            }

            SkillObjectManager.bindSubSkills(skillObject, playerCharacter.getId(), 1);
            // 绑定之后强制更新skillObject
            skillObject = MongoMapper.skillObjectRepository.findSkillObjectById(skillObject.getId());

            playerCharacter.getSkills().add(skillObject.getId());
            for (String subSkillId : skillObject.getSubSKills()) {
                playerCharacter.getSkills().add(subSkillId);
            }
            MongoMapper.playerCharacterRepository.save(playerCharacter);
            updatedSession.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.LEARNED_SKILL, skillObject.getName()))));
            updatedSession.sendText(JsonResponse.JsonStringResponse(new MsgMessage(String.format(GameWords.LEARNED_SKILL, skillObject.getName()))));
        }

        returnAllSkills(playerCharacter);
        showStatus(playerCharacter);
        return skillObject;
    }

    /**
     * 显示所有技能
     *
     * @param playerCharacter 玩家角色
     */
    public static void returnAllSkills(PlayerCharacter playerCharacter) {

        Map<String, Map<String, SimpleSkill>> skills = new HashMap<>();
        // 玩家技能分类
        for (String categoryTypeKey : SkillTypeHandler.categoryTypeMapping.keySet()) {
            skills.put(categoryTypeKey, new HashMap<>());
        }
        // 挨个加入玩家技能
        for (String skillId : playerCharacter.getSkills()) {
            SkillObject skillObject = MongoMapper.skillObjectRepository.findSkillObjectById(skillId);
            SimpleSkill skillInfo = new SimpleSkill(skillObject);
            // 获得技能可以执行的命令
            skillInfo.setCmds(SkillObjectManager.getAvailableCommands(skillObject, playerCharacter));
            skills.get(skillObject.getCategoryType()).put(skillObject.getDataKey(), skillInfo);
        }
        playerCharacter.msg(new PlayerCharacterSkills(skills));
    }

    /**
     * 显示特定技能位置上的技能
     *
     * @param playerCharacter 玩家角色
     * @param position        技能位置
     */
    public static void getSkillsByPosition(PlayerCharacter playerCharacter, String position) {
        /*
         * @ 获得玩家某一个位置的所有技能的key，分为已经装备的和没有装备的
         * @ 最终返回给客户端的数据包含两个字符串集合，used 和 can_replace
         * */

        Map<String, Set<String>> skillsInPosition = new HashMap<>();
        Set<String> usedSkills = new HashSet<>();
        Set<String> canReplacedSkills = new HashSet<>();
        for (String skillId : playerCharacter.getSkills()) {
            SkillObject skillObject = MongoMapper.skillObjectRepository.findSkillObjectById(skillId);
            // 只取得当前位置可用的技能
            if (skillObject.isPassive() && skillObject.getPositions().contains(position)) {
                if (skillObject.getEquippedPositions().contains(position)) {
                    //该技能装备到了这个位置
                    usedSkills.add(skillObject.getDataKey());
                } else {
                    //该技能可以装备到这个位置，但是当前没有装备
                    canReplacedSkills.add(skillObject.getDataKey());
                }
            }
        }
        playerCharacter.msg(new PositionSkillsMessage(usedSkills, canReplacedSkills));
    }

    /**
     * 当装备等唯一物品，强化，进阶或者属性发生改变后，调用该方法，同步背包信息
     */
    public static void syncBagpack(PlayerCharacter playerCharacter, CommonObject commonObject) {
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(playerCharacter.getBagpack());
        CommonItemContainerManager.updateItem(bagpackObject, commonObject);
        MongoMapper.bagpackObjectRepository.save(bagpackObject);
    }

    /**
     * 返回玩家的背包信息
     *
     * @param playerCharacter 玩家
     */
    public static void showBagpack(PlayerCharacter playerCharacter) {
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(playerCharacter.getBagpack());
        Map<String, CommonObjectInfo> valuess = bagpackObject.getItems();
        // Collection<CommonObjectInfo> values = bagpackObject.getItems().values();
        // playerCharacter.msg(new BagPackListMessage(new ArrayList<CommonObjectInfo>(values)));
        playerCharacter.msg(new BagPackListMessage(valuess));
    }

    /**
     * 显示玩家所有的装备
     *
     * @param playerCharacter 玩家角色
     */
    public static void returnEquippedEquipments(PlayerCharacter playerCharacter) {
        playerCharacter.msg(new EquipmentMessage(playerCharacter));
    }

    /**
     * 接受一件物品放入背包
     *
     * @param playerCharacter 玩家
     * @param commonObject    物品  可以是 NormalObjectObject、EquipmentObject、GemObject
     * @param number          数量
     * @return 放入是否成功
     */
    public static boolean receiveObjectToBagpack(PlayerCharacter playerCharacter, CommonObject commonObject, int number) {
        /*
         * 接受物品到背包
         * 把物品放入背包
         * */
        if (commonObject == null) {
            playerCharacter.msg(new MsgMessage("无法获取此物品!"));
            return false;
        }
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(playerCharacter.getBagpack());
        if (CommonItemContainerManager.addItem(bagpackObject, commonObject, number)) {
            commonObject.setTotalNumber(commonObject.getTotalNumber() + number);
            MongoMapper.bagpackObjectRepository.save(bagpackObject);
            commonObject.setOwner(playerCharacter.getId());
            CommonObjectBuilder.save(commonObject);
            playerCharacter.msg(new ToastMessage(new GettingObjectMessage(commonObject, number).getMsg()));
            showBagpack(playerCharacter);
            afterPlayerReceiveObject(playerCharacter, commonObject, number);
            return true;
        } else {
            playerCharacter.msg(new MsgMessage(String.format(GameWords.CAN_NOT_GET_OBJECT, commonObject.getName())));
            return false;
        }
    }

    /**
     * 接受一件物品放入背包
     *
     * @param playerCharacter 玩家
     * @param commonObjectKey 物品  可以是 NormalObjectObject、EquipmentObject、GemObject
     * @param number          数量
     * @return 放入是否成功
     */
    public static boolean receiveObjectToBagpack(PlayerCharacter playerCharacter, String commonObjectKey, int number) {
        /*
         * 接受物品到背包
         * 把物品放入背包
         * */
        CommonObject commonObject = CommonObjectBuilder.buildCommonObject(commonObjectKey);
        if (commonObject == null) {
            return false;
        }
        // 如果已经有了这个物品，则直接添加即可，并只能有一个格子
        if (hasObject(playerCharacter, commonObjectKey, 0) && commonObject.isUnique()) {
            commonObject = CommonObjectBuilder.findObjectByDataKeyAndOwner(commonObjectKey, playerCharacter.getId());
            if (commonObject instanceof EquipmentObject) {
                EquipmentObject equipmentObject = (EquipmentObject) CommonObjectBuilder.buildCommonObject(commonObjectKey);
                return receiveObjectToBagpack(playerCharacter, equipmentObject, number);
            } else if (commonObject instanceof SkillBookObject) {
                SkillBookObject skillBookObject = (SkillBookObject) CommonObjectBuilder.buildSkillBookObject(commonObjectKey);
                return receiveObjectToBagpack(playerCharacter, skillBookObject, number);
            }
        } else {
            CommonObjectBuilder.save(commonObject);
            return receiveObjectToBagpack(playerCharacter, commonObject, number);
        }
        return receiveObjectToBagpack(playerCharacter, commonObject, number);
    }

    /**
     * 玩家接受物品之后 要做的处理
     */
    public static void afterPlayerReceiveObject(PlayerCharacter playerCharacter, CommonObject commonObject, int number) {
        // 检查是否是任务物品
        for (String questId : playerCharacter.getCurrentQuests()) {
            QuestObject questObject = MongoMapper.questObjectRepository.findQuestObjectById(questId);
            Set<ObjectiveStatus> newObjectives = new HashSet<>();
            boolean questAccomplished = true;
            for (ObjectiveStatus objective : questObject.getObjectives()) {
                // 如果是任务物品则更新任务目标状态
                if (objective.getObject().equals(commonObject.getDataKey())) {
                    objective.setAccomplished(objective.getAccomplished() + number);
                    newObjectives.add(objective);
                }
                questAccomplished = objective.getAccomplished() >= objective.getTotal();
            }
            questObject.setObjectives(newObjectives);
            questObject.setAccomplished(questAccomplished);
            MongoMapper.questObjectRepository.save(questObject);
            if (questObject.isAccomplished()) {
                playerCharacter.msg(String.format("完成任务 {c%s{n", questObject.getName()));
                lookAround(playerCharacter);
            }
        }
    }

    /**
     * 玩家购买商品
     *
     * @param playerCharacter 玩家角色
     * @param goodsKey        商品的Key
     */
    public static void buyGoods(PlayerCharacter playerCharacter, String goodsKey) {
        ShopGoods goods = DbMapper.shopGoodsRepository.findShopGoodsByDataKey(goodsKey);
        int price = goods.getPrice();
        String unit = goods.getUnit();
        if (castMoney(playerCharacter, unit, price)) {
            int number = goods.getNumber();
            receiveObjectToBagpack(playerCharacter, goods.getGoods(), number);
        } else {
            playerCharacter.msg("{r你的钱不够...{n");
        }
    }

    /**
     * 玩家使用货币
     *
     * @param playerCharacter 玩家
     * @param unit            货币单位
     * @param price           价格
     */
    public static boolean castMoney(PlayerCharacter playerCharacter, String unit, int price) {
        if (price == 0) {
            return true;
        }
        // 有足够的钱，可以付款
        if (hasObject(playerCharacter, unit, price)) {
            return discardObject(playerCharacter, unit, price);
        } else {
            // 钱不够，若果是银子不够，但还有金子
            if (unit.equals("OBJECT_YINLIANG") && discardObject(playerCharacter, "OBJECT_JINZI", 1)) {
                // 移除金子，换银子
                return receiveObjectToBagpack(playerCharacter, unit, 100 - price);
            } else {
                return false;
            }
        }
    }

    /**
     * 玩家使用货币
     *
     * @param playerCharacter 玩家
     * @param unit            货币单位
     * @param price           价格
     */
    public static boolean castMoney2(PlayerCharacter playerCharacter, String unit, int price) {
        if (price == 0) {
            return true;
        }
        // 有足够的钱，可以付款
        if (hasMoney(playerCharacter, unit, price)) {
            return discardMoney(playerCharacter, unit, price);
        }
        return false;
    }

    /**
     * 玩家获取货币
     *
     * @param playerCharacter 玩家
     * @param unit            货币单位
     * @param number          价格
     */
    public static boolean addMoney(PlayerCharacter playerCharacter, String unit, int number) {
        // 钱不够，若果是银子不够，但还有金子
        if (unit.equals("OBJECT_YINLIANG")) {
            // 银子
            String bagpackId = playerCharacter.getBagpack();
            BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(bagpackId);
            // 银子数量
            int currentNumber = CommonItemContainerManager.getNumberByDataKey(bagpackObject, unit);
            boolean result = false;
            if (currentNumber + number < 100) {
                //小于100直接追加
                result = receiveObjectToBagpack(playerCharacter, "OBJECT_YINLIANG", number);
            } else {
                //大于100超出最大叠加
                int yinNumber = (currentNumber + number) % 100;
                int jinNumber = (currentNumber + number) / 100;
                result = receiveObjectToBagpack(playerCharacter, "OBJECT_JINZI", jinNumber);
                if (yinNumber - currentNumber > 0) {
                    result = receiveObjectToBagpack(playerCharacter, "OBJECT_YINLIANG", yinNumber - currentNumber);
                } else if (currentNumber - yinNumber != 0) {
                    result = discardObject(playerCharacter, "OBJECT_YINLIANG", currentNumber - yinNumber);
                }

            }
            return result;
        } else {
            return receiveObjectToBagpack(playerCharacter, "OBJECT_JINZI", number);
        }
    }

    /**
     * 加载默认物品
     *
     * @param playerCharacter 玩家
     */
    public static void loadDefaultObjects(PlayerCharacter playerCharacter) {
        /*
         * 初始化玩家的默认物品
         * */
        Iterable<DefaultObjects> defaultObjects = DbMapper.defaultObjectsRepository.findDefaultObjectsByTarget("player");
        for (DefaultObjects defaultObject : defaultObjects) {
            int number = defaultObject.getNumber();
            String objectKey = defaultObject.getCommonObject();
            CommonObject commonObject = CommonObjectBuilder.buildCommonObject(objectKey);
            if (commonObject != null) {
                commonObject.setOwner(playerCharacter.getId());
                // 持久化物品
                CommonObjectBuilder.save(commonObject);
                // 玩家背包接受物品
                receiveObjectToBagpack(playerCharacter, commonObject, number);
            } else {
                System.out.println("加载默认物品的时候，没有找到 : " + defaultObject.getCommonObject());
            }
        }
    }

    /**
     * 初始化玩家的背包
     *
     * @param playerCharacter 玩家对象
     */
    public static void initBagPack(PlayerCharacter playerCharacter) {
        BagpackObject bagpackObject = BagpackObjectManager.create(playerCharacter.getId());
        MongoMapper.bagpackObjectRepository.save(bagpackObject);
        playerCharacter.setBagpack(bagpackObject.getId());
        MongoMapper.playerCharacterRepository.save(playerCharacter);
    }

    /**
     * 初始化玩家的仓库
     *
     * @param playerCharacter 玩家对象
     */
    public static void initWareHouse(PlayerCharacter playerCharacter) {
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
     * @param removedObject   要移除的物品对象
     * @param number          要移除的数量
     * @return boolean 最终是否成功移除
     */
    public static boolean removeObjectsFromBagpack(PlayerCharacter playerCharacter, CommonObject removedObject, int number) {
        String bagpackId = playerCharacter.getBagpack();
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(bagpackId);
        if (CommonItemContainerManager.removeItem(bagpackObject, removedObject, number)) {
            MongoMapper.bagpackObjectRepository.save(bagpackObject);
            PlayerCharacterManager.showBagpack(playerCharacter);
//            Collection<CommonObjectInfo> values = bagpackObject.getItems().values();
//            playerCharacter.msg(new BagPackListMessage(new ArrayList<CommonObjectInfo>(values)));
            return true;
        } else {
            playerCharacter.msg(new AlertMessage(String.format(GameWords.CAN_NOT_REMOVE_FROM_BAGPACK,
                    number, removedObject.getUnitName(), removedObject.getName())));
            return false;
        }
    }

    /**
     * 从背包移除物品
     *
     * @param playerCharacter  玩家对象
     * @param removedObjectKey 要移除的物品的key
     * @param number           要移除的数量
     * @return boolean 最终是否成功移除
     */
    public static boolean removeObjectsFromBagpack(PlayerCharacter playerCharacter, String removedObjectKey, int number) {
        if (hasObject(playerCharacter, removedObjectKey, number)) {
            CommonObject removeObject = CommonObjectBuilder.findObjectByDataKeyAndOwner(removedObjectKey, playerCharacter.getId());
            return removeObjectsFromBagpack(playerCharacter, removeObject, number);
        } else {
            return false;
        }
    }

    /**
     * 往背包添加物品
     *
     * @param playerCharacter 玩家对象
     * @param removedObject   要添加的物品对象
     * @param number          要添加的数量
     * @return boolean 最终是否成功放入背包
     */
    public static boolean addObjectsToBagpack(PlayerCharacter playerCharacter, CommonObject removedObject, int number) {
        String bagpackId = playerCharacter.getBagpack();
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(bagpackId);
        if (CommonItemContainerManager.addItem(bagpackObject, removedObject, number)) {
            MongoMapper.bagpackObjectRepository.save(bagpackObject);
            Collection<CommonObjectInfo> values = bagpackObject.getItems().values();
            playerCharacter.msg(new BagPackListMessage(new ArrayList<CommonObjectInfo>(values)));
            return true;
        } else {
            playerCharacter.msg(new AlertMessage(String.format(GameWords.CAN_NOT_REMOVE_FROM_BAGPACK,
                    number, removedObject.getUnitName(), removedObject.getName())));
            return false;
        }
    }

    /**
     * 根据key销毁物品
     *
     * @param playerCharacter 玩家
     * @param objectKey       物品Key
     * @param number          数量
     * @return boolean 是否成功
     */
    public static boolean discardObject(PlayerCharacter playerCharacter, String objectKey, int number) {
        if (hasObject(playerCharacter, objectKey, number)) {
            NormalObjectObject normalObjectObject =
                    MongoMapper.normalObjectObjectRepository.
                            findNormalObjectObjectByDataKeyAndOwner(objectKey, playerCharacter.getId());
            if (removeObjectsFromBagpack(playerCharacter, normalObjectObject, number)) {
                normalObjectObject.setTotalNumber(normalObjectObject.getTotalNumber() - number);
                MongoMapper.normalObjectObjectRepository.save(normalObjectObject);
                return true;
            }
        }
        return false;
    }

    /**
     * 根据key销毁物品
     *
     * @param playerCharacter 玩家
     * @param objectKey       物品Key
     * @param number          数量
     * @return boolean 是否成功
     */
    public static boolean discardMoney(PlayerCharacter playerCharacter, String objectKey, int number) {
        NormalObjectObject normalObjectObject =
                MongoMapper.normalObjectObjectRepository.
                        findNormalObjectObjectByDataKeyAndOwner(objectKey, playerCharacter.getId());
        if (objectKey.equals("OBJECT_YINLIANG") && number > 99) {
            NormalObjectObject jinzitObject = MongoMapper.normalObjectObjectRepository.findNormalObjectObjectByDataKeyAndOwner(objectKey, playerCharacter.getId());
            int jinziNumber = (int) Math.floor(number / 100);
            int yinglangNumber = number % 100;
            if (normalObjectObject.getTotalNumber() >= yinglangNumber) {
                if (removeObjectsFromBagpack(playerCharacter, normalObjectObject, yinglangNumber)) {
                    normalObjectObject.setTotalNumber(normalObjectObject.getTotalNumber() - yinglangNumber);
                    MongoMapper.normalObjectObjectRepository.save(normalObjectObject);
                }
            } else {
                if (removeObjectsFromBagpack(playerCharacter, normalObjectObject, 100 - yinglangNumber)) {
                    normalObjectObject.setTotalNumber(normalObjectObject.getTotalNumber() - (100 - yinglangNumber));
                    jinziNumber = jinziNumber++;
                    MongoMapper.normalObjectObjectRepository.save(normalObjectObject);
                }
            }
            if (removeObjectsFromBagpack(playerCharacter, jinzitObject, jinziNumber)) {
                jinzitObject.setTotalNumber(normalObjectObject.getTotalNumber() - jinziNumber);
                MongoMapper.normalObjectObjectRepository.save(jinzitObject);
                return true;
            }
        } else {
            if (removeObjectsFromBagpack(playerCharacter, normalObjectObject, number)) {
                normalObjectObject.setTotalNumber(normalObjectObject.getTotalNumber() - number);
                MongoMapper.normalObjectObjectRepository.save(normalObjectObject);
                return true;
            }
        }
        return false;
    }


    /**
     * 检查背包是否有某种物品
     *
     * @param playerCharacter 玩家
     * @param objectKey       物品的key
     * @param number          需要的数量
     * @return boolean 检测结果
     */
    public static boolean hasObject(PlayerCharacter playerCharacter, String objectKey, int number) {
        String bagpackId = playerCharacter.getBagpack();
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(bagpackId);
        return CommonItemContainerManager.hasObjectByDataKey(bagpackObject, objectKey, number);
    }

    /**
     * 检查背包是否有某种物品
     *
     * @param playerCharacter 玩家
     * @param objectKey       物品的key
     * @param number          需要的数量
     * @return boolean 检测结果
     */
    public static boolean hasMoney(PlayerCharacter playerCharacter, String objectKey, int number) {
        String bagpackId = playerCharacter.getBagpack();
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(bagpackId);
        if (objectKey.equals("OBJECT_YINLIANG") && number > 99) {
            //TODO 将背包里的金子转换成银锭在做比较
            // 获取背包里金子的数量
            int jinziNumber = CommonItemContainerManager.getNumberByDataKey(bagpackObject, "OBJECT_JINZI");
            // 获取背包里银锭的数量
            int yinliangNumber = CommonItemContainerManager.getNumberByDataKey(bagpackObject, objectKey);
            // 将背包里金子换算成银子
            return (jinziNumber * 100 + yinliangNumber) >= number;
        } else {
            //TODO 与换算无关直接判断
            return CommonItemContainerManager.hasObjectByDataKey(bagpackObject, objectKey, number);
        }
    }

    /**
     * 原地复活
     *
     * @param playerCharacter 玩家
     */
    public static void rebornHere(PlayerCharacter playerCharacter) {
        String rebornObjectKey = ServerManager.gameSetting.getDefaultRebornObject();
        if (discardObject(playerCharacter, rebornObjectKey, 1)) {
            reborn(playerCharacter);
            //   moveTo(playerCharacter, getHome(playerCharacter).getDataKey());// 强制更新客户端地图到复活点
            showStatus(playerCharacter);
            PlayerCharacterManager.lookAround(playerCharacter);
        } else {
            playerCharacter.msg(new ToastMessage("你没有让自己原地复活的灵丹妙药！"));
        }
    }

    /**
     * 回城复活
     *
     * @param playerCharacter 玩家
     */
    public static void rebornHome(PlayerCharacter playerCharacter) {
        reborn(playerCharacter);
        playerCharacter.setHp((int) (playerCharacter.getMax_hp() * 0.3));
        WorldRoomObject homeRoom = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(getHome(playerCharacter).getDataKey());
        revealMap(playerCharacter, homeRoom, true);
        moveTo(playerCharacter, getHome(playerCharacter).getDataKey());// 强制更新客户端地图
        PlayerCharacterManager.lookAround(playerCharacter);
        showStatus(playerCharacter);
    }

    /**
     * 基本复活操作
     *
     * @param playerCharacter 玩家
     */
    public static void reborn(PlayerCharacter playerCharacter) {
        playerCharacter.setHp(playerCharacter.getMax_hp());
        playerCharacter.setName(playerCharacter.getName().replaceAll("的尸体", ""));
        GameSessionService.updateCallerType(playerCharacter.getId(), CallerType.CHARACTER);
        GameCharacterManager.saveCharacter(playerCharacter);
        playerCharacter.msg(new MsgMessage("一道白光闪过，你又活蹦乱跳了！"));
    }

    /**
     * 找到当前区域的复活点名称
     *
     * @param playerCharacter 玩家
     * @return WorldRoomObject 复活点房间
     */
    public static WorldRoomObject getHome(PlayerCharacter playerCharacter) {
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(playerCharacter.getLocation());
        WorldAreaObject area = MongoMapper.worldAreaObjectRepository.findWorldAreaObjectByDataKey(room.getLocation());
        WorldRoomObject home = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(area.getAreaHome());
        return home;
    }

    /**
     * 玩家使用道具
     *
     * @param playerCharacter 玩家角色
     * @param objectId        物品道具
     */
    public static void useObject(PlayerCharacter playerCharacter, String objectId) {
        if (MongoMapper.skillBookObjectRepository.existsById(objectId)) {
            useSkillBook(playerCharacter, objectId);
        } else if (MongoMapper.normalObjectObjectRepository.existsById(objectId)) {
            NormalObjectObject normalObjectObject = MongoMapper.normalObjectObjectRepository.findNormalObjectObjectById(objectId);
            NormalObjectObjectManager.useObject(normalObjectObject, playerCharacter);
        }
    }


    /**
     * 玩家使用技能书
     *
     * @param playerCharacter 玩家
     * @param skillBookId     技能书Id
     */
    public static void useSkillBook(PlayerCharacter playerCharacter, String skillBookId) {
        SkillBookObject skillBookObject = MongoMapper.skillBookObjectRepository.findSkillBookObjectById(skillBookId);
        // 检查技能书归属
        if (!skillBookObject.getOwner().equals(playerCharacter.getId())) {
            playerCharacter.msg(new MsgMessage(String.format(GameWords.NO_PREMISSION_USE, skillBookObject.getName())));
        } else if (!ConditionHandler.matchCondition(skillBookObject.getUseCondition(), playerCharacter)) {
            playerCharacter.msg(new MsgMessage("尚不满足研究这个物件的条件"));
        } else {
            Runnable runnable = learnSkillBySkillBook(playerCharacter, skillBookObject);
            if (runnable != null) {
                ScheduledExecutorService service = PlayerScheduleManager.createOrGetExecutorServiceForCaller(playerCharacter.getId());
                service.scheduleAtFixedRate(runnable, 2000, 3000, TimeUnit.MILLISECONDS);
            }
        }
    }

    /**
     * 增加后天属性
     *
     * @param playerCharacter 用户对象
     * @param atter           属性
     * @param value           增加值
     * @return
     */
    public static PlayerCharacter addAffterAttr(PlayerCharacter playerCharacter, String atter, int value) {
        switch (atter) {
            case "after_arm":
                playerCharacter.setAfter_arm(playerCharacter.getAfter_arm() + value);
                break;
            case "after_bone":
                playerCharacter.setAfter_bone(playerCharacter.getAfter_bone() + value);
                break;
            case "after_body":
                playerCharacter.setAfter_body(playerCharacter.getAfter_body() + value);
                break;
            case "after_smart":
                playerCharacter.setAfter_smart(playerCharacter.getAfter_smart() + value);
                break;
            case "after_looks":
                playerCharacter.setAfter_looks(playerCharacter.getAfter_looks() + value);
                break;
            case "after_lucky":
                playerCharacter.setAfter_lucky(playerCharacter.getAfter_lucky() + value);
                break;
            case "dao_xishu":
                playerCharacter.setDaoXiShu(playerCharacter.getDaoXiShu() + value);
                break;
            case "qimen_xishu":
                playerCharacter.setQiMenXishu(playerCharacter.getQiMenXishu() + value);
                break;
            case "jian_xishu":
                playerCharacter.setJianXishu(playerCharacter.getJianXishu() + value);
                break;
            case "quan_xishu":
                playerCharacter.setQuanXishu(playerCharacter.getQuanXishu() + value);
                break;
            case "neili_xishu":
                playerCharacter.setNeigongXishu(playerCharacter.getNeigongXishu() + value);
                break;
            case "du_xishu":
                playerCharacter.setDuXiShu(playerCharacter.getDuXiShu() + value);
                break;
            case "yi_xishu":
                playerCharacter.setYiXiShu(playerCharacter.getYiXiShu() + value);
                break;
            case "zaxue_xishu":
                playerCharacter.setZaXueXiShu(playerCharacter.getZaXueXiShu() + value);
                break;
            case "dushu_xishu":
                playerCharacter.setXueXiXishu(playerCharacter.getXueXiXishu() + value);
                break;
        }

        MongoMapper.playerCharacterRepository.save(playerCharacter);
        return playerCharacter;
    }

    /**
     * 用户主手是否装备指定装备
     *
     * @param caller
     * @param dataKey
     * @return
     */
    public static boolean isPositionLeftHand(PlayerCharacter caller, String dataKey) {
        Map<String, String> sourceData = caller.getEquippedEquipments();
        if (sourceData.get("POSITION_LEFT_HAND") == null || !MongoMapper.equipmentObjectRepository.findEquipmentObjectById(sourceData.get("POSITION_LEFT_HAND")).getDataKey().equals(dataKey)) {
            return false;
        }
        return true;
    }

    /**
     * 用户主手是否装备指定装备
     *
     * @param caller
     * @param dataKey
     * @return EquipmentObject  装备，若为null则没有装备指定装备
     */
    public static EquipmentObject getPositionLeftHand(PlayerCharacter caller, String dataKey) {
        Map<String, String> sourceData = caller.getEquippedEquipments();
        if (sourceData.get("POSITION_LEFT_HAND") == null) {
            return null;
        }
        EquipmentObject equipmentObject = MongoMapper.equipmentObjectRepository.findEquipmentObjectById(sourceData.get("POSITION_LEFT_HAND"));
        if (equipmentObject.getDataKey().equals(dataKey)) {
            return equipmentObject;
        }
        return null;
    }
}
