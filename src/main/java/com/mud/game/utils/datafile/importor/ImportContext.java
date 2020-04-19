package com.mud.game.utils.datafile.importor;

import org.springframework.data.repository.CrudRepository;

import java.io.InputStream;

public class ImportContext {
    private ImportStrategy strategy = null;

    public ImportContext(InputStream stream, String tableName, String type, CrudRepository repository) {
        if ("csv".equals(type)) {
            ImportFromCsv importFromCsv = new ImportFromCsv(stream, tableName, repository);
            this.strategy = importFromCsv;
        }else if ("xlsx".equals(type)){
            ImportFromExcel importFromExcel = new ImportFromExcel(stream, tableName, repository);
            this.strategy = importFromExcel;
        }else if("xxx".equals(type)) {
            // TODO: 其他文件的支持策略
        }
    }

    public ImportStrategy getStrategy() {
        return this.strategy;
    }
}
