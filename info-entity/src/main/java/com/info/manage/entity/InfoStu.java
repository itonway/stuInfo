package com.info.manage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class InfoStu extends BaseEntity {

    private Long classId;

    private String classNo;

    private String className;

    @NotBlank(message = "学生编号不能为空")
    @Length( max = 20,message = "学生编号不能大于20个汉字")
    private String stuNo;

    @NotBlank(message = "学生姓名不能为空")
    @Length( max = 50,message = "学生姓名不能大于50个汉字")
    private String stuName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date stuBirthDate;

    private Integer stuAge;

    @NotBlank(message = "身份证号码不能为空")
    @Length(max = 18,message = "身份证号码不能大于18")
    private String stuCard;

    private String stuSex;

    private String stuSexStr;

    @NotBlank(message = "邮箱不能为空")
    @Length(max = 50,message = "邮箱不能大于50")
    private String stuEmail;

    @NotBlank(message = "手机号不能为空")
    private String stuMobile;

    private String stuAddress;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date stuStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date stuEndDate;

    private String stuStatus;

    private String stuRemark;

    private String stuBirthDateStr;

    private String stuStartDateStr;

    private String stuEndDateStr;

    private String value;

    private String title;

    private Long assignClassId;

    private Long[] classIds;

    private Long[] ids;

}