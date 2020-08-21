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
import org.json.JSONArray;
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
 * args:{
 * // 价格
 * //货币类型参数为两种，选择不同的货币类型则价格包含的对象也不一样：金子和金叶子，金子包含金子和银两，金叶子则只有金叶子
 * buyout_price: [
 * {"OBJECT_JINZI":1},{"OBJECT_YINLIANG":1} || {"OBJECT_JINYEZI":1}
 * ],
 * // 物品ID
 * sell_object: dbref,
 * // 数量
 * number: this.goodsNumber,
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
        //物品id
        String sell_object = args.getString("sell_object");
        //数量
        int number = args.getInt("number");
        JSONArray buyoutPrice = args.getJSONArray("buyout_price");
        if (null == buyoutPrice) {
            caller.msg(new AlertMessage("价格不能为空！"));
            return;
        }
        // 物品信息
        CommonObject commonObject = CommonObjectBuilder.findObjectById(sell_object);
        if (number <= 0 || number > commonObject.getTotalNumber()) {
            caller.msg(new AlertMessage("上架物品失败！"));
            return;
        }
        //价格校验
        List<String> priceKeyList = new ArrayList<>();
        JSONObject jsonObject = null;
        Boolean priceZero = true;
        for (int i = 0; i < buyoutPrice.length(); i++) {
            jsonObject = buyoutPrice.getJSONObject(i);
            Iterator it = jsonObject.keys();
            while (it.hasNext()) {
                String key = String.valueOf(it.next().toString());
                priceKeyList.add(key);
                if (!"0".equals(jsonObject.get(key).toString().trim())) {
                    priceZero = false;
                }
            }
        }
        if (priceZero) {
            caller.msg(new AlertMessage("价格不能为0！"));
            return;
        }
        if (priceKeyList.contains("OBJECT_JINYEZI") && priceKeyList.size() > 1) {
            caller.msg(new AlertMessage("金叶子不能和金子银两混用！"));
            return;
        }

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
        JSONArray buyoutPrice = args.getJSONArray("buyout_price");
        int number = args.getInt("number");
        consignmentInformation.setPalyerId(caller.getId());
        consignmentInformation.setPalyerName(caller.getName());
        consignmentInformation.setObjectId(sell_object);
        consignmentInformation.setObjectName(commonObject.getName());
        consignmentInformation.setQuality(commonObject.getQuality());
        consignmentInformation.setObjectName(commonObject.getName());
        consignmentInformation.setDescription(commonObject.getDescription());
        consignmentInformation.setPrice(buyoutPrice.toString());
        consignmentInformation.setNumber(number);
        consignmentInformation.setCategory(commonObject.getCategory());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        consignmentInformation.setCreateTime(df.format(new Date()));
        return consignmentInformation;
    }
}
