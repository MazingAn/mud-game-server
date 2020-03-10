package com.mud.game.combat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.Mongo;
import com.mud.game.messages.JoinCombatMessage;
import com.mud.game.messages.MsgMessage;
import com.mud.game.net.session.GameSessionService;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.structs.CharacterState;
import com.mud.game.utils.collections.ArrayListUtils;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.yeauty.pojo.Session;

import java.util.ArrayList;


/**
 * {@code FighterManager}
 * 当一个角色进入战斗的时候，FighterManager 实现了战斗中的具体操作 并把战斗结果发送给玩家
 * */

public class FighterManager {


    /**
     * 把一个玩家放入战斗状态
     * 更改角色的 {@code state}属性为 {@code CharacterState.STATE_COMBAT}  玩家属性参见 {@link CommonCharacter} <br>
     * 通知客户端 玩家进入战斗 发送进入战斗的消息  消息结构 参见 {@link JoinCombatMessage}
     *
     * @param character 参与战斗的角色
     * */
    public static void joinCombat(CommonCharacter character) {

        try{
            character.setState(CharacterState.STATE_COMBAT);
            Session session = GameSessionService.getSessionByCallerId(character.getId());
            if(session!=null){
                session.sendText(JsonResponse.JsonStringResponse(new JoinCombatMessage()));
            }
        }catch (Exception e){
            System.out.println("加入战斗失败");
        }
    }

    /**
     * 为队伍中的角色设置随机目标
     *
     * 战斗开始初期两个队伍中的成员相互随机设定一个攻击目标
     *
     * @param character 要给设置目标的角色
     * @param targets 角色可选的目标列表
     * */
    public static void setRandomTarget(CommonCharacter character, ArrayList<CommonCharacter> targets) {
        // 为角色随机设置对手
        CommonCharacter target = ArrayListUtils.randomChoice(targets);
        character.setTarget(target.getId());
    }

    /**
     * 战斗开始后，角色默认使用自动攻击的方式展开战斗
     *
     * @param character 要进行自动攻击的角色
     * */
    public static void startAutoCombat(CommonCharacter character) {
        // 绝对对自己的对手开始自动攻击

        try{
            while (true) {

                if (character.getTarget() != null) {

                    CommonCharacter target = null;

                    if(MongoMapper.worldNpcObjectRepository.existsById(character.getTarget())){
                        target = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(character.getTarget());
                    }else{
                        target = MongoMapper.playerCharacterRepository.findPlayerCharacterById(character.getTarget());
                    }

                    if(target!=null){
                        Session selfSession = GameSessionService.getSessionByCallerId(character.getId());
                        if (selfSession != null) {
                            selfSession.sendText(JsonResponse.JsonStringResponse(new MsgMessage(
                                    "你攻击了" + target.getName())));
                        }

                        Session targetSession = GameSessionService.getSessionByCallerId(character.getId());
                        if (targetSession != null) {
                            targetSession.sendText(JsonResponse.JsonStringResponse(new MsgMessage(
                                    character.getName() + "攻击了你！")));
                        }

                    }

                }
            }
        }catch (Exception e){
            System.out.println("攻击失败");
        }



    }

}
