package com.mud.game.utils.datafile.exportor;

public class ExportContext {
    private ExportStrategy strategy = null;

    public ExportContext(Iterable<?> data, String tableName, String outFilePath, String type) {
        if ("csv".equals(type)) {
            ExportToCsv exportToCsv = new ExportToCsv(data, tableName, outFilePath);
            this.strategy = exportToCsv;
        }else if ("xls".equals(type)){
            // TODO: 增加对导出excel文件的支持策略
            ;
        }else if("xxx".equals(type)) {
            // TODO: 其他文件导出的支持策略
            ;
        }
    }

    public ExportStrategy getStrategy() {
        return this.strategy;
    }
}
