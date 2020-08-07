package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.AlertMessage;
import com.mud.game.messages.ConsignmentInformationsMsg;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.EquipmentObject;
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

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 寄售
 * <p>
 * {
 * cmd: "add_auction",   //命令
 * args: {
 * sell_object: "sell_object",  //物品id
 * buyout_price: "8", //价格
 * moneyType: "OBJECT_JINZI",   //金币单位
 * number: 1
 * }
 * }
 */
public class SellObjects extends BaseCommand {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public SellObjects(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        String sell_object = args.getString("sell_object");
        int buyout_price = args.getInt("buyout_price");
        if (buyout_price <= 0) {
            caller.msg(new AlertMessage("金币必须大于0！"));
            return;
        }
        // 物品信息
        CommonObject commonObject = CommonObjectBuilder.findObjectById(sell_object);
        if (null == commonObject) {
            caller.msg("此物品不存在！");
            logger.error("此物品不存在！");
        }
        //判断是否为装备
        EquipmentObject equipmentObject = MongoMapper.equipmentObjectRepository.findEquipmentObjectById(sell_object);
        if (null != equipmentObject && equipmentObject.isEquipped()) {
            caller.msg("此物品已被装备不能寄售！");
            logger.error("此物品已被装备不能寄售！");
        }
        //保存寄售信息
        ConsignmentInformation consignmentInformation = buildConsignmentInformation(commonObject, args, sell_object, caller);
        DbMapper.consignmentInfomationRepository.save(consignmentInformation);
        if (commonObject.isUnique()) {
            //物品唯一归属值为空
            commonObject.setOwner(null);
            CommonObjectBuilder.save(commonObject);
        }
        //删除背包内的已上架商品
        PlayerCharacterManager.removeObjectsFromBagpack(caller, commonObject, args.getInt("number"));
        PlayerCharacterManager.showBagpack(caller);
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

    private ConsignmentInformation buildConsignmentInformation(CommonObject commonObject, JSONObject args, String sell_object, PlayerCharacter caller) throws JSONException {
        ConsignmentInformation consignmentInformation = new ConsignmentInformation();
        int buyout_price = args.getInt("buyout_price");
        int number = args.getInt("number");
        String moneyType = args.getString("moneyType");
        consignmentInformation.setPalyerId(caller.getId());
        consignmentInformation.setObjectId(sell_object);
        consignmentInformation.setObjectName(commonObject.getName());
        consignmentInformation.setQuality(commonObject.getQuality());
        consignmentInformation.setObjectName(commonObject.getName());
        consignmentInformation.setDescription(commonObject.getDescription());
        consignmentInformation.setPrice(buyout_price);
        consignmentInformation.setNumber(number);
        consignmentInformation.setCategory(commonObject.getCategory());
        consignmentInformation.setMoneyType(moneyType);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        consignmentInformation.setCreateTime(df.format(new Date()));
        return consignmentInformation;
    }
}
