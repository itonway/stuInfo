package com.info.manage.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class InfoCourse extends BaseEntity {

    private Long teacherId;

    private String teacherName;

    @NotBlank(message = "课程编号不能为空")
    @Length(max = 20,message = "课程编号不能大于20个字符")
    private String courseNo;

    @NotBlank(message = "课程名称不能为空")
    @Length(max = 50,message = "课程名称不能大于50个字符")
    private String courseName;

    @Length(max = 255,message = "备注不能大于255个字符")
    private String courseRemark;


}