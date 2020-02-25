package com.mud.game.object.manager;

import com.mud.game.object.algorithm.CommonAlgorithm;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.lang.reflect.Field;
import java.util.Map;

public class GameCharacterManager {
    /*
    * 游戏中对角色通用处理都放在这里
    * 在战斗，技能使用，装备使用这些环节，游戏中的NPC和玩家没有任何区别
    * */

    public static Object findAttributeByName(CommonCharacter character, String attrName) throws NoSuchFieldException, IllegalAccessException {
        /*
         * @ 玩家类的反射工具，通过反射把玩家类的属性找到并开放出去
         *  获取所有的所有成员属性，包括父类，然后在这些属性中查找对应的属性Filed数据更新进行操作
         *  为了配合这里能够获取到父类的属性，玩家类中所有的成员属性都被设置为了public
         *  感觉有点怪怪的,对性能有一定影响，暂时还没找到更好的解决方案:(
         *  聪明的旅行者（接盘侠）你有好办法没？ :)
         */
        Field[] fields = character.getClass().getFields();
        // 用了一万年的遍历查找，
        // 如果要优化的化，可以在服务器启动的时候找到所有的角色属性，建一个静态Map吧
        Field hitField = null; //命中的字段
        for(Field field: fields){
            if (attrName.equals(field.getName())){
                hitField = field;
                return hitField.get(character);
            }
        }
        // 如果上面的查找没有成功返回，那么属性可能被包含在了Character.customerAttr中
        // 所以 如果上面的查询没有命中 就还要在customerAttr里面再找一次, 加油吧~_~!
        if(character.getCustomerAttr().containsKey(attrName)){
            return character.getCustomerAttr().get(attrName).get("value");
        }
        return null;
    }

    public static void setAttributeByName(CommonCharacter character, String attrKey, Object value) {
        /*
         * @ 这个方法主要用来直接设置角色的属性
         * @ 角色的属性分为默认属性，这部分属性可以直接通过get set方法获取和设置
         * @ 还有一部分属性是自定义属性， 这部分属性是在数据库里面自己定义的，需要修改CustomertAttr
         * */
        try{
            Field field = character.getClass().getField(attrKey);
            String valueStr = value.toString();
            if("int".equals(field.getType().getName()) || "Integer".equals(field.getType().getName())){
                if(valueStr.contains(".")) valueStr = valueStr.split("\\.")[0];
                field.setInt(character, Integer.parseInt(valueStr));
            }else if("float".equalsIgnoreCase(field.getType().getName())){
                field.setFloat(character, Float.parseFloat(valueStr));
            }else if("double".equalsIgnoreCase(field.getType().getName())){
                field.setDouble(character, Double.parseDouble(valueStr));
            }else if("String".equals(field.getType().getName())){
                field.set(character, valueStr);
            }else if("byte".equalsIgnoreCase(field.getType().getName())){
                if(valueStr.contains(".")) valueStr = valueStr.split("\\.")[0];
                field.setByte(character, Byte.parseByte(valueStr));
            }else if("long".equalsIgnoreCase(field.getType().getName())){
                if(valueStr.contains(".")) valueStr = valueStr.split("\\.")[0];
                field.setLong(character, Long.parseLong(valueStr));
            }else if("boolean".equalsIgnoreCase(field.getType().getName())){
                field.setBoolean(character, Boolean.parseBoolean(valueStr));
            }else{
                field.set(character, value);
            }
        }catch (NoSuchFieldException | IllegalAccessException e){
            // 既然抛出了上述异常，那么这个属性可能在自定义属性中
            Map<String, Map<String, Object>> cattr =  character.getCustomerAttr();
            if(cattr.containsKey(attrKey)){
                // 根据属性的类型运算
                Object originValue = cattr.get(attrKey).get("value");
                if(originValue.getClass() == Integer.class){
                    String valueStr = value.toString();
                    if(valueStr.contains(".")) valueStr = valueStr.split("\\.")[0];
                    cattr.get(attrKey).put("value", Integer.parseInt(valueStr));
                }
                if(originValue.getClass() == Float.class){
                    float finalValue = (float)value;
                    cattr.get(attrKey).put("value", value);
                }
            }else{
                // 如果自定义属性中也没有找到，那这这不步操作肯定是运维人员配置的有问题，这部操作就GG了，测试期间先打印一下
                System.out.println("没有找到对应的属性：" + attrKey);
            }
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            System.out.println("无法转换类型");
        }
    }

    public static void changeStatus(CommonCharacter character, String attrKey, Object value) {
        /*
        * @ 这个方法主要用来增加或减少角色的属性
        * @ 角色的属性分为默认属性，这部分属性可以直接通过get set方法获取和设置
        * @ 还有一部分属性是自定义属性， 这部分属性是在数据库里面自己定义的，需要修改CustomertAttr
        * */

        // 检查是不是角色的默认属性,使用反射检查玩家是否有这个属性，如果没有会抛出NoSuchFieldException，那么则可能在自定义属性中
        try{
            Field field = character.getClass().getField(attrKey);
            String valueStr = value.toString();
            if("int".equals(field.getType().getName()) || "Integer".equals(field.getType().getName())){
                if(valueStr.contains(".")) valueStr = valueStr.split("\\.")[0];
                field.setInt(character, field.getInt(character) + Integer.parseInt(valueStr));
            }else if("float".equalsIgnoreCase(field.getType().getName())){
                field.setFloat(character, field.getFloat(character) + Float.parseFloat(valueStr));
            }else if("double".equalsIgnoreCase(field.getType().getName())){
                field.setDouble(character, field.getDouble(character) + Double.parseDouble(valueStr));
            }else if("String".equals(field.getType().getName())){
                field.set(character, valueStr);
            }else if("byte".equalsIgnoreCase(field.getType().getName())){
                if(valueStr.contains(".")) valueStr = valueStr.split("\\.")[0];
                field.setByte(character, (byte)(field.getByte(character) + Byte.parseByte(valueStr)));
            }else if("long".equalsIgnoreCase(field.getType().getName())){
                if(valueStr.contains(".")) valueStr = valueStr.split("\\.")[0];
                field.setLong(character, field.getLong(character) + Long.parseLong(valueStr));
            }else if("boolean".equalsIgnoreCase(field.getType().getName())){
                field.setBoolean(character, Boolean.parseBoolean(valueStr));
            }else{
                field.set(character, value);
            }

            // 检查时都有后天属性发生变动，这个时候应该追加其影响的其他属性
            checkOnAfterAttrChange(character, attrKey, value);

        }catch (NoSuchFieldException | IllegalAccessException e){
            // 既然抛出了上述异常，那么这个属性可能在自定义属性中
            Map<String, Map<String, Object>> cattr =  character.getCustomerAttr();
            if(cattr.containsKey(attrKey)){
                // 根据属性的类型运算
                Object originValue = cattr.get(attrKey).get("value");
                if(originValue.getClass() == Integer.class){
                    String valueStr = value.toString();
                    if(valueStr.contains(".")) valueStr = valueStr.split("\\.")[0];
                    int finalValue = (int)originValue + Integer.parseInt(valueStr);
                    cattr.get(attrKey).put("value", finalValue);
                }
                if(originValue.getClass() == Float.class){
                    float finalValue = (float)originValue + (float)value;
                    cattr.get(attrKey).put("value", finalValue);
                }
            }else{
                // 如果自定义属性中也没有找到，那这这不步操作肯定是运维人员配置的有问题，这部操作就GG了，测试期间先打印一下
                System.out.println("没有找到对应的属性：" + attrKey);
            }
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            System.out.println("无法转换类型");
        }
        // 持久化更改
        if(character.getClass().equals(PlayerCharacter.class)){
            MongoMapper.playerCharacterRepository.save((PlayerCharacter) character);
        }else{
            MongoMapper.worldNpcObjectRepository.save((WorldNpcObject) character);
        }
    }

    public static void checkOnAfterAttrChange(CommonCharacter character, String attrKey, Object changedValue){
        /*
        * 当玩家的后天属性发生变动的时候进行联动更新
        * */
        String strValue = changedValue.toString();
        if(strValue.contains(".")) strValue = strValue.split("\\.")[0];
        int intValue = Integer.parseInt(strValue);
        if(attrKey.equals("after_bone")){
            CommonAlgorithm.onAfterBoneChange(character, intValue);
        }
        if(attrKey.equals("after_body")){
            CommonAlgorithm.onAfterBodyChange(character, intValue);
        }
        if(attrKey.equals("after_smart")){
            CommonAlgorithm.onAfterSmartChange(character, intValue);
        }
        if(attrKey.equals("after_lucky")){
            CommonAlgorithm.onAfterLuckyChange(character, intValue);
        }
        if(attrKey.equals("after_arm")){
            CommonAlgorithm.onAfterArmChange(character, intValue);
        }
    }

    public static boolean characterHasSkill(CommonCharacter character, String skillKey){
        /*
        * @ 检查角色是否拥有某个技能
        * */
        for(String skillId : character.getSkills()){
            SkillObject skillObject = MongoMapper.skillObjectRepository.findSkillObjectById(skillId);
            if(skillObject != null && skillObject.getDataKey().equals(skillKey)){
                return true;
            }
        }
        return false;
    }

    public static SkillObject getCharacterSkillByDataKey(CommonCharacter character, String skillKey){
        /*
        * 根据技能的key获得角色的技能对象
        * */
        for(String skillId : character.getSkills()){
            SkillObject skillObject = MongoMapper.skillObjectRepository.findSkillObjectById(skillId);
            if(skillObject != null && skillObject.getDataKey().equals(skillKey)){
                return skillObject;
            }
        }
        return null;
    }


}
