package com.info.manage.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class InfoClass extends BaseEntity {

    @NotBlank(message = "班级编号不能为空")
    @Length(max = 20,message = "班级编号不能大于20个字符")
    private String classNo;

    @NotBlank(message = "班级名称不能为空")
    @Length(max = 20,message = "班级名称不能大于20个字符")
    private String className;

    @Length(max = 255,message = "备注不能大于255个字符")
    private String classRemark;
}