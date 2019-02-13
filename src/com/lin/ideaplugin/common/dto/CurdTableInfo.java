package com.lin.ideaplugin.common.dto;

import java.util.List;

public class CurdTableInfo {
    private String tableName;
    private String entityName;
    private List<TableColumnInfo> columns;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<TableColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<TableColumnInfo> columns) {
        this.columns = columns;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
