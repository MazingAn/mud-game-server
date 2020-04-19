package com.mud.game.utils.datafile.importor;

import org.springframework.data.repository.CrudRepository;
import java.io.InputStream;

public class Importor {

    private InputStream stream;
    private String fileName;
    private CrudRepository repository;

    public Importor(InputStream stream, String fileName, CrudRepository repository) {
        this.stream = stream;
        this.fileName = fileName;
        this.repository = repository;
    }

    public void ToDataBase() {
        String[] fileNameSplit =  this.fileName.split("\\.");
        String tableName = fileNameSplit[0];
        String type = fileNameSplit[1];
        ImportContext strategySelector = new ImportContext(stream, tableName, type, repository);
        ImportStrategy strategy = strategySelector.getStrategy();
        strategy.importData();
    }
}
