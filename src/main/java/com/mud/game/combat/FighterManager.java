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

public class FighterManager {
    /*
    *  当一个角色进入战斗的时候，FighterManager 将执行战斗逻辑
    * */

    public static void joinCombat(CommonCharacter character) {
        /*
        * 把一个玩家放入战斗场景
        * */
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

    public static void setRandomTarget(CommonCharacter character, ArrayList<CommonCharacter> targets) {
        // 为角色随机设置对手
        CommonCharacter target = ArrayListUtils.randomChoice(targets);
        character.setTarget(target.getId());
    }

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
