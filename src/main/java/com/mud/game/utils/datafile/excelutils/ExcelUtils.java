package com.mud.game.utils.datafile.excelutils;

import com.mud.game.utils.modelsutils.ClassUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ExcelUtils {

    public static <T> void write(Class<T> clazz, Iterable<?> data, String outFilePath) throws IOException, IllegalAccessException, NoSuchFieldException {
        /*
        * @ excel文件写入
        * */
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(clazz.getSimpleName());
        Row header = sheet.createRow(0);
        // 创建表头
        List<String> tableHeaders = ClassUtils.getSortedFieldNames(clazz);
        for(int i = 0; i < tableHeaders.size(); i++ )
        {
            Cell cell = header.createCell(i);
            cell.setCellValue(tableHeaders.get(i));
        }

        // 写入内容
        int rowNumber = 1;
        for(Object datum : data){
            Row row = sheet.createRow(rowNumber++);
            int cellNumber = 0;
            for(Object rowValue: ClassUtils.getSortedValue(datum.getClass(), datum)){
                if(rowValue == null) rowValue = "";
                row.createCell(cellNumber++).setCellValue(rowValue.toString());
            }
        }

        //保存
        try {
            FileOutputStream fos = new FileOutputStream(outFilePath);
            workbook.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * excel文件读取
    * */
    public static <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        List<T> instances = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(stream);
        Sheet sheet = workbook.getSheetAt(0);
        // 拿到header
        Row header = sheet.getRow(0);
        String[] names = new String[header.getLastCellNum()];
        for(int i = 0; i <= header.getLastCellNum()-1; i++ ){
            names[i] = header.getCell(i).toString();
        }
        // 拿到每列对应的java模型的Field
        List<Field> fields = ClassUtils.getSortedFields(clazz);
        // 把每一行转换成一个model对象
        for(int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++){
            Row row = sheet.getRow(rowNum);
            if(row == null || rowNum == 0){
                continue;
            }
            Constructor<?> constructor = clazz.getConstructor();
            Object instance = constructor.newInstance();
            for(int colNum = 0; colNum <= row.getLastCellNum()-1; colNum++){
                Cell cell = row.getCell(colNum);
                Field field = fields.get(colNum);
                if(cell.getCellType() == CellType.NUMERIC){
                    // 如果这是一个数字类型的字段;  作如下操作和类型转换
                    double value = cell.getNumericCellValue();
                    if (long.class.equals(field.getType())||Long.class.equals(field.getType())) {
                        field.set(instance, (long)value);
                    } else if (float.class.equals(field.getType()) || Float.class.equals(field.getType())) {
                        field.set(instance, (float)value);
                    } else if (double.class.equals(field.getType()) || Double.class.equals(field.getType())) {
                        field.set(instance, value);
                    } else if (Integer.class.equals(field.getType()) || int.class.equals(field.getType())) {
                        field.set(instance, (int)value);
                    } else if (Byte.class.equals(field.getType()) || byte.class.equals(field.getType())) {
                        field.set(instance, (byte)value);
                    } else{
                        field.set(instance, value);
                    }
                }else if(cell.getCellType() == CellType.BOOLEAN){
                    // 对于布尔类型的字段作如下处理
                    field.set(instance, cell.getBooleanCellValue());
                }else if(cell.getCellType() == CellType.STRING){
                    // 对于字符串类型 作处理
                    String value = cell.getStringCellValue();
                    if (long.class.equals(field.getType())||Long.class.equals(field.getType())) {
                        field.set(instance, Long.parseLong(value));
                    } else if (float.class.equals(field.getType()) || Float.class.equals(field.getType())) {
                        field.set(instance, Float.parseFloat(value));
                    } else if (double.class.equals(field.getType()) || Double.class.equals(field.getType())) {
                        field.set(instance, Double.parseDouble(value));
                    } else if (Integer.class.equals(field.getType()) || int.class.equals(field.getType())) {
                        field.set(instance, Integer.parseInt(value));
                    } else if (Byte.class.equals(field.getType()) || byte.class.equals(field.getType())) {
                        field.set(instance, Byte.parseByte(value));
                    } else if(Boolean.class.equals(field.getType()) || boolean.class.equals(field.getType())){
                        field.set(instance, Boolean.parseBoolean(value.toLowerCase()));
                    } else{
                        field.set(instance, value);
                    }
                }else {
                    // 对空字段，异常，错误字段的处理
                    if (long.class.equals(field.getType())||Long.class.equals(field.getType())) {
                        System.out.println("对于" + clazz + "第" + rowNum +"行，其" + field.getName() +"为空， 我们默认设置为" + 0);
                        field.set(instance, 0L);
                    } else if (float.class.equals(field.getType()) || Float.class.equals(field.getType())) {
                        System.out.println("对于" + clazz + "第" + rowNum +"行，其" + field.getName() +"为空， 我们默认设置为" + 0);
                        field.set(instance, 0F);
                    } else if (double.class.equals(field.getType()) || Double.class.equals(field.getType())) {
                        System.out.println("对于" + clazz + "第" + rowNum +"行，其" + field.getName() +"为空， 我们默认设置为" + 0);
                        field.set(instance, 0D);
                    } else if (Integer.class.equals(field.getType()) || int.class.equals(field.getType())) {
                        System.out.println("对于" + clazz + "第" + rowNum +"行，其" + field.getName() +"为空， 我们默认设置为" + 0);
                        field.set(instance, 0);
                    } else if (Byte.class.equals(field.getType()) || byte.class.equals(field.getType())) {
                        System.out.println("对于" + clazz + "第" + rowNum +"行，其" + field.getName() +"为空， 我们默认设置为" + 0);
                        field.set(instance, 0);
                    } else if(Boolean.class.equals(field.getType()) || boolean.class.equals(field.getType())){
                        System.out.println("对于" + clazz + "第" + rowNum +"行，其" + field.getName() +"为空， 我们默认设置为 false ");
                        field.set(instance, false);
                    } else if(String.class.equals(field.getType())){
                        System.out.println("对于" + clazz + "第" + rowNum +"行，其" + field.getName() +"为空， 我们默认设置为空字符串");
                        field.set(instance, "");
                    }else {
                        System.out.println("对于" + clazz + "第" + rowNum +"行，其" + field.getName() +"为空， 我们默认设置为null");
                        field.set(instance, null);
                    }
                }
            }
            instances.add((T) instance);
        }
        return instances;
    }


}
