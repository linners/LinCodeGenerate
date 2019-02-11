package com.lin.ideaplugin.extension;

import java.util.Map;

public class SettingConfigure {

    private String mapperPath;          // mapper路径
    private String entityPath;          // entity路径
    private String paramPath;           // param路径
    private String xmlPath;             // xml路径
    private String servicePath;         // service路径
    private String controllerPath;      // controller路径
    private String vuePagePath;         // vue页面路径

    private String basePackage;         // 基础包
    private String mapperPackage;       // mapper包
    private String entityPackage;       // entity包
    private String paramPackage;        // param包
    private String servicePackage;      // service包
    private String controllerPackage;   // controller包

    private Map<String, String> codeTemplateMap;    // 代码模板

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getMapperPackage() {
        return mapperPackage;
    }

    public void setMapperPackage(String mapperPackage) {
        this.mapperPackage = mapperPackage;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getParamPackage() {
        return paramPackage;
    }

    public void setParamPackage(String paramPackage) {
        this.paramPackage = paramPackage;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getVuePagePath() {
        return vuePagePath;
    }

    public void setVuePagePath(String vuePagePath) {
        this.vuePagePath = vuePagePath;
    }

    public Map<String, String> getCodeTemplateMap() {
        return codeTemplateMap;
    }

    public void setCodeTemplateMap(Map<String, String> codeTemplateMap) {
        this.codeTemplateMap = codeTemplateMap;
    }

    public String getMapperPath() {
        return mapperPath;
    }

    public void setMapperPath(String mapperPath) {
        this.mapperPath = mapperPath;
    }

    public String getEntityPath() {
        return entityPath;
    }

    public void setEntityPath(String entityPath) {
        this.entityPath = entityPath;
    }

    public String getParamPath() {
        return paramPath;
    }

    public void setParamPath(String paramPath) {
        this.paramPath = paramPath;
    }

    public String getServicePath() {
        return servicePath;
    }

    public void setServicePath(String servicePath) {
        this.servicePath = servicePath;
    }

    public String getControllerPath() {
        return controllerPath;
    }

    public void setControllerPath(String controllerPath) {
        this.controllerPath = controllerPath;
    }
}
