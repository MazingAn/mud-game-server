package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.AlertMessage;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.manager.CommonItemContainerManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.BagpackObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.CompositeMaterial;
import com.mud.game.worlddata.db.models.Equipment;
import com.mud.game.worlddata.db.models.SkillBook;
import com.mud.game.worlddata.db.models.supermodel.BaseCommonObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.List;

/**
 * 合成
 * <p>
 * {
 * cmd: "composite",   //命令
 * args: {
 * category: "TYPE_OBJECTTYPE_EQUIPMENT / TYPE_OBJECTTYPE_JINENGSHU",  //类型 区分装备和技能书
 * dataKey: maolv    //标识
 * }
 * }
 */
public class Composite extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public Composite(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        //用户信息
        PlayerCharacter playerCharacter = (PlayerCharacter) getCaller();
        String bagpackId = playerCharacter.getBagpack();
        //参数
        JSONObject args = getArgs();
        String dataKey = args.getString("dataKey");
        BaseCommonObject baseCommonObject = CommonObjectBuilder.findObjectTemplateByDataKey(dataKey);
        if (null == baseCommonObject) {
            playerCharacter.msg(new AlertMessage("此物品不存在!"));
        }
        //
        List<CompositeMaterial> compositeMaterialList = DbMapper.compositeMaterialRepository.findCompositeMaterialByDataKey(dataKey);
        if (compositeMaterialList == null || compositeMaterialList.size() == 0) {
            playerCharacter.msg(new AlertMessage("此装备配方不存在！"));
        }
        //校验合成材料是否足够
        //背包信息
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(bagpackId);
        for (int i = 0; i < compositeMaterialList.size(); i++) {
            if (!CommonItemContainerManager.checkCanRemove(bagpackObject, compositeMaterialList.get(i).getDataKey(), compositeMaterialList.get(i).getNumber())) {
                //playerCharacter.msg(new AlertMessage("你的{g" + compositeMaterialList.get(i).getObjectName() + "{n不够!"));
                break;
            }
        }
        //从背包移除材料
        for (int i = 0; i < compositeMaterialList.size(); i++) {
            PlayerCharacterManager.removeObjectsFromBagpack(playerCharacter, compositeMaterialList.get(i).getDataKey(), compositeMaterialList.get(i).getNumber());
        }
        //生成物品放入背包
        PlayerCharacterManager.receiveObjectToBagpack(playerCharacter, dataKey, 1);
        PlayerCharacterManager.showBagpack(playerCharacter);
    }
}