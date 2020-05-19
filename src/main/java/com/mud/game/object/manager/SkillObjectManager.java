package com.mud.game.object.manager;

import com.mud.game.handler.SkillFunctionHandler;
import com.mud.game.handler.SkillPositionHandler;
import com.mud.game.messages.MsgMessage;
import com.mud.game.messages.ToastMessage;
import com.mud.game.algorithm.CommonAlgorithm;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.structs.CharacterState;
import com.mud.game.structs.EmbeddedCommand;
import com.mud.game.structs.SkillEffect;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.jsonutils.JsonStrConvetor;
import com.mud.game.utils.resultutils.GameWords;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Skill;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.yeauty.pojo.Session;

import java.util.*;

public class SkillObjectManager {

    public static SkillObject create(String skillTemplateKey)  {
        SkillObject skillObject = new SkillObject();
        try {
            Skill template = DbMapper.skillRepository.findSkillByDataKey(skillTemplateKey);
            skillObject.setName(template.getName());
            skillObject.setDataKey(template.getDataKey());
            skillObject.setActionTime(template.getActionTime());
            skillObject.setCd(template.getCd());
            skillObject.setActionTime(template.getActionTime());
            skillObject.setExpandMp(template.getExpandMp());
            skillObject.setLevel(0);
            skillObject.setCurrentPotentialBalance(0);
            skillObject.setChargedPotential(0);
            skillObject.setEffects(new HashSet<>());
            skillObject.setMessage(template.getMessage());
            skillObject.setCategoryType(template.getCategoryType());
            skillObject.setFunctionType(template.getFunctionType());
            skillObject.setWeaponType(JsonStrConvetor.ToSet(template.getWeaponType()));
            skillObject.setPositions(JsonStrConvetor.ToSet(template.getPositions()));
            skillObject.setEquippedPositions(new HashSet<>());
            skillObject.setPassive(template.isPassive());
            skillObject.setIcon(template.getIcon());
            skillObject.setSkillFunction(template.getSkillFunction());
            skillObject.setOwner(null);
            skillObject.setBasicSkill(template.getBasicSkill());
        }catch (Exception e){
            System.out.println(skillObject.getDataKey() + "创建的时候出现异常");
            e.printStackTrace();
        }
        return skillObject;
    }

    public static void calculusEffects(CommonCharacter caller, CommonCharacter target, SkillObject skillObject){
        /*
        * 计算技能的效果
        * */
        SkillFunctionHandler.useSkill(caller, target, skillObject);
    }

    public static void bindSubSkills(SkillObject skillObject, String ownerId, int level) {
        /*
        * 设置技能的子技能
        * */
        Set<String> subSkillIds = new HashSet<>();
        try{
            Skill template = DbMapper.skillRepository.findSkillByDataKey(skillObject.getDataKey());
            if(template.getSubSkills() != null && !template.getSubSkills().trim().equals("")){
                Set<String> subSkillKeys = JsonStrConvetor.ToSet(template.getSubSkills());
                for(String skillKey :  subSkillKeys){
                    SkillObject subSkillObject = create(skillKey);
                    subSkillObject.setOwner(ownerId);
                    subSkillObject.setLevel(level);
                    subSkillIds.add(subSkillObject.getId());
                    MongoMapper.skillObjectRepository.save(subSkillObject);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        skillObject.setSubSKills(subSkillIds);
    }

    /**
     * 装备技能到角色身上
     * @param skillObject 要装备的技能
     * @param character 装备技能的角色
     * @param position 技能装备的位置
     * @param session 通信通道
     *
     * */
    public static void equipTo(SkillObject skillObject, CommonCharacter character, String position, Session session)  {
        /*
        * @ 装备技能到角色身上（仅限于被动技能）
        * */
        String ownerId = skillObject.getOwner();
        if(!ownerId.equals(character.getId())){
            // 检查是否拥有这个技能
            character.msg(new MsgMessage("你没有这个技能！"));
        }
        else if(!character.getState().equals(CharacterState.STATE_NORMAL)){
            character.msg(new ToastMessage("你正在学习，无法装备技能"));
        }
        else if(skillObject.getEquippedPositions().contains(position)){
            // 检查是否已将装备了这个技能
            if(session!=null) session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("你已经装备了这个技能！")));
        }else if(!checkWeaponWhenEquipSkill(skillObject, character, position, session)){
            // 检查装备的位置和技能要求的武器是否匹配
            if(session!=null) session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("当前武器无法匹配此武学！")));
        }else{// 装备技能
            // 获得角色的以装备技能列表
            Map<String, Set<String>> equippedSkills = character.getEquippedSkills();
            // 如果不存在则初始化一个
            if(equippedSkills == null) equippedSkills = new HashMap<>();
            // 获得玩家当前位置已经装备的技能并全部卸掉
            if(equippedSkills.containsKey(position)) {
                for (String skillId : equippedSkills.get(position)) {
                    // 这里必须手动卸掉技能，不能使用take_off方法，因为使用take_off会造成CurrentModification的异常
                    SkillObject oldSkill = MongoMapper.skillObjectRepository.findSkillObjectById(skillId);
                    if(oldSkill != null){
                        undoSkill(oldSkill, character, position);
                        oldSkill.getEquippedPositions().remove(position);
                        MongoMapper.skillObjectRepository.save(oldSkill);
                    }
                }
            }

            // 取消所有技能的装备效果之后，设置当前位置的技能为空
            equippedSkills.put(position, new HashSet<>());
            // 针对这个位置创建一个空的 以装备技能的Set
            Set<String> positionSkills = new HashSet<>();

            // 应用技能的父技能(对应的基本技能)
            SkillObject basicSkillObject = findBasicSkill(skillObject, character, position);
            if( basicSkillObject != null) {
                // 装备基本技能
                positionSkills.add(basicSkillObject.getId());
                // 持久化技能信息
                basicSkillObject.getEquippedPositions().add(position);
                MongoMapper.skillObjectRepository.save(basicSkillObject);
                // 执行技能效果
                castSkill(basicSkillObject, character, position);
            }

            // 装备技能本身
            positionSkills.add(skillObject.getId());
            // 更新角色技能表
            equippedSkills.put(position, positionSkills);
            // 持久化技能信息
            skillObject.getEquippedPositions().add(position);
            MongoMapper.skillObjectRepository.save(skillObject);
            // 执行技能
            castSkill(skillObject, character, position);
            saveCharacterEquippedSkill(character, position, session, equippedSkills);
        }
    }

    /**
     * 卸掉技能
     * @param skillObject 要卸掉的技能对象
     * @param character 要卸掉技能的角色
     * @param position 要卸掉技能的位置
     * @param session 通信通道
     * */
    public static void takeOff(SkillObject skillObject, CommonCharacter character, String position, Session session)  {
        Set<String> skillEquippedPositions =  skillObject.getEquippedPositions();
        // 先判断这个技能是否被装备了
        if(!skillEquippedPositions.contains(position)){
            // 并没有装备，则提示无法卸掉
            if(session!=null) session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(GameWords.NOT_EQUIP_SKILL)));
        }else if(!character.getState().equals(CharacterState.STATE_NORMAL)){
            character.msg(new ToastMessage("你正在学习，无法卸掉技能"));
        }else{
            // 获得角色的技能装备列表
            Map<String, Set<String>> equippedSkills = character.getEquippedSkills();
            // 获取技能的装备信息
            if(equippedSkills != null && equippedSkills.containsKey(position)){
                if(equippedSkills.get(position).contains(skillObject.getId())){
                    // 如果这个技能是基本技能，则先卸掉所有该位置的技能
                    if(skillObject.getCategoryType().equals("SCT_JIBEN")){
                        for(String skillId :equippedSkills.get(position)){
                            SkillObject willTakeOffSkillObject = MongoMapper.skillObjectRepository.findSkillObjectById(skillId);
                            undoSkill(willTakeOffSkillObject, character, position);
                            // 持久化技能信息
                            willTakeOffSkillObject.getEquippedPositions().remove(position);
                            MongoMapper.skillObjectRepository.save(willTakeOffSkillObject);
                        }
                        // 设置角色的该位置技能列表为空
                        equippedSkills.put(position, new HashSet<>());
                    }else{
                        // 撤销这个技能子玩家身上的效果
                        undoSkill(skillObject, character, position);
                        // 持久化技能信息
                        skillObject.getEquippedPositions().remove(position);
                        MongoMapper.skillObjectRepository.save(skillObject);
                        // 从玩家技能装备列表删除这个技能
                        equippedSkills.get(position).remove(skillObject.getId());
                    }
                    // 持久化角色的技能装备列表
                    saveCharacterEquippedSkill(character, position, session, equippedSkills);
                }
            }
        }

    }

    private static void saveCharacterEquippedSkill(CommonCharacter character, String position, Session session, Map<String, Set<String>> equippedSkills)  {
        /*
        * 技能变更之后要保持技能的equippedposition和角色的equippedSkills两个数据结构保持一致
        * 并把这些信息持久化
        * */
        character.setEquippedSkills(equippedSkills);
        if(session == null){
            // session 为 null 则为npc， 持久化npc信息
            MongoMapper.worldNpcObjectRepository.save((WorldNpcObject) character);
        }else{
            MongoMapper.playerCharacterRepository.save((PlayerCharacter) character);
            PlayerCharacterManager.returnAllSkills((PlayerCharacter) character);
            PlayerCharacterManager.showStatus((PlayerCharacter) character);
            PlayerCharacterManager.getSkillsByPosition((PlayerCharacter) character, position, session);
        }
    }

    public static void castSkill(SkillObject skillObject, CommonCharacter character, String position){
        //对于被动技能，直接应用
        if(skillObject.isPassive()){
            for(SkillEffect effect : skillObject.getEffects()){
                if(effect.getPosition().equals(position)){
                    GameCharacterManager.changeStatus(character, effect.getAttrKey(), effect.getValue());
                }
            }
        }
    }

    public static void castSkill(SkillObject skillObject, CommonCharacter caller, CommonCharacter target){
        if(!skillObject.isPassive())
            SkillFunctionHandler.useSkill(caller, target, skillObject);
    }

    public static void undoSkill(SkillObject skillObject, CommonCharacter character, String position) {
        for(SkillEffect effect : skillObject.getEffects()){
                if(effect.getPosition().equals(position)){
                    if(effect.getValue().getClass().equals(Double.class) || effect.getValue().getClass().equals(double.class)){
                        // 修改double类型数字
                        GameCharacterManager.changeStatus(character, effect.getAttrKey(), (double)effect.getValue() * -1);
                    }else if(effect.getValue().getClass().equals(float.class) || effect.getValue().getClass().equals(Float.class)){
                        // 修改float类型数字
                        GameCharacterManager.changeStatus(character, effect.getAttrKey(), (float)effect.getValue() * -1);
                    }else if(effect.getValue().getClass().equals(Integer.class) || effect.getValue().getClass().equals(int.class)){
                        // 修改int类型数字
                        GameCharacterManager.changeStatus(character, effect.getAttrKey(), (int)effect.getValue() * -1);
                    }else if(effect.getValue().getClass().equals(Long.class) || effect.getValue().getClass().equals(long.class)){
                        // 修改long类型数字
                        GameCharacterManager.changeStatus(character, effect.getAttrKey(), (long)effect.getValue() * -1);
                    }else if(effect.getValue().getClass().equals(Short.class) || effect.getValue().getClass().equals(short.class)){
                        // 修改long类型数字
                        GameCharacterManager.changeStatus(character, effect.getAttrKey(), (long)effect.getValue() * -1);
                    }else if(effect.getValue().getClass().equals(Byte.class) || effect.getValue().getClass().equals(byte.class)){
                        // 修改long类型数字
                        GameCharacterManager.changeStatus(character, effect.getAttrKey(), (long)effect.getValue() * -1);
                    }else if(effect.getValue().getClass().equals(Boolean.class) || effect.getValue().getClass().equals(boolean.class)){
                        // 修改double类型数字
                        GameCharacterManager.changeStatus(character, effect.getAttrKey(), !(boolean)effect.getValue() );
                    }else if(effect.getValue().getClass().equals(String.class)){
                        // 修改字符串类型数字
                        GameCharacterManager.changeStatus(character, effect.getAttrKey(), effect.getValue() );
                    }
                }
            }
    }

    public static SkillObject findBasicSkill(SkillObject skillObject, CommonCharacter character, String position) {
        /*
        * 寻找当前技能对应的的基本技能
        * eg：  华山剑法->基本剑法    天山折梅手-装备到招架->基本招架    天山折梅手-装备到主手->基本拳脚
        * */
        String basicSkillKey = "";
        SkillObject basicSkillObject = null;
        // 先检查技能是不是特殊技能
        if(skillObject.getCategoryType().equals("SCT_TESHU")){
            if(position.equals("zhaojia")){ //如果是装备到招架，则手动设置
                basicSkillKey = "skill_jiben_zhaojia";
            }else{// 否则直接获取
                basicSkillKey = skillObject.getBasicSkill();
            }
        }

        if(basicSkillKey != null && !basicSkillKey.trim().equals("")){
            //根据技能的key和所有者 在数据库中查找技能
            basicSkillObject = MongoMapper.skillObjectRepository.findSkillObjectByDataKeyAndOwner(basicSkillKey, character.getId());
        }

        return basicSkillObject;
    }

    public static boolean checkWeaponWhenEquipSkill(SkillObject skillObject, CommonCharacter character, String position, Session session) {
        /*
        * 检查技能装备的位置对应的是否有合法的武器
        * */
        // todo: 检查装备和武器是否对应
        return true;
    }

    public static float calculusRemainCd(SkillObject skillObject) {
        /*
        * @ 计算技能的Cd时间
        * */
        if(skillObject.getCdFinishTime() == null)  return 3;
        float cdRemain = (System.currentTimeMillis() - skillObject.getCdFinishTime()) / 1000F;
        return  cdRemain < 0 ? 3 : cdRemain;
    }

    public static List<EmbeddedCommand> getAvailableCommands(SkillObject skillObject, PlayerCharacter caller){
        List<EmbeddedCommand> cmds = new ArrayList<>();
        if(skillObject.isPassive()){
            // 检查技能的所有者是npc还是玩家
            if(MongoMapper.worldNpcObjectRepository.existsById(skillObject.getOwner())){
                // 是npc，显示请教等命令
                WorldNpcObject owner = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(skillObject.getOwner());
                Map<String, Object> args = new HashMap<String, Object>();
                args.put("skill_key", skillObject.getDataKey());
                args.put("from", owner.getId());
                if(owner.isLearnByObject()){
                    // 通过物品学习
                    cmds.add(new EmbeddedCommand("请教", "learn_skill_by_object", args));
                }else{
                    // 正常的通过师傅学习
                    cmds.add(new EmbeddedCommand("请教", "learn_skill_from_teacher", args));
                }
            }else if(MongoMapper.playerCharacterRepository.existsById(skillObject.getOwner())){
                // 是玩家则显示 装备、取消装备、遗忘等命令
                if(!skillObject.getCategoryType().equals("SCT_ZHISHI")){
                    PlayerCharacter owner = MongoMapper.playerCharacterRepository.findPlayerCharacterById(skillObject.getOwner());
                    for(String position: skillObject.getPositions()){
                        Map<String, Object> args = new HashMap<>();
                        args.put("dbref", skillObject.getId());
                        args.put("position", position);
                        String positionName = SkillPositionHandler.skillPositionNameMapping.get(position);
                        // 对于非知识类技能，有装备和卸掉操作，否则没有
                        if(skillObject.getEquippedPositions().contains(position)){
                            // 已经装备，显示卸掉
                            cmds.add(new EmbeddedCommand("从"+positionName+"卸掉", "take_off_skill", args));
                        }else{
                            // 没有装备，显示装备
                            cmds.add(new EmbeddedCommand("装备到"+positionName, "equip_skill", args));
                        }
                    }
                }

            }else{
                // 技能没有归属
                System.out.println("技能（"+ skillObject.getName() +"）没有归属！");
            }
        }
        return cmds;
    }

    public static void chargeSkill(SkillObject skillObject, PlayerCharacter playerCharacter, Session session, Object learnTarget) {
        /*
        * 给技能充能，如果当前级别充满了则升级
        * */
        try{
            // 计算玩家本次充值的潜能数量
            int chargeNumber = CommonAlgorithm.calculateSkillChargeNumber(playerCharacter);

            // 计算技能当前等级升级所需要的潜能数量
            int skillLevelUpNeedNumber = CommonAlgorithm.calculateSkillLevelUpNumber(skillObject);

            // 扣除玩家的潜能(知识类技能免除这一步)
            if(!skillObject.getCategoryType().equals("SCT_ZHISHI")){
                if(playerCharacter.getPotential() < chargeNumber){//如果不够就全扣，归零
                    chargeNumber = playerCharacter.getPotential();
                    playerCharacter.setPotential(0);
                }else{
                    playerCharacter.setPotential(playerCharacter.getPotential() - chargeNumber);
                }
                MongoMapper.playerCharacterRepository.save(playerCharacter);
            }

            // 如果是通过NPC学习，判断NPC是师傅传授还是通过物品教授
            if(learnTarget.getClass().equals(WorldNpcObject.class)){
                WorldNpcObject npc = (WorldNpcObject) learnTarget;
                if(npc.isLearnByObject()){
                    // 学习的对象是通过物品充值才能习得，扣除充值的潜能
                    int balance = playerCharacter.getLearnByObjectRecord().get(npc.getName());
                    if(balance < chargeNumber){
                        chargeNumber = balance;
                        playerCharacter.getLearnByObjectRecord().put(npc.getName(), 0);
                    }else{
                        playerCharacter.getLearnByObjectRecord().put(npc.getName(), balance - chargeNumber);
                    }
                    MongoMapper.playerCharacterRepository.save(playerCharacter);
                }
            }

            // 累加技能累计消耗的潜能
            skillObject.setChargedPotential(skillObject.getChargedPotential() + chargeNumber);

            // 计算技能当前等级下所充值的潜能数量
            int currentBalance = skillObject.getCurrentPotentialBalance() + chargeNumber;
            skillObject.setCurrentPotentialBalance(currentBalance);
            MongoMapper.skillObjectRepository.save(skillObject);
            // 如果已经溢出，则表示可以升级技能
            while (currentBalance >= skillLevelUpNeedNumber){
                currentBalance = skillLevelUpNeedNumber - currentBalance;
                levelUp(skillObject, playerCharacter, session);
                skillLevelUpNeedNumber =  CommonAlgorithm.calculateSkillLevelUpNumber(skillObject);
            }
        }catch (Exception e){
            System.out.println("技能升级失败");
        }
    }

    private static void levelUp(SkillObject skillObject, PlayerCharacter playerCharacter, Session session)  {
        /*
        * 升级一个技能
        * */
        // 升级技能
        skillObject.setLevel(skillObject.getLevel() + 1);
        // 如果技能已经装备 在重新计算技能属性之前，取消技能的效果
        if(skillObject.getEquippedPositions().size() > 0){
            Set<String> positions = skillObject.getEquippedPositions();
            for(String position: positions){
                SkillObjectManager.undoSkill(skillObject, playerCharacter, position);
            }
        }
        SkillObjectManager.calculusEffects(playerCharacter, null, skillObject);
        // 如果技能已经装备 在重新计算技能属性之之后，应用技能的效果
        if(skillObject.getEquippedPositions().size() > 0){
            Set<String> positions = skillObject.getEquippedPositions();
            for(String position: positions){
                SkillObjectManager.castSkill(skillObject, playerCharacter, position);
            }
        }
        MongoMapper.skillObjectRepository.save(skillObject);
        session.sendText(JsonResponse.JsonStringResponse(new ToastMessage(String.format(GameWords.SKILL_LEVEL_UP,  skillObject.getName(), skillObject.getLevel()))));
    }

    public static String getCastMessage(CommonCharacter target) {
        return "测试信息";
    }
}
