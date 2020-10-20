package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * 和npc切磋
 * <p>
 * 使用示例：
 * <pre>
 *      {
 *          "cmd" : "attack_test_npc",
 *          "args" : "dbref" //npc主键
 *      }
 * </pre>
 */
public class AttackTestNpc extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public AttackTestNpc(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        String dbref = args.getString("args");
        WorldNpcObject worldNpcObject = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(dbref);
        List<CommonCharacter> commonCharacterList = new ArrayList<>();
        commonCharacterList.add(worldNpcObject);
        PlayerCharacterManager.getAttack(caller, commonCharacterList, -1);
    }
}
