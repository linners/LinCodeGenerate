package com.lin.ideaplugin.common.contants;

/**
 * 自动生成代码模板类型
 */
public enum VelocityFileType {
    MAPPER("mapper", "mapper code"),
    MAPPER_XML("mapper_xml", "mapper xml code"),
    MAPPER_XML_EXTEND("mapper_xml_extend", "mapper xml code"),
    ENTITY("entity", "entity code"),
    PARAM("param", "param code"),

    SERVICE("service", "service code"),
    SERVICE_IMPL("service_impl", "service code"),

    API("api", "api code"),
    API_IMPL("api_impl", "api code"),
    DTO("dto", "dto code"),

    CONTROLLER("controller", "controller"),

    VUE_LIST("vue_list", "controller"),
    VUE_ADD("vue_add", "controller"),
    VUE_DETAIL("vue_detail", "controller");

    private String type;
    private String description;

    VelocityFileType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String getDescriptionByKey(Integer key) {
        if (key == null) {
            return "";
        }
        VelocityFileType[] values = VelocityFileType.values();
        String retValue = null;
        for (VelocityFileType value : values) {
            if (value.type.equals(key)) {
                retValue = value.description;
                break;
            }
        }
        return retValue;
    }
}
