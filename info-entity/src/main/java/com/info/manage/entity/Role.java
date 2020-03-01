package com.info.manage.entity;

import lombok.Data;

@Data
public class Role extends BaseEntity{

    private String name;

    private String status;

    private String remark;

    private Long[] selectMenuIds;

}