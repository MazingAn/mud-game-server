package com.mud.game.commands.character;

import com.alibaba.fastjson.JSONArray;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.AlertMessage;
import com.mud.game.messages.ConsignmentInformationsMsg;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.ConsignmentInformation;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yeauty.pojo.Session;

import java.util.*;

/**
 * 购买寄售商品
 * <p>
 * {cmd:"buyout"
 * args:{
 * auction_id :"8644",  寄售数据主键
 * number:1
 * }
 * }
 * </p>
 */
public class SellBuyObject extends BaseCommand {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //收取10%佣金，完成交易上架者获取金钱的系数
    private static final double COMMISSION_DEDUCTION = 0.9;

    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public SellBuyObject(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        //获取用户以及参数
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        //获取参数
        Long auctionId = args.getLong("auction_id"); //寄售id
        int number = args.getInt("number"); //购买数量
        if (number <= 0) {
            caller.msg(new AlertMessage("购买物品数量必须大于0！"));
            return;
        }
        //获取寄售信息
        ConsignmentInformation consignmentInformation = DbMapper.consignmentInfomationRepository.findConsignmentInformationById(auctionId);
        if (null == consignmentInformation) {
            getSession().sendText(JsonResponse.JsonStringResponse(new AlertMessage("此寄售信息不存在!")));
            logger.error("此寄售信息不存在！");
            return;
        }
        //TODO 交易
        // 单价
        String price = consignmentInformation.getPrice();
        if (StringUtils.isEmpty(price)) {
            caller.msg(new AlertMessage("购买失败！"));
            return;
        }
        // 寄售者资产修改
        PlayerCharacter playerCharacter = MongoMapper.playerCharacterRepository.findPlayerCharacterById(consignmentInformation.getPalyerId());
        JSONArray jsonArray = JSONArray.parseArray(price);
        // 校验 1、购买者的资产是否足够 2、物品数量是否足够
        if (consignmentInformation.getNumber() < number) {
            getSession().sendText(JsonResponse.JsonStringResponse(new AlertMessage("物品数量不够!")));
            logger.error("物品数量不够！");
            return;
        }
        // 物品信息
        CommonObject commonObject = CommonObjectBuilder.findObjectById(consignmentInformation.getObjectId());
        //TODO  判断金银或者金叶子，金叶子直接移除，
        if (jsonArray.size() == 1) {
            //金叶子
            com.alibaba.fastjson.JSONObject jsonObject = jsonArray.getJSONObject(0);
            int jinyeziNum = jsonObject.getInteger("OBJECT_JINYEZI");
            if (PlayerCharacterManager.castMoney(caller, "OBJECT_JINYEZI", jinyeziNum)) {
                //接收一件物品放入背包,减去相应金钱
                PlayerCharacterManager.receiveObjectToBagpack(caller, commonObject, number);
                PlayerCharacterManager.addMoney(playerCharacter, "OBJECT_JINYEZI", (int) (number * jinyeziNum * COMMISSION_DEDUCTION));
            } else {
                getSession().sendText(JsonResponse.JsonStringResponse(new AlertMessage("你的钱不够!")));
                logger.error("你的金叶子不够！");
                return;
            }
        } else {
            Map<String, Integer> map = new HashMap<>();
            com.alibaba.fastjson.JSONObject jsonObject = null;
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                    map.put(entry.getKey(), Integer.parseInt(entry.getValue().toString()));
                }
            }
            //校验
            Boolean moneyEnough = true;
            if (PlayerCharacterManager.hasObject(caller, "OBJECT_YINLIANG", map.get("OBJECT_YINLIANG"))) {
                if (!PlayerCharacterManager.hasObject(caller, "OBJECT_JINZI", map.get("OBJECT_JINZI"))) {
                    moneyEnough = false;
                }
            } else {
                if (!PlayerCharacterManager.hasObject(caller, "OBJECT_JINZI", map.get("OBJECT_JINZI") + 1)) {
                    moneyEnough = false;
                }
            }
            if (moneyEnough) {
                //减去对应的钱
                if (map.get("OBJECT_YINLIANG") > 0) {
                    PlayerCharacterManager.castMoney(caller, "OBJECT_YINLIANG", number * map.get("OBJECT_YINLIANG"));
                    PlayerCharacterManager.addMoney(playerCharacter, "OBJECT_YINLIANG", (int) (number * map.get("OBJECT_YINLIANG") * COMMISSION_DEDUCTION));
                }
                if (map.get("OBJECT_JINZI") > 0) {
                    PlayerCharacterManager.castMoney(caller, "OBJECT_JINZI", number * map.get("OBJECT_JINZI"));
                    PlayerCharacterManager.addMoney(playerCharacter, "OBJECT_JINZI", (int) (number * map.get("OBJECT_JINZI") * COMMISSION_DEDUCTION));
                }

                PlayerCharacterManager.receiveObjectToBagpack(caller, commonObject, number);
            } else {
                caller.msg(new AlertMessage("你的钱不够！"));
            }

        }
        //物品唯一修改物品归属
        if (commonObject.isUnique()) {
            commonObject.setOwner(caller.getId());
            CommonObjectBuilder.save(commonObject);
        }
        // 交易成功减去物品相应数量/数量为0直接删除寄售信息
        if (consignmentInformation.getNumber() - number == 0) {
            DbMapper.consignmentInfomationRepository.deleteById(auctionId);
        } else {
            consignmentInformation.setNumber(consignmentInformation.getNumber() - number);
            DbMapper.consignmentInfomationRepository.save(consignmentInformation);
        }
        //刷新背包
        PlayerCharacterManager.showBagpack(playerCharacter);
        //给客户端返回新的寄售列表
        List<ConsignmentInformationsMsg> consignmentInformationsMsgList = new ArrayList<>();
        Iterable<ConsignmentInformation> informations = DbMapper.consignmentInfomationRepository.findAll();
        Iterator<ConsignmentInformation> consignmentInformationIterator = informations.iterator();
        while (consignmentInformationIterator.hasNext()) {
            consignmentInformationsMsgList.add(new ConsignmentInformationsMsg(caller, consignmentInformationIterator.next()));
        }
        getSession().sendText(JsonResponse.JsonStringResponse(new HashMap() {{
            put("auction_list", consignmentInformationsMsgList);
        }}));
    }
}
