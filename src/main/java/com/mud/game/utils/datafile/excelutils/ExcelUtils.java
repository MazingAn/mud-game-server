package com.mud.game.utils.datafile.excelutils;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.mud.game.utils.modelsutils.ClassUtils;
import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

public class ExcelUtils {


    public static <T> void write(Class<T> clazz, Iterable<?> data, String outFilePath) throws IOException, IllegalAccessException, NoSuchFieldException {
        /*
        * @ excel文件写入
        * */
        HSSFWorkbook workbook = HSSFWorkbookFactory.createWorkbook();
        HSSFSheet sheet = workbook.createSheet(clazz.getSimpleName());
        HSSFRow header = sheet.createRow(0);
        List<String> tableHeaders = ClassUtils.getSortedFieldNames(clazz);
        for(int i = 0; i < tableHeaders.size(); i++ )
        {
            HSSFCell cell = header.createCell(i);
            cell.setCellValue(tableHeaders.get(i));
        }
        int rowNumber = 1;
        for(Object datum : data){
            HSSFRow row = sheet.createRow(rowNumber++);
            int cellNumber = 0;
            for(Object rowValue: ClassUtils.getSortedValue(datum.getClass(), datum)){
                row.createCell(cellNumber++).setCellValue(rowValue.toString());
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(outFilePath);
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
