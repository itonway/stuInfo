package com.info.manage.entity;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * xxy
 * 2018/11/29
 * 描述
 */
@Data
public class BaseEntity implements Serializable {

    private Long id;

    private Long createrId;

    private Long updaterId;

    private String creater;

    private String updater;

    private Date createTime;

    private Date updateTime;

    private String createTimeStr;

    private String updateTimeStr;


}
