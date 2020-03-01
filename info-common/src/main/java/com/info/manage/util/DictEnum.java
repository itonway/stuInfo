package com.info.manage.util;

/**
 * @Author xxy
 * @Date 2019/7/9 10:30
 * @Description TODO
 **/
public enum DictEnum {

    SEX ( "性别", "1001" );


    DictEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    private String name;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
