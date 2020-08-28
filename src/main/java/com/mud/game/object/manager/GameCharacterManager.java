package com.mud.game.object.manager;

import com.mud.game.algorithm.CommonAlgorithm;
import com.mud.game.messages.*;
import com.mud.game.net.session.CallerType;
import com.mud.game.net.session.GameSessionService;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.*;
import com.mud.game.server.ServerManager;
import com.mud.game.statements.buffers.BufferManager;
import com.mud.game.statements.buffers.CharacterBuffer;
import com.mud.game.structs.CombatCommand;
import com.mud.game.structs.ObjectMoveInfo;
import com.mud.game.structs.SimpleCharacter;
import com.mud.game.utils.resultutils.GameWords;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.lang.reflect.Field;
import java.util.*;


/**
 * 游戏角色管理类
 * 游戏中对角色通用处理都放在这里
 * 在战斗，技能使用，装备使用这些环节，游戏中的NPC和玩家没有任何区别
 */
public class GameCharacterManager {

    /**
     * 玩家类的反射工具，通过反射把玩家类的属性找到并开放出去
     * <p>
     * 获取所有的所有成员属性，包括父类，然后在这些属性中查找对应的属性Filed数据更新进行操作
     * 为了配合这里能够获取到父类的属性，玩家类中所有的成员属性都被设置为了public
     * 感觉有点怪怪的,对性能有一定影响，暂时还没找到更好的解决方案:(
     * 聪明的旅行者（接盘侠）你有好办法没？ :)
     * </p>
     *
     * @param character CommonCharacter 要查找的角色
     * @param attrName  String 属性的名称（英文标识）
     * @return Object 玩家的属性对象，Object类型，调用者自行根据游戏逻辑和属性定义转换
     */
    public static Object findAttributeByName(CommonCharacter character, String attrName) throws NoSuchFieldException, IllegalAccessException {

        Field[] fields = character.getClass().getFields();
        // 用了一万年的遍历查找，
        // 如果要优化的化，可以在服务器启动的时候找到所有的角色属性，建一个静态Map吧
        Field hitField = null; //命中的字段
        for (Field field : fields) {
            if (attrName.equals(field.getName())) {
                hitField = field;
                return hitField.get(character);
            }
        }
        // 如果上面的查找没有成功返回，那么属性可能被包含在了Character.customerAttr中
        // 所以 如果上面的查询没有命中 就还要在customerAttr里面再找一次, 加油吧~_~!
        if (character.getCustomerAttr().containsKey(attrName)) {
            return character.getCustomerAttr().get(attrName).get("value");
        }
        return null;
    }

    /**
     * 直接设置角色的某个属性
     *
     * @param character CommonCharacter 角色
     * @param attrKey   String 属性名称
     * @param value     Object 属性值
     */
    public static void setAttributeByName(CommonCharacter character, String attrKey, Object value) {
        /*
         * @ 这个方法主要用来直接设置角色的属性
         * @ 角色的属性分为默认属性，这部分属性可以直接通过get set方法获取和设置
         * @ 还有一部分属性是自定义属性， 这部分属性是在数据库里面自己定义的，需要修改CustomertAttr
         * */
        try {
            Field field = character.getClass().getField(attrKey);
            String valueStr = value.toString();
            if ("int".equals(field.getType().getName()) || "Integer".equals(field.getType().getName())) {
                if (valueStr.contains(".")) valueStr = valueStr.split("\\.")[0];
                field.setInt(character, Integer.parseInt(valueStr));
            } else if ("float".equalsIgnoreCase(field.getType().getName())) {
                field.setFloat(character, Float.parseFloat(valueStr));
            } else if ("double".equalsIgnoreCase(field.getType().getName())) {
                field.setDouble(character, Double.parseDouble(valueStr));
            } else if ("String".equals(field.getType().getName())) {
                field.set(character, valueStr);
            } else if ("byte".equalsIgnoreCase(field.getType().getName())) {
                if (valueStr.contains(".")) valueStr = valueStr.split("\\.")[0];
                field.setByte(character, Byte.parseByte(valueStr));
            } else if ("long".equalsIgnoreCase(field.getType().getName())) {
                if (valueStr.contains(".")) valueStr = valueStr.split("\\.")[0];
                field.setLong(character, Long.parseLong(valueStr));
            } else if ("boolean".equalsIgnoreCase(field.getType().getName())) {
                field.setBoolean(character, Boolean.parseBoolean(valueStr));
            } else {
                field.set(character, value);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // 既然抛出了上述异常，那么这个属性可能在自定义属性中
            Map<String, Map<String, Object>> cattr = character.getCustomerAttr();
            if (cattr.containsKey(attrKey)) {
                // 根据属性的类型运算
                Object originValue = cattr.get(attrKey).get("value");
                if (originValue.getClass() == Integer.class) {
                    String valueStr = value.toString();
                    if (valueStr.contains(".")) valueStr = valueStr.split("\\.")[0];
                    cattr.get(attrKey).put("value", Integer.parseInt(valueStr));
                }
                if (originValue.getClass() == Float.class) {
                    float finalValue = (float) value;
                    cattr.get(attrKey).put("value", value);
                }
            } else {
                // 如果自定义属性中也没有找到，那这这不步操作肯定是运维人员配置的有问题，这部操作就GG了，测试期间先打印一下
                System.out.println("没有找到对应的属性：" + attrKey);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("无法转换类型");
        }
    }

    /**
     * 修改角色的某个属性
     *
     * @param character CommonCharacter 角色
     * @param attrKey   String 属性名称
     * @param value     Object 属性值
     */
    public static void changeStatus(CommonCharacter character, String attrKey, Object value) {
        /*
         * @ 这个方法主要用来增加或减少角色的属性
         * @ 角色的属性分为默认属性，这部分属性可以直接通过get set方法获取和设置
         * @ 还有一部分属性是自定义属性， 这部分属性是在数据库里面自己定义的，需要修改CustomertAttr
         * */
        // 检查是不是角色的默认属性,使用反射检查玩家是否有这个属性，如果没有会抛出NoSuchFieldException，那么则可能在自定义属性中
        try {
            Field field = character.getClass().getField(attrKey);
            String valueStr = value.toString();
            if ("int".equals(field.getType().getName()) || "Integer".equals(field.getType().getName())) {
                if (valueStr.contains(".")) valueStr = valueStr.split("\\.")[0];
                field.setInt(character, field.getInt(character) + Integer.parseInt(valueStr));
            } else if ("float".equalsIgnoreCase(field.getType().getName())) {
                    field.setFloat(character, field.getFloat(character) + Float.parseFloat(valueStr));
            } else if ("double".equalsIgnoreCase(field.getType().getName())) {
                field.setDouble(character, field.getDouble(character) + Double.parseDouble(valueStr));
            } else if ("String".equals(field.getType().getName())) {
                field.set(character, valueStr);
            } else if ("byte".equalsIgnoreCase(field.getType().getName())) {
                if (valueStr.contains(".")) valueStr = valueStr.split("\\.")[0];
                field.setByte(character, (byte) (field.getByte(character) + Byte.parseByte(valueStr)));
            } else if ("long".equalsIgnoreCase(field.getType().getName())) {
                if (valueStr.contains(".")) valueStr = valueStr.split("\\.")[0];
                field.setLong(character, field.getLong(character) + Long.parseLong(valueStr));
            } else if ("boolean".equalsIgnoreCase(field.getType().getName())) {
                field.setBoolean(character, Boolean.parseBoolean(valueStr));
            } else {
                field.set(character, value);
            }
            // 检查时都有后天属性发生变动，这个时候应该追加其影响的其他属性
            checkOnAfterAttrChange(character, attrKey, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // 既然抛出了上述异常，那么这个属性可能在自定义属性中
            Map<String, Map<String, Object>> cattr = character.getCustomerAttr();
            if (cattr.containsKey(attrKey)) {
                // 根据属性的类型运算
                Object originValue = cattr.get(attrKey).get("value");
                if (originValue.getClass() == Integer.class) {
                    String valueStr = value.toString();
                    if (valueStr.contains(".")) valueStr = valueStr.split("\\.")[0];
                    int finalValue = (int) originValue + Integer.parseInt(valueStr);
                    cattr.get(attrKey).put("value", finalValue);
                }
                if (originValue.getClass() == Float.class) {
                    float finalValue = (float) originValue + (float) value;
                    cattr.get(attrKey).put("value", finalValue);
                }
            } else {
                // 如果自定义属性中也没有找到，那这这不步操作肯定是运维人员配置的有问题，这部操作就GG了，测试期间先打印一下
                System.out.println("没有找到对应的属性：" + attrKey);
            }
            // 持久化
            GameCharacterManager.saveCharacter(character);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("无法转换类型");
        }

        // 检测角色死亡
        if (character.getHp() <= 0) {
            GameCharacterManager.die(character);
        }

    }

    /**
     * 当角色的后天属性发生变动的时候进行联动更新
     *
     * @param character    CommonCharacter 角色
     * @param attrKey      String 属性名称
     * @param changedValue Object 属性值
     */
    public static void checkOnAfterAttrChange(CommonCharacter character, String attrKey, Object changedValue) {

        String strValue = changedValue.toString();
        if (strValue.contains(".")) strValue = strValue.split("\\.")[0];

        if (attrKey.equals("canAttck")) {
            CommonAlgorithm.onAfterCanAttckChange(character, attrKey, Boolean.parseBoolean(strValue));
            return;
        }
        int intValue = Integer.parseInt(strValue);
        if (attrKey.equals("after_bone")) {
            CommonAlgorithm.onAfterBoneChange(character, intValue);
        }
        if (attrKey.equals("after_body")) {
            CommonAlgorithm.onAfterBodyChange(character, intValue);
        }
        if (attrKey.equals("after_smart")) {
            CommonAlgorithm.onAfterSmartChange(character, intValue);
        }
        if (attrKey.equals("after_lucky")) {
            CommonAlgorithm.onAfterLuckyChange(character, intValue);
        }
        if (attrKey.equals("after_arm")) {
            CommonAlgorithm.onAfterArmChange(character, intValue);
        }
    }

    /**
     * 检查角色是否拥有某一个技能
     *
     * @param character Character 角色
     * @param skillKey  String 技能key
     * @return boolean 有：true 没有：false
     */
    public static boolean hasSkill(CommonCharacter character, String skillKey) {
        /*
         * @ 检查角色是否拥有某个技能
         * */
        return findSkillBySKillKey(character, skillKey) != null;
    }

    public static boolean skillLevelGt(CommonCharacter character, String skillKey, int compareLevel) {
        boolean result = false;
        SkillObject skillObject = findSkillBySKillKey(character, skillKey);
        if (skillObject != null && skillObject.getLevel() >= compareLevel) {
            return true;
        } else {
            character.msg(new MsgMessage(String.format(GameWords.NEED_SKILL_LEVEL_GT, skillObject.getName(), compareLevel)));
            return false;
        }
    }

    public static SkillObject findSkillBySKillKey(CommonCharacter character, String skillKey) {
        for (String skillId : character.getSkills()) {
            SkillObject skillObject = MongoMapper.skillObjectRepository.findSkillObjectById(skillId);
            if (skillObject != null && skillObject.getDataKey().equals(skillKey)) {
                return skillObject;
            }
        }
        return null;
    }

    /**
     * 检查角色是否装备某一个技能
     *
     * @param character 被检查的角色
     * @param skillKey  技能的key
     */
    public static boolean hasEquippedSkill(CommonCharacter character, String skillKey) {
        for (String skillId : character.getSkills()) {
            SkillObject skillObject = MongoMapper.skillObjectRepository.findSkillObjectById(skillId);
            if (skillObject != null && skillObject.getDataKey().equals(skillKey) &&
                    skillObject.getEquippedPositions().size() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据技能的key获得角色的技能对象
     *
     * @param character CommonCharacter 角色
     * @param skillKey  String 技能的标识
     */
    public static SkillObject getCharacterSkillByDataKey(CommonCharacter character, String skillKey) {
        for (String skillId : character.getSkills()) {
            SkillObject skillObject = MongoMapper.skillObjectRepository.findSkillObjectById(skillId);
            if (skillObject != null && skillObject.getDataKey().equals(skillKey)) {
                return skillObject;
            }
        }
        return null;
    }

    /**
     * 根据ID查找角色实例
     */
    public static CommonCharacter getCharacterObject(String characterId) {
        if (MongoMapper.playerCharacterRepository.existsById(characterId))
            return MongoMapper.playerCharacterRepository.findPlayerCharacterById(characterId);
        else
            return MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(characterId);
    }

    /**
     * 获得默认技能
     *
     * @param character CommonCharacter 角色
     */
    public static SkillObject getDefaultSkill(CommonCharacter character) {
        if (character.getDefaultSkill() == null) {
            // 如果橘角色没有默认技能 则根据默认技能的key查找这个技能 并创建
            String defaultSkillKey = ServerManager.gameSetting.getDefaultSkill();
            SkillObject defaultSkill = SkillObjectManager.create(defaultSkillKey);
            defaultSkill.setOwner(character.getId());
            character.setDefaultSkill(defaultSkill.getId());
            MongoMapper.skillObjectRepository.save(defaultSkill);
            saveCharacter(character);
            return defaultSkill;
        } else {
            // 如果有默认技能，则查询技能实例
            return MongoMapper.skillObjectRepository.findSkillObjectByDataKeyAndOwner(character.getDefaultSkill(), character.getId());
        }
    }

    /**
     * 执行技能
     *
     * @param character   CommonCharacter 技能的释放者
     * @param target      CommonCharacter 技能作用对象
     * @param skillObject SkillObject 技能实例
     */
    public static void castSkill(CommonCharacter character, CommonCharacter target, SkillObject skillObject) {
        SkillObjectManager.castSkill(skillObject, character, target);
    }

    /**
     * 持久化角色信息（保存到数据库）
     *
     * @param character CommonCharacter
     */
    public static void saveCharacter(CommonCharacter character) {
        if (character instanceof PlayerCharacter)
            MongoMapper.playerCharacterRepository.save((PlayerCharacter) character);
        else if (character instanceof WorldNpcObject)
            MongoMapper.worldNpcObjectRepository.save((WorldNpcObject) character);
    }

    /**
     * 角色死亡触发
     *
     * @param character 角色死亡之后更新信息到客户端
     *                  延时触发复活
     */
    public static void die(CommonCharacter character) {
        character.setHp(0);
        character.setName(character.getName() + "的尸体");
        GameSessionService.updateCallerType(character.getId(), CallerType.DIE);
        saveCharacter(character);
        characterMoveOut(character);
        characterMoveIn(character);
        if (character instanceof WorldNpcObject) {
            Timer timer = new Timer();
            float rebornTime = Math.max(((WorldNpcObject) character).getRebornTime(), 1);
            timer.schedule(reborn(character), (int) rebornTime * 1000);
        } else {
            GameSessionService.updateCallerType(character.getId(), CallerType.DIE);
            character.msg(new RebornCommandsMessage((PlayerCharacter) character));
        }
    }

    /**
     * 角色复活
     *
     * @param character 角色实例
     * @return TimerTask 延时任务实例
     */
    public static TimerTask reborn(CommonCharacter character) {
        return new TimerTask() {
            @Override
            public void run() {
                character.setHp(character.getMax_hp());
                character.setName(character.getName().replaceAll("的尸体", ""));
                characterMoveOut(character);
                characterMoveIn(character);
                GameCharacterManager.saveCharacter(character);
            }
        };
    }

    /**
     * 角色信息从当前房间列表移除
     *
     * @param character 角色实例
     */
    public static void characterMoveOut(CommonCharacter character) {
        SimpleCharacter simpleCharacter = new SimpleCharacter(character);
        String type = character instanceof WorldNpcObject ? "npcs" : "players";
        ObjectMoveInfo moveInfo = new ObjectMoveInfo(type, Arrays.asList(new SimpleCharacter[]{simpleCharacter}));
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(character.getLocation());
        WorldRoomObjectManager.broadcast(room, new ObjectMoveOutMessage(moveInfo), character.getId());
    }

    /**
     * 角色信息追加到当前房间列表
     *
     * @param character 角色实例
     */
    public static void characterMoveIn(CommonCharacter character) {
        SimpleCharacter simpleCharacter = new SimpleCharacter(character);
        String type = character instanceof WorldNpcObject ? "npcs" : "players";
        ObjectMoveInfo moveInfo = new ObjectMoveInfo(type, Arrays.asList(new SimpleCharacter[]{simpleCharacter}));
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(character.getLocation());
        WorldRoomObjectManager.broadcast(room, new ObjectMoveInMessage(moveInfo), character.getId());
    }

    /**
     * 为角色追加一个buff
     */
    public static void addBuffer(String bufferName, float duration, int addedCount, int maxAdd,
                                 boolean goodBuffer, String attrKey, Object changedValue,
                                 CommonCharacter target, SkillObject skillObject, boolean persistent, CommonCharacter caller) {
        Map<String, Set<String>> characterBuffers = target.getBuffers();
        // 如果玩家已经有了这个buffer
        if (characterBuffers.containsKey(bufferName)) {
            Set<String> usedBuffers = characterBuffers.get(bufferName);
            // 如果buffer可以叠加 则直接叠加
            if (usedBuffers.size() < maxAdd || usedBuffers.isEmpty()) {
                CharacterBuffer buffer = BufferManager.CreateBuffer(bufferName, duration, maxAdd, goodBuffer, attrKey, changedValue, target, skillObject, persistent,caller);
                BufferManager.startBufferTimer(buffer.bufferId);
            }
        }
        // 如果玩家没有这个buffer
        else {
            CharacterBuffer buffer = BufferManager.CreateBuffer(bufferName, duration, maxAdd, goodBuffer, attrKey, changedValue, target, skillObject, persistent, caller);
            BufferManager.startBufferTimer(buffer.bufferId);
        }
        showBuffers(target);
    }


    /**
     * 像客户端推送buffer信息
     *
     * @param character 游戏角色
     */
    public static void showBuffers(CommonCharacter character) {
        character.msg(new BuffersMessage(character));
    }

    /**
     * 显示玩家战斗期间可以使用的技能
     *
     * @param character 要作用的角色
     */
    public static void showCombatCommands(CommonCharacter character) {
        List<CombatCommand> commands = new ArrayList<>();
        for (String skillObjectId : character.getSkills()) {
            SkillObject skillObject = MongoMapper.skillObjectRepository.findSkillObjectById(skillObjectId);
            if (!skillObject.isPassive() && skillObject.getEquippedPositions().size() > 0) {
                commands.add(new CombatCommand(skillObject.getName(), skillObject.getDataKey(), skillObject.getIcon()));
            }
        }
        character.msg(new CombatCommandsMessage(commands));
    }

    /**
     * 获取角色一件已经装备的武器
     */
    public static EquipmentObject getOneEquippedWeapon(CommonCharacter character) {
        if (character.getEquippedEquipments() == null) {
            return null;
        }
        String leftHandWeaponId = character.getEquippedEquipments().get("POSITION_LEFT_HAND");
        String rightHandWeaponId = character.getEquippedEquipments().get("POSITION_RIGHT_HAND");
        if (leftHandWeaponId != null && !"".equals(leftHandWeaponId.trim())) {
            return MongoMapper.equipmentObjectRepository.findEquipmentObjectById(leftHandWeaponId);
        }

        if (rightHandWeaponId != null && !"".equals(rightHandWeaponId.trim())) {
            return MongoMapper.equipmentObjectRepository.findEquipmentObjectById(rightHandWeaponId);
        }

        return null;
    }

    /**
     * 获取角色所有已经装备的武器
     */
    public static Set<EquipmentObject> getAllEquippedWeapon(CommonCharacter character) {
        Set<EquipmentObject> equippedWeapons = new HashSet<>();
        if (character.getEquippedEquipments() != null) {
            String leftHandWeaponId = character.getEquippedEquipments().get("POSITION_LEFT_HAND");
            String rightHandWeaponId = character.getEquippedEquipments().get("POSITION_RIGHT_HAND");
            if (leftHandWeaponId != null && !"".equals(leftHandWeaponId.trim())) {
                equippedWeapons.add(MongoMapper.equipmentObjectRepository.findEquipmentObjectById(leftHandWeaponId));
            }
            if (rightHandWeaponId != null && !"".equals(rightHandWeaponId.trim())) {
                equippedWeapons.add(MongoMapper.equipmentObjectRepository.findEquipmentObjectById(rightHandWeaponId));
            }
        }
        return equippedWeapons;
    }

}
