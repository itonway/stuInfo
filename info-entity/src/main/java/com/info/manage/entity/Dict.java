package com.info.manage.entity;

import lombok.Data;

@Data
public class Dict extends BaseEntity {
    private String dictName;
    private String dictCode;
    private String dictStatus;
}
