package com.info.manage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class InfoTeacher extends BaseEntity {

    @NotBlank(message = "教师编号不能为空")
    @Length(max = 20,message = "教师编号不能大于20个字符")
    private String teacherNo;

    @NotBlank(message = "教师姓名不能为空")
    @Length(max = 20,message = "教师姓名不能大于20个字符")
    private String teacherName;

    private String teacherPassword;

    @Length(max = 20,message = "")
    private String teacherCard;

    private String teacherSex;

    private String teacherSexStr;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date teacherBirthDate;

    private String teacherBirthDateStr;

    @NotBlank(message = "手机号不能为空")
    @Length(max = 11,message = "手机号格式不正确")
    private String teacherMobile;

    @NotBlank(message = "邮箱不能为空")
    @Length(max = 20,message = "邮箱不能大于20个字符")
    private String teacherEmail;

    @Length(max = 255,message = "地址不能大于255个字符")
    private String teacherAddress;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startJobDate;

    private String startJobDateStr;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endJobDate;

    private String endJobDateStr;

    private String teacherStatus;

    @Length(max = 255,message = "备注不能大于255个字符")
    private String teacherRemark;


}