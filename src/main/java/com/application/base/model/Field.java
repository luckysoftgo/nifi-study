package com.application.base.model;

/**
 * @author : 孤狼
 * @NAME: Field.
 * @DESC: 字段描述.
 **/
public class Field {

    private String name;
    private String[] type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }
}
