package com.mud.game.utils.datafile.exportor;

import com.mud.game.utils.datafile.csvutils.CsvUtils;
import org.springframework.data.repository.CrudRepository;

import java.io.InputStream;
import java.util.List;

public class ExportToCsv extends ExportStrategy {
    /*
    * 数据库导出文件策略的csv实现
    * */

    private final Iterable<?> data;
    private final String tableName;
    private final String outFilePath;


    public ExportToCsv(Iterable<?> data, String tableName, String outFilePath) {
        this.data = data;
        this.tableName = tableName;
        this.outFilePath = outFilePath;
    }

    @Override
    public String exportData() {
        try{
            //反射模型
            String modelPackagePath = "com.mud.game.worlddata.db.models.";
            Class<?> modelClass = Class.forName(modelPackagePath+tableName);
            //从数据库读取数据
            String fullPath = outFilePath + "/" + tableName + ".csv";
            CsvUtils.write(modelClass, data, fullPath);
            return fullPath;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
