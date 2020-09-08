package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.builder.MailObjectBuilder;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.MailObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.models.supermodel.BaseCommonObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * 发送邮件
 * <pre>
 *    {
 *        "cmd": "send_mail",
 *        "args": {
 *           "dbref": "id", //接收人id
 *           "content": "content", //邮件内容
 *           "attachment": "id/数量" //附件
 *        }
 *    }
 *  </pre>
 */
public class SendMail extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public SendMail(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
//        // 接收人id
//        String targetId = args.getString("dbref");
//        //邮件内容
//        String content = args.getString("content");
//        //附件信息
//        String dataKey = args.getString("dataKey");
        String targetId = "5f45d0dee3347a0a9299d5a2";
        String content = "送你一把长剑";
        String dataKey = "changjian";
        int number = 1;
        //查询接收者信息
        PlayerCharacter target = MongoMapper.playerCharacterRepository.findPlayerCharacterById(targetId);
        if (target == null) {
            caller.msg(new ToastMessage("邮件发送失败!"));
            caller.msg("邮件发送失败!");
            return;
        }
        List<CommonObject> commonObjectList = new ArrayList<>();
        //commonObjectList.add(CommonObjectBuilder.findObjectTemplateByDataKey(dataKey));
        //持久化邮件信息
        MailObjectBuilder.save(caller, target, content, commonObjectList, number);
    }
}
