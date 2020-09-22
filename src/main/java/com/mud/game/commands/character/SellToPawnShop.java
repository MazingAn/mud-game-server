package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.BagpackObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.SellPawnShopObject;
import com.mud.game.structs.CommonObjectInfo;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.ObjectBindPrice;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static com.mud.game.constant.Constant.SALE_RECORD_NUM;

/**
 * 打开当铺
 * <p>
 * 使用示例：
 * <pre>
 *      {
 *          "cmd" : "sell_to_pawn_shop",
 *          "args" : ""
 *      }
 * </pre>
 */

public class SellToPawnShop extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public SellToPawnShop(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Transactional
    @Override
    public void execute() throws JSONException {
        JSONObject args = getArgs();
        Session session = getSession();
        PlayerCharacter playerCharacter = (PlayerCharacter) getCaller();
        //背包格子Id
        String itemId = args.getString("args");
        //移除背包格子
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(playerCharacter.getBagpack());
        Map<String, CommonObjectInfo> map = bagpackObject.getItems();
        if (map.containsKey(itemId)) {
            CommonObjectInfo commonObjectInfo = map.get(itemId);
            ObjectBindPrice objectBindPrice = DbMapper.objectBindPriceRepository.findObjectBindPriceByDataKey(commonObjectInfo.getDataKey());
            if (objectBindPrice != null) {
                //从背包移除物品
                //将唯一物品设置为无归属
                CommonObject commonObject = CommonObjectBuilder.findObjectById(commonObjectInfo.getDbref());
                if (commonObject != null) {
                    //出售记录
                    SellPawnShopObject sellPawnShopObject = MongoMapper.sellPawnShopObjectRepository.findSellPawnShopObjectByOwner(playerCharacter.getId());
                    if (null == sellPawnShopObject) {
                        sellPawnShopObject = new SellPawnShopObject();
                        sellPawnShopObject.setOwner(playerCharacter.getId());
                        sellPawnShopObject.setCommonObjectInfoList(new ArrayList<CommonObjectInfo>());
                    }
                    if (sellPawnShopObject.getCommonObjectInfoList().size() == SALE_RECORD_NUM) {
                        sellPawnShopObject.getCommonObjectInfoList().remove(sellPawnShopObject.getCommonObjectInfoList().size() - 1);
                    }
                    Collections.reverse(sellPawnShopObject.getCommonObjectInfoList());
                    sellPawnShopObject.getCommonObjectInfoList().add(commonObjectInfo);
                    Collections.reverse(sellPawnShopObject.getCommonObjectInfoList());
                    MongoMapper.sellPawnShopObjectRepository.save(sellPawnShopObject);

                    if (commonObject.getMaxStack() == 1) {
                        CommonObjectBuilder.deleteObjectById(commonObjectInfo.getDbref());
                        commonObject.setOwner(null);
                        CommonObjectBuilder.save(commonObject);
                    }
                }
                map.remove(itemId);
                //todo 增加钱数
                MongoMapper.bagpackObjectRepository.save(bagpackObject);
                PlayerCharacterManager.addMoney(playerCharacter, "OBJECT_YINLIANG", objectBindPrice.getPrice() * commonObjectInfo.getNumber());
                PlayerCharacterManager.showBagpack(playerCharacter);
            } else {
                playerCharacter.msg("出售失败！");
            }
        } else {
            playerCharacter.msg("出售失败！");
        }
    }
}
