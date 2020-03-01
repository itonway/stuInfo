package com.info.manage.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class InfoScore extends BaseEntity {

    @NotNull(message = "学生不能为空")
    private Long stuId;

    @NotNull(message = "课程不能为空")
    private Long courseId;

    @DecimalMax(message = "分数不能大于300", value = "300.00")
    private BigDecimal score;

    @Length(max = 255,message = "备注不能大于255个字符")
    private String scoreRemark;

    private String stuNo;

    private String stuName;

    private Long classId;

    private String classNo;

    private String className;

    private String courseName;


}