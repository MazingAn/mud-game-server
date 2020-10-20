package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.handler.TrophyHandler;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.Set;

/**
 * 拾取房间内掉落装备
 * <p>
 * 使用示例：
 * <pre>
 *      {
 *          "cmd" : "pick_up_in_room",
 *          "args" : "dbref" //被物品主键
 *      }
 * </pre>
 */
public class PickUpInRoom extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public PickUpInRoom(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        String dbref = args.getString("dbref");
        CommonObject commonObject = CommonObjectBuilder.findObjectById(dbref);
        Set<String> stringSet = TrophyHandler.getTrophy(caller.getLocation());
        if (stringSet == null || stringSet.isEmpty() || !stringSet.contains(dbref) || commonObject == null) {
            caller.msg(new ToastMessage("此物品不存在！"));
            return;
        }

        //拾取物品
        if (PlayerCharacterManager.receiveObjectToBagpack(caller, commonObject, 1)) {
            //删除记录
            TrophyHandler.getTrophy(caller.getLocation()).remove(dbref);
            //移除前端物品
            GameCharacterManager.objectMoveOut(commonObject, caller.getLocation());
        }

    }
}