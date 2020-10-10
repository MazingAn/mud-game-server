package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.SellPawnShopObject;
import com.mud.game.structs.CommonObjectInfo;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.ObjectBindPrice;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.HashMap;
import java.util.List;


/**
 * 从当铺买回之前物品
 * <p>
 * 使用示例：
 * <pre>
 *      {
 *          "cmd" : "buy_back_pawn_shop",
 *          "args" : "" //需要买回物品的id
 *      }
 * </pre>
 */
public class BuyBackPawnShop extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public BuyBackPawnShop(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        JSONObject args = getArgs();
        Session session = getSession();
        PlayerCharacter playerCharacter = (PlayerCharacter) getCaller();
        //买回物品Id
        String objectId = args.getString("args");
        //出售记录
        SellPawnShopObject sellPawnShopObject = MongoMapper.sellPawnShopObjectRepository.findSellPawnShopObjectByOwner(playerCharacter.getId());
        if (sellPawnShopObject == null) {
            return;
        }
        CommonObjectInfo commonObjectInfoThe = new CommonObjectInfo();
        for (CommonObjectInfo commonObjectInfo : sellPawnShopObject.getCommonObjectInfoList()) {
            if (objectId.equals(commonObjectInfo.getDbref())) {
                //放入背包
                CommonObject commonObject = CommonObjectBuilder.findObjectById(commonObjectInfo.getDbref());
                PlayerCharacterManager.receiveObjectToBagpack(playerCharacter, commonObject, commonObjectInfo.getNumber());
                if (commonObjectInfo.getMax_stack() == 1) {
                    CommonObjectBuilder.save(commonObject);
                }
                //扣钱
                ObjectBindPrice objectBindPrice = DbMapper.objectBindPriceRepository.findObjectBindPriceByDataKey(commonObjectInfo.getDataKey());
                PlayerCharacterManager.castMoney(playerCharacter, "OBJECT_YINLIANG", objectBindPrice.getPrice() * commonObjectInfo.getNumber());
                //从记录表里删除这条记录
                commonObjectInfoThe = commonObjectInfo;
                //购买成功之后返回物品信息
                playerCharacter.msg(new HashMap<String, Object>() {{
                    put("buy_back_pawn_shop_back", commonObjectInfo);
                }});
            }
        }

        if (commonObjectInfoThe != null) {
            sellPawnShopObject.getCommonObjectInfoList().remove(commonObjectInfoThe);
            MongoMapper.sellPawnShopObjectRepository.save(sellPawnShopObject);
        }
    }
}
