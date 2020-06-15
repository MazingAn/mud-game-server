package com.mud.game.utils.datafile.exportor;

public class ExportContext {
    private ExportStrategy strategy = null;

    public ExportContext(Iterable<?> data, String tableName, String outFilePath, String type) {
        if ("csv".equals(type)) {
            ExportToCsv exportToCsv = new ExportToCsv(data, tableName, outFilePath);
            this.strategy = exportToCsv;
        }else if ("xlsx".equals(type)){
            ExportToExcel exportToExcel = new ExportToExcel(data, tableName, outFilePath);
            this.strategy = exportToExcel;
        }else if("xls".equals(type)) {
            ExportToExcel exportToExcel = new ExportToExcel(data, tableName, outFilePath);
            this.strategy = exportToExcel;
        }
    }

    public ExportStrategy getStrategy() {
        return this.strategy;
    }
}
