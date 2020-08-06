package com.mud.game.commands.character;

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
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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

    public static void main(String[] args) throws JSONException {
        PlayerCharacter playerCharacter = new PlayerCharacter();
        playerCharacter.setId("1");
        JSONObject object = new JSONObject();
        object.put("auction_id", 8644);
        object.put("number", 1);
        new SellBuyObject(null, playerCharacter, object, null);
    }

    @Override
    public void execute() throws JSONException {
        //获取用户以及参数
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        //获取参数
        Long auctionId = args.getLong("auction_id"); //寄售id
        int number = args.getInt("number"); //购买数量
        //获取寄售信息
        ConsignmentInformation consignmentInformation = DbMapper.consignmentInfomationRepository.findConsignmentInformationById(auctionId);
        if (null == consignmentInformation) {
            getSession().sendText(JsonResponse.JsonStringResponse(new AlertMessage("此寄售信息不存在!")));
            logger.error("此寄售信息不存在！");
            return;
        }
        //TODO 交易
        // 单价
        int price = consignmentInformation.getPrice();
        // 校验 1、购买者的资产是否足够 2、物品数量是否足够
        if (consignmentInformation.getNumber() < number) {
            getSession().sendText(JsonResponse.JsonStringResponse(new AlertMessage("物品数量不够!")));
            logger.error("物品数量不够！");
            return;
        }
        // 物品信息
        CommonObject commonObject = CommonObjectBuilder.findObjectById(consignmentInformation.getObjectId());
        if (PlayerCharacterManager.castMoney(caller, consignmentInformation.getMoneyType(), number * price)) {
            //接收一件物品放入背包,减去相应金钱
            PlayerCharacterManager.receiveObjectToBagpack(caller, commonObject, number);
        } else {
            getSession().sendText(JsonResponse.JsonStringResponse(new AlertMessage("你的钱不够!")));
            logger.error("你的钱不够！");
            return;
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
        // 寄售者资产修改
        PlayerCharacter playerCharacter = MongoMapper.playerCharacterRepository.findPlayerCharacterById(consignmentInformation.getPalyerId());
        PlayerCharacterManager.addMoney(playerCharacter, consignmentInformation.getMoneyType(), number * price);
        //刷新背包
        PlayerCharacterManager.showBagpack(playerCharacter);
        //返回页面消息
       // getSession().sendText(JsonResponse.JsonStringResponse(new AlertMessage("你获得了{g" + consignmentInformation.getObjectName() + "{n!")));
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
