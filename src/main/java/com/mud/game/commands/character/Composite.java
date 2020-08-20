package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.AlertMessage;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.manager.CommonItemContainerManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.BagpackObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.CompositeMaterial;
import com.mud.game.worlddata.db.models.supermodel.BaseCommonObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONArray;
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
 * ******  //标识 * }
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
        if (null == args) {
            playerCharacter.msg("无效的参数！");
        }
        String dataKey = args.getString("dataKey");
        JSONArray materials = args.getJSONArray("materials");
        BaseCommonObject baseCommonObject = CommonObjectBuilder.findObjectTemplateByDataKey(dataKey);
        if (null == baseCommonObject) {
            return;
        }
        //校验配方
        List<CompositeMaterial> compositeMaterialList = DbMapper.compositeMaterialRepository.findCompositeMaterialByDataKey(dataKey);
        if (compositeMaterialList == null || compositeMaterialList.size() == 0) {
            return;
        }
        //校验合成材料是否足够
        //背包信息
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(bagpackId);
        for (int i = 0; i < compositeMaterialList.size(); i++) {
            if (!CommonItemContainerManager.checkCanRemove(bagpackObject, compositeMaterialList.get(i).getDependency(), compositeMaterialList.get(i).getNumber())) {
                baseCommonObject = CommonObjectBuilder.findObjectTemplateByDataKey(compositeMaterialList.get(i).getDependency());
                playerCharacter.msg(new AlertMessage("你的{g" + baseCommonObject.getName() + "{n不够!"));
                return;
            }
        }
        //从背包移除材料
        for (int i = 0; i < materials.length(); i++) {
            if (null != materials.get(i)) {
                CommonObject removeObject = CommonObjectBuilder.findObjectById(materials.get(i).toString());
                if (null == removeObject) {
                    return;
                }
                PlayerCharacterManager.removeObjectsFromBagpack(playerCharacter, removeObject, compositeMaterialList.get(i).getNumber());
                if (removeObject.getMaxStack() == 1) {
                    CommonObjectBuilder.deleteObjectById(removeObject.getId());
                }
                //生成物品放入背包
                PlayerCharacterManager.receiveObjectToBagpack(playerCharacter, dataKey, 1);
                PlayerCharacterManager.showBagpack(playerCharacter);
            }
        }

    }
}
