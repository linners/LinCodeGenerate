package com.lin.ideaplugin.common.dto;

import com.lin.ideaplugin.extension.CodeGenerateSetting;

import java.util.List;

public class GenerateCurdParam {
    private boolean mapperCheckBox;
    private boolean xmlCheckBox;
    private boolean entityCheckBox;
    private boolean paramCheckBox;
    private boolean serviceCheckBox;
    private boolean apiCheckBox;
    private boolean dtoCheckBox;
    private boolean controllerCheckBox;
    private boolean vueListCheckBox;
    private boolean vueNewCheckBox;
    private boolean vueDetailCheckBox;
    private List<CurdTableInfo> tableInfos;

    private CodeGenerateSetting settings;

    public boolean isMapperCheckBox() {
        return mapperCheckBox;
    }

    public void setMapperCheckBox(boolean mapperCheckBox) {
        this.mapperCheckBox = mapperCheckBox;
    }

    public boolean isXmlCheckBox() {
        return xmlCheckBox;
    }

    public void setXmlCheckBox(boolean xmlCheckBox) {
        this.xmlCheckBox = xmlCheckBox;
    }

    public boolean isEntityCheckBox() {
        return entityCheckBox;
    }

    public void setEntityCheckBox(boolean entityCheckBox) {
        this.entityCheckBox = entityCheckBox;
    }

    public boolean isParamCheckBox() {
        return paramCheckBox;
    }

    public void setParamCheckBox(boolean paramCheckBox) {
        this.paramCheckBox = paramCheckBox;
    }

    public boolean isServiceCheckBox() {
        return serviceCheckBox;
    }

    public void setServiceCheckBox(boolean serviceCheckBox) {
        this.serviceCheckBox = serviceCheckBox;
    }

    public boolean isApiCheckBox() {
        return apiCheckBox;
    }

    public void setApiCheckBox(boolean apiCheckBox) {
        this.apiCheckBox = apiCheckBox;
    }

    public boolean isDtoCheckBox() {
        return dtoCheckBox;
    }

    public void setDtoCheckBox(boolean dtoCheckBox) {
        this.dtoCheckBox = dtoCheckBox;
    }

    public boolean isControllerCheckBox() {
        return controllerCheckBox;
    }

    public void setControllerCheckBox(boolean controllerCheckBox) {
        this.controllerCheckBox = controllerCheckBox;
    }

    public boolean isVueListCheckBox() {
        return vueListCheckBox;
    }

    public void setVueListCheckBox(boolean vueListCheckBox) {
        this.vueListCheckBox = vueListCheckBox;
    }

    public boolean isVueNewCheckBox() {
        return vueNewCheckBox;
    }

    public void setVueNewCheckBox(boolean vueNewCheckBox) {
        this.vueNewCheckBox = vueNewCheckBox;
    }

    public boolean isVueDetailCheckBox() {
        return vueDetailCheckBox;
    }

    public void setVueDetailCheckBox(boolean vueDetailCheckBox) {
        this.vueDetailCheckBox = vueDetailCheckBox;
    }

    public CodeGenerateSetting getSettings() {
        return settings;
    }

    public void setSettings(CodeGenerateSetting settings) {
        this.settings = settings;
    }

    public List<CurdTableInfo> getTableInfos() {
        return tableInfos;
    }

    public void setTableInfos(List<CurdTableInfo> tableInfos) {
        this.tableInfos = tableInfos;
    }
}
