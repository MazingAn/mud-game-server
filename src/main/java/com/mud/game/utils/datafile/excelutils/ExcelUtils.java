package com.mud.game.utils.datafile.excelutils;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.mud.game.utils.modelsutils.ClassUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;

public class ExcelUtils {

    public static <T> void write(Class<T> clazz, Iterable<?> data, String outFilePath) throws IOException, IllegalAccessException, NoSuchFieldException {
        /*
        * @ excel文件写入
        * */
        HSSFWorkbook workbook = HSSFWorkbookFactory.createWorkbook();
        HSSFSheet sheet = workbook.createSheet(clazz.getSimpleName());
        HSSFRow header = sheet.createRow(0);
        // 创建表头
        List<String> tableHeaders = ClassUtils.getSortedFieldNames(clazz);
        for(int i = 0; i < tableHeaders.size(); i++ )
        {
            HSSFCell cell = header.createCell(i);
            cell.setCellValue(tableHeaders.get(i));
        }

        // 写入内容
        int rowNumber = 1;
        for(Object datum : data){
            HSSFRow row = sheet.createRow(rowNumber++);
            int cellNumber = 0;
            for(Object rowValue: ClassUtils.getSortedValue(datum.getClass(), datum)){
                if(rowValue == null) rowValue="";
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
        HSSFWorkbook workbook = new HSSFWorkbook(stream);
        FormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator(workbook);
        HSSFSheet sheet = workbook.getSheetAt(0);
        // 拿到header
        HSSFRow header = sheet.getRow(0);
        String[] names = new String[header.getLastCellNum()];
        for(int i = 0; i <= header.getLastCellNum()-1; i++ ){
            names[i] = header.getCell(i).toString();
        }
        // 拿到每列对应的java模型的Field
        List<Field> fields = ClassUtils.getSortedFields(clazz);
        // 把每一行转换成一个model对象
        for(int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++){
            HSSFRow row = sheet.getRow(rowNum);
            if(row == null || rowNum == 0){
                continue;
            }
            Constructor<?> constructor = clazz.getConstructor();
            Object instance = constructor.newInstance();
            for(int colNum = 0; colNum <= row.getLastCellNum()-1; colNum++){
                HSSFCell cell = row.getCell(colNum);
                Field field = fields.get(colNum);
                cell.setCellType(CellType.STRING);
                String valueStr = cell.getStringCellValue();
                // 高能警告： 代码很丑，但是确实就是这样 要坦然接受  优雅的代码只是表面 好用的框架 底层依旧是渣渣代码
                // 对于原生类型 只能这样转换，对于其他类型就GG了，需要自己写转换器然后在这里增加
                if (long.class.equals(field.getType())||Long.class.equals(field.getType())) {
                    if(valueStr.trim().equals(""))  field.set(instance, 0L); else  field.set(instance, Long.parseLong(valueStr));
                } else if (float.class.equals(field.getType()) || Float.class.equals(field.getType())) {
                    if(valueStr.trim().equals(""))  field.set(instance, 0F); else field.set(instance, Float.parseFloat(valueStr));
                } else if (double.class.equals(field.getType()) || Double.class.equals(field.getType())) {
                    if(valueStr.trim().equals(""))  field.set(instance, 0D); else field.set(instance, Double.parseDouble(valueStr));
                } else if (Integer.class.equals(field.getType()) || int.class.equals(field.getType())) {
                    if(valueStr.trim().equals(""))  field.set(instance, 0); else field.set(instance, Integer.parseInt(valueStr));
                } else if (Boolean.class.equals(field.getType()) || boolean.class.equals(field.getType())) {
                    if(valueStr.trim().equals(""))  field.set(instance, ""); else field.set(instance, Boolean.parseBoolean(valueStr.toLowerCase()));
                } else if (Byte.class.equals(field.getType()) || byte.class.equals(field.getType())) {
                    if(valueStr.trim().equals(""))  field.set(instance, ""); else field.set(instance, valueStr);
                } else if (String.class.equals(field.getType())) {
                    if(valueStr.trim().equals(""))  field.set(instance, ""); else field.set(instance, valueStr);
                }  else {
                    field.set(instance, null);
                }
            }
            instances.add((T) instance);
        }
        return instances;
    }


}
