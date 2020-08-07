package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.ConsignmentInformationsMsg;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.ConsignmentInformation;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 取消寄售
 *
 * <p>
 * {
 * cmd:"sellCancel"
 * args:{
 * "auctionId":123
 * }
 * }
 */
public class SellCancel extends BaseCommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public SellCancel(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        //获取用户以及参数
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        //获取参数
        Long auctionId = args.getLong("auctionId");
        //获取寄售信息
        ConsignmentInformation consignmentInformation = DbMapper.consignmentInfomationRepository.findConsignmentInformationById(auctionId);
        if (null == consignmentInformation) {
            caller.msg("此物品不存在！");
            logger.error("此物品不存在！");
        }
        //拍卖行删除商品
        DbMapper.consignmentInfomationRepository.deleteById(auctionId);
        //获取商品信息
        CommonObject commonObject = CommonObjectBuilder.findObjectById(consignmentInformation.getObjectId());
        //物品唯一修改物品归属
        if (commonObject.isUnique()) {
            //物品唯一归属值为空
            commonObject.setOwner(caller.getId());
            CommonObjectBuilder.save(commonObject);
        }
        //物品放入玩家背包
        PlayerCharacterManager.addObjectsToBagpack(caller, commonObject,consignmentInformation.getNumber());
        PlayerCharacterManager.showBagpack(caller);
        //给客户端返回已寄售列表
        List<ConsignmentInformationsMsg> consignmentInformationsMsgList = new ArrayList<>();
        Iterable<ConsignmentInformation> informations = DbMapper.consignmentInfomationRepository.findByPalyerId(caller.getId());
        Iterator<ConsignmentInformation> consignmentInformationIterator = informations.iterator();
        while (consignmentInformationIterator.hasNext()) {
            consignmentInformationsMsgList.add(new ConsignmentInformationsMsg(caller, consignmentInformationIterator.next()));
        }
        getSession().sendText(JsonResponse.JsonStringResponse(new HashMap() {{
            put("sellPut_list", consignmentInformationsMsgList);
        }}));
    }
}
