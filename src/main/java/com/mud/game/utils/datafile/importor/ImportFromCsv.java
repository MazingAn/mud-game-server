package com.mud.game.utils.datafile.importor;

import com.mud.game.utils.datafile.csvutils.CsvUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;

import java.io.InputStream;
import java.util.List;

public class ImportFromCsv extends ImportStrategy {
    /*
    * 文件导入数据库策略的csv实现
    * */

    private final InputStream inputStream;
    private final String tableName;
    private final CrudRepository repository;
    private final String modelPackagePath;

    ImportFromCsv(InputStream inputStream, String tableName, CrudRepository repository) {
        this.inputStream = inputStream;
        this.tableName = tableName;
        this.repository = repository;
        this.modelPackagePath = "com.mud.game.worlddata.db.models.";
    }

    @Override
    public void importData() {
        try{
            //反射模型
            Class<?> modelClass = Class.forName(modelPackagePath+tableName);
            //读取数据
            List data = CsvUtils.read(modelClass, inputStream);
            //写入数据到对应的表
            repository.deleteAll();
            repository.saveAll(data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
