package com.mud.game.net.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.handler.CommandSetHandler;
import com.mud.game.messages.MsgMessage;
import com.mud.game.messages.RebornCommandsMessage;
import com.mud.game.object.account.Player;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.lang.reflect.Constructor;

/**
 * 系统交互的代理类
 * 根据callerId和callerType得到对应的caller，划分其命令集合，然后执行命令
 * */
public class CallerDelegate {

    private final String callerId;

    public CallerDelegate(String callerId){
        this.callerId = callerId;
    }

    /**
     * 命令执行通道
     * @param commandKey String类型 命令的key
     * @param args JSONObject 命令参数列表
     * */
    public void executeCommand(String commandKey, JSONObject args)  {
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
                        String callerId = GameSessionService.getCallerIdBySessionId(session.id());
                        PlayerCharacter playerCharacter = MongoMapper.playerCharacterRepository.findPlayerCharacterById(callerId);
                        BaseCommand command = (BaseCommand)c.newInstance(commandKey, playerCharacter, args, session);
                    }catch ( Exception e){
                        e.printStackTrace();
                    }
                }else{
                    //session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("不能执行命令"+commandKey + "；因为没有找见")));
                    System.out.println("哎呀，出事了！好像没有找到key为" + commandKey + "的命令！请尝试实现它并记得在CommandHandlerSet里面注册！");
                }
                break;
            case DIE:
                if(CommandSetHandler.playerCharacterDieCommandSet.containsKey(commandKey)){
                    Class clazz = CommandSetHandler.playerCharacterDieCommandSet.get(commandKey);
                    try {
                        Constructor c = clazz.getConstructor(String.class, Object.class, JSONObject.class, Session.class);
                        String callerId = GameSessionService.getCallerIdBySessionId(session.id());
                        PlayerCharacter playerCharacter = MongoMapper.playerCharacterRepository.findPlayerCharacterById(callerId);
                        BaseCommand command = (BaseCommand)c.newInstance(commandKey, playerCharacter, args, session);
                    }catch ( Exception e){
                        e.printStackTrace();
                    }
                }else{
                    PlayerCharacter playerCharacter = MongoMapper.playerCharacterRepository.findPlayerCharacterById(callerId);
                    playerCharacter.msg(new MsgMessage("你已经死了！"));
                    playerCharacter.msg(new RebornCommandsMessage(playerCharacter));
                    //session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("不能执行命令"+commandKey + "；因为没有找见")));
                    System.out.println("哎呀，出事了！好像没有找到key为" + commandKey + "的命令！请尝试实现它并记得在CommandHandlerSet里面注册！");
                }
                break;
            default:
                break;
        }
    }

}
