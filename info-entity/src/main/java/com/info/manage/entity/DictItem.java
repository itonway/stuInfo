package com.info.manage.entity;

import lombok.Data;

@Data
public class DictItem extends BaseEntity {

    private String dictCode;

    private String dictItemCode;

    private String dictItemName;

    private String dictItemStatus;

}
