package com.lin.ideaplugin.common.dto;

/**
 * 数据库表的所有字段
 */
public class TableColumnInfo {
    private String ownTableName;           // 所属的数据库表名称

    private String columnName;             // 数据库中，字段名称
    private String dataType;               // 数据库中，数据类型
    private String columnComment;          // 字段描述

    private String fieldName;              // entity类，字段名称
    private String fieldType;              // entity类，数据类型

    public String getOwnTableName() {
        return ownTableName;
    }

    public void setOwnTableName(String ownTableName) {
        this.ownTableName = ownTableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
