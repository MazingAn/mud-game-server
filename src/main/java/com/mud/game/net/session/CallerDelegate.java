package com.mud.game.net.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.handler.CommandSetHandler;
import com.mud.game.object.account.Player;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.lang.reflect.Constructor;

public class CallerDelegate {
    /*
    * @系统交互的代理类
    * @根据callerId和callerType得到对应的caller，划分其命令集合，然后执行命令
    * */
    private String callerId;

    public CallerDelegate(String callerId){
        this.callerId = callerId;
    }


    public void executeCommand(String commandKey, JSONObject args) throws JsonProcessingException {
        Session session = GameSessionService.getSessionByCallerId(callerId);
        CallerType callerType = GameSessionService.getCallerTypeByCallerId(callerId);
        switch (callerType){
            case ANONYMOUS:
                if(CommandSetHandler.unLoginCommandSet.containsKey(commandKey)){
                    Class clazz = CommandSetHandler.unLoginCommandSet.get(commandKey);
                    try {
                        Constructor c = clazz.getConstructor(String.class, Object.class, JSONObject.class, Session.class);
                        c.newInstance(commandKey, null, args, session);
                    }catch ( Exception e){
                        e.printStackTrace();
                    }
                }else{
                    //session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("不能执行命令"+commandKey + "；因为没有找见")));
                }
                break;
            case ACCOUNT:
                Player player = MongoMapper.playerRepository.findPlayerById(callerId);
                if(CommandSetHandler.accountCommandSet.containsKey(commandKey)){
                    Class clazz = CommandSetHandler.accountCommandSet.get(commandKey);
                    try {
                        Constructor c = clazz.getConstructor(String.class, Object.class, JSONObject.class, Session.class);
                        BaseCommand command = (BaseCommand)c.newInstance(commandKey, null, args, session);
                    }catch ( Exception e){
                        e.printStackTrace();
                    }
                }else{
                    //session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("不能执行命令"+commandKey + "；因为没有找见")));
                }
                break;
            case CHARACTER:
                if(CommandSetHandler.playerCharacterCommandSet.containsKey(commandKey)){
                    Class clazz = CommandSetHandler.playerCharacterCommandSet.get(commandKey);
                    try {
                        Constructor c = clazz.getConstructor(String.class, Object.class, JSONObject.class, Session.class);
                        String chatId = GameSessionService.getCallerIdBySessionId(session.id());
                        PlayerCharacter playerCharacter = MongoMapper.playerCharacterRepository.findPlayerCharacterById(chatId);
                        BaseCommand command = (BaseCommand)c.newInstance(commandKey, playerCharacter, args, session);
                    }catch ( Exception e){
                        e.printStackTrace();
                    }
                }else{
                    //session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("不能执行命令"+commandKey + "；因为没有找见")));
                    System.out.println("哎呀，出事了！好像没有找到key为" + commandKey + "的命令！请尝试实现它并记得在CommandHandlerSet里面注册！");
                }
                break;
            default:
                break;
        }
    }

}
