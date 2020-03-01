package com.info.manage.form;

import com.info.manage.entity.BaseEntity;

public class DictItemForm extends BaseEntity {
    private Long id;
    private String dictCode;
    private String dictItemCode;
    private String dictItemName;
    private String dictItemStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictItemCode() {
        return dictItemCode;
    }

    public void setDictItemCode(String dictItemCode) {
        this.dictItemCode = dictItemCode;
    }

    public String getDictItemName() {
        return dictItemName;
    }

    public void setDictItemName(String dictItemName) {
        this.dictItemName = dictItemName;
    }

    public String getDictItemStatus() {
        return dictItemStatus;
    }

    public void setDictItemStatus(String dictItemStatus) {
        this.dictItemStatus = dictItemStatus;
    }
}
