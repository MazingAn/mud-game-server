package com.mud.game.utils.datafile.csvutils;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CsvUtils {
    private static final CsvMapper mapper = new CsvMapper();


    /*
    * Csv文件读取
    * */
    public static <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
        // 取消默认的字段排序（字典序）
        mapper.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
        CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
        ObjectReader reader = mapper.readerFor(clazz).with(schema);
        return reader.<T>readValues(stream).readAll();
    }
    /*
    * 写入可迭代对象到csv文件
     */
    public static <T> void write(Class<T> clazz, Iterable<?> data, String outFilePath) throws IOException {
        // 取消默认的字段排序（字典序）
        mapper.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
        //写入到文件
        CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
        ObjectWriter writer = mapper.writer(schema);
        writer.writeValue(new File(outFilePath), data);
    }

}