package com.mud.game.object.manager;

import com.mongodb.Mongo;
import com.mud.game.handler.ConditionHandler;
import com.mud.game.messages.MsgMessage;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.*;
import com.mud.game.structs.*;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.EventData;
import com.mud.game.worlddata.db.models.LootList;
import com.mud.game.worlddata.db.models.WorldObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.yeauty.pojo.Session;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WorldObjectCreatorManager {

    /**
     * 从模板创建物品生成器
     * @param template 物品生成器模板
     * */
    public static WorldObjectCreator build(WorldObject template){
        WorldObjectCreator obj = new WorldObjectCreator();
        obj.setDataKey(template.getDataKey());
        obj.setDescription(template.getDescription());
        obj.setName(template.getName());
        obj.setIcon(template.getIcon());
        obj.setLocation(template.getLocation());
        obj.setActionName(template.getActionName());
        obj.setLootNumber(template.getLootNumber());
        obj.setLootOnce(template.isLootOnce());
        obj.setRefreshTime(template.getRefreshTime());
        obj.setShowCondition(template.getShowCondition());
        bindLootList(obj);
        bindEvents(obj);
        obj.setCoolDown(true);
        // 把物品生成器放到房间内
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(obj.getLocation());
        WorldRoomObjectManager.updateCreators(room, obj);
        return obj;
    }

    /**
     * 从模板更新物品生成器
     * @param obj  要更新的对象
     * @param template 更新模板
     * */
    public static void update(WorldObjectCreator obj, WorldObject template){
        obj.setDataKey(template.getDataKey());
        obj.setDescription(template.getDescription());
        obj.setName(template.getName());
        obj.setIcon(template.getIcon());
        obj.setLocation(template.getLocation());
        obj.setActionName(template.getActionName());
        obj.setLootNumber(template.getLootNumber());
        obj.setLootOnce(template.isLootOnce());
        obj.setRefreshTime(template.getRefreshTime());
        obj.setShowCondition(template.getShowCondition());
        bindLootList(obj);
        bindEvents(obj);
        obj.setCoolDown(true);
        // 把物品生成器放到房间内
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(obj.getLocation());
        WorldRoomObjectManager.updateCreators(room, obj);
    }

    /**
     * 绑定世界物体对应的事件
     * @param obj 世界物体
     * */
    private static void bindEvents(WorldObjectCreator obj){
        /*
         * 绑定世界物体对应的事件
         * */
        Iterable<EventData> eventData = DbMapper.eventDataRepository.findEventDataByTriggerObject(obj.getDataKey());
        Set<String> events = new HashSet<>();
        for(EventData event : eventData){
            events.add(event.getDataKey());
        }
        obj.setEvents(events);
    }

    /**
     * 玩家查看世界物体时的回调
     * @param obj  世界物体
     * @param playerCharacter  玩家
     * @param session  玩家的通信隧道
     * */
    public static void onPlayerLook(WorldObjectCreator obj, PlayerCharacter playerCharacter, Session session)  {
        /*
         * @ 当玩家查看游戏世界内的物体生成器的时候返回物体信息和可执行的命令（操作）
         * */
        Map<String, Object> lookMessage = new HashMap<>();
        GameObjectAppearance appearance = new GameObjectAppearance(obj);
        // 设置玩家可以对此物体执行的命令
        appearance.setCmds(getAvailableCommands(obj, playerCharacter));
        lookMessage.put("look_obj", appearance);
        session.sendText(JsonResponse.JsonStringResponse(lookMessage));
    }

    /**
     * 返回物品生成器对玩家支持的命令
     * @param obj 物品生成器
     * @param playerCharacter 玩家
     * @return List<EmbeddedCommand> 命令列表
     * */
    private static List<EmbeddedCommand> getAvailableCommands(WorldObjectCreator obj, PlayerCharacter playerCharacter){
        /*
         * @ 获取物体生成器的可操作命令
         * @ 对于物品生成器来说 只有一个生成命令
         * @ 返回的命令列表只包含loot命令以及配套参数，玩家执行loot命令之后才进行物品的获取
         * */
        List<EmbeddedCommand> cmds = new ArrayList<>();
        cmds.add(new EmbeddedCommand(obj.getActionName(), "loot", obj.getId()));
        return cmds;
    }

    /**
     * 绑定物品生成器可生成的物品列表
     * @param obj 物品生成器
     * */
    private static void bindLootList(WorldObjectCreator obj){
        /*
        * @ 设置物品生成器可以生成的物品列表记录
        * */
        Iterable<LootList> records = DbMapper.lootListRepository.getLootListsByProvider(obj.getDataKey());
        Set<LootList> lootLists = new HashSet<>();
        for(LootList loot : records){
            lootLists.add(loot);
        }
        obj.setLootLists(lootLists);
    }

    /**
     * 玩家触发物品生成器时候的回调
     * @param creator  物品生成器
     * @param playerCharacter  玩家
     * */
    public static void onPlayerLoot(WorldObjectCreator creator, PlayerCharacter playerCharacter)  {
        /*
        * @ 玩家从物品生成器获得物品
        * */
        Set<LootList> lootLists = creator.getLootLists();
        if(creator.isCoolDown()){
            for(LootList lootList : lootLists) {
                // 检查是否满足获取条件
                if (ConditionHandler.matchCondition(lootList.getLootCondition(), playerCharacter)){
                    // 检查是否有任务依赖
                    if(lootList.getDependQuest() == null || lootList.getDependQuest().equals("")){
                        Random random = new Random();
                        // 检查获取概率
                        if(random.nextFloat() < lootList.getOdds()){
                            PlayerCharacterManager.receiveObjectToBagpack(playerCharacter, lootList.getObject(), lootList.getNumber());
                        }
                    }
                    // 如果有任务依赖 则任务必须在进行中才能获取物品
                    else if(GameQuestManager.isQuestInProgress(playerCharacter,lootList.getDependQuest())){
                        PlayerCharacterManager.receiveObjectToBagpack(playerCharacter, lootList.getObject(), lootList.getNumber());
                    }
                }
            }
            // loot完毕之后设置冷却
            creator.setCoolDown(false);
            MongoMapper.worldObjectCreatorRepository.save(creator);
            // 如果是一次性的 设置showCondition永远不显示
            if(creator.isLootOnce()){
                creator.setShowCondition("never_unlock()");
                MongoMapper.worldObjectCreatorRepository.save(creator);
                //发送物体离开信息
                SimpleObject simpleObjet = new SimpleObject(creator);
                ObjectMoveInfo objectMoveInfo = new ObjectMoveInfo("things", Arrays.asList(new SimpleObject[]{simpleObjet}));
                WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(creator.getLocation());
                Map<String, Object> roomMessage = new HashMap<String, Object>();
                roomMessage.put("obj_moved_out", objectMoveInfo.getInfo());
                WorldRoomObjectManager.broadcast(room, roomMessage, creator.getId());
            }else{ //否则，定义定时器 在冷却时间刷新后解锁冷却
                ScheduledExecutorService service = PlayerScheduleManager.createOrGetExecutorServiceForCaller(creator.getId());
                service.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        creator.setCoolDown(true);
                        MongoMapper.worldObjectCreatorRepository.save(creator);
                        PlayerScheduleManager.shutdownExecutorByCallerId(creator.getId());
                    }
                }, (int)creator.getRefreshTime() * 1000, 1000000000, TimeUnit.MILLISECONDS);
            }
        }else {
            playerCharacter.msg(new MsgMessage("这里面{r空空如也{n！"));
        }
    }

    /**
     * 物品生成器创建物品
     * */
    public static CommonObject createObject(String objectKey){
        return CommonObjectBuilder.buildCommonObject(objectKey);
    }

}
