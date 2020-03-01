package com.info.manage.entity;

import lombok.Data;

import java.util.List;

@Data
public class Menu extends BaseEntity{

    private String name;

    private Long pId;

    private String url;

    private String ramark;

    private String status;

    private String type;

    private String permission;

    private String icon;

    private boolean open;

    private boolean parent;

    private boolean checked;

    private List<Menu> children;

    private List<Long> menuIds;


}