package com.mud.game.utils.datafile.exportor;

import org.springframework.data.repository.CrudRepository;

import java.io.InputStream;

public class Exportor {

    private String tableName;
    private String type;
    private String outFilePath;
    private CrudRepository repository;

    public Exportor(CrudRepository repository, String outFilePath, String tableName, String type) {
        this.outFilePath = outFilePath;
        this.type = type;
        this.tableName = tableName;
        this.repository = repository;
    }

    public String toFile() {
        Iterable<?> data = repository.findAll();
        ExportContext strategySelector = new ExportContext(data, tableName, outFilePath, type);
        ExportStrategy strategy = strategySelector.getStrategy();
        return strategy.exportData();
    }
}
