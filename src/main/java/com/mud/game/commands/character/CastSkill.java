package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.resultutils.GameWords;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.Date;

import static com.mud.game.handler.SkillCdHandler.skillCdMap;

/**
 * 玩家执行技能
 * <pre>
 *  {
 *      "cmd":"castskill",
 *      "args":
 *      {
 *          "skill": "技能key",
 *          "target":"目标ID",
 *          "combat": true
 *      }
 *  }"
 *
 * </pre>
 */
public class CastSkill extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public CastSkill(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        String skillKey = args.getString("skill");
        String targetId = args.getString("target");
        boolean combat = args.getBoolean("combat");
        CommonCharacter target = GameCharacterManager.getCharacterObject(targetId);
        SkillObject skillObject = GameCharacterManager.getCharacterSkillByDataKey(caller, skillKey);
        //TODO 先放在map里，之后集成redis处理技能cd问题
        if (skillCdMap.containsKey(caller.getId() + skillKey) && calLastedTime(skillCdMap.get(caller.getId() + skillKey)) < skillObject.getCd()) {
            getSession().sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format("技能" + skillObject.getName() + "正在冷却！"))));
        } else {
            GameCharacterManager.castSkill(caller, target, skillObject, false);
        }
    }

    //获取技能施放时间
    public static int calLastedTime(Date startDate) {
        long a = new Date().getTime();
        long b = startDate.getTime();
        int c = (int) ((a - b) / 1000);
        return c;
    }
}
