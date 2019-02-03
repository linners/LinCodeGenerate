package com.lin.ideaplugin.contants;

public enum ActionType {
    MAPPER(1, "mapper code"),
    MAPPER_XML(2, "mapper xml code"),
    ENTITY(3, "entity code"),
    PARAM(4, "param code"),

    SERVICE(5, "service code"),
    SERVICE_IMPL(6, "service code"),

    API(7, "api code"),
    API_IMPL(8, "api code"),
    DTO(9, "dto code"),

    CONTROLLER(10, "controller"),

    VUE_LIST(11, "controller"),
    VUE_ADD(12, "controller"),
    VUE_DETAIL(13, "controller");

    private Integer type;
    private String description;

    ActionType(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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
        ActionType[] values = ActionType.values();
        String retValue = null;
        for (ActionType value : values) {
            if (value.type.equals(key)) {
                retValue = value.description;
                break;
            }
        }
        return retValue;
    }
}
