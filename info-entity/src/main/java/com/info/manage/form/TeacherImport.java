package com.info.manage.form;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class TeacherImport extends BaseRowModel {
    @ExcelProperty(value = "教师编号", index = 0)
    private String teacherNo;
    @ExcelProperty(value = "教师姓名", index = 1)
    private String teacherName;
    @ExcelProperty(value = "身份证号", index = 2)
    private String teacherCard;
    @ExcelProperty(value = "性别", index = 3)
    private String teacherSex;
    @ExcelProperty(value = "出生年月", index = 4)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date teacherBirthDate;
    @ExcelProperty(value = "手机号", index = 5)
    private String teacherMobile;
    @ExcelProperty(value = "邮箱", index = 6)
    private String teacherEmail;
    @ExcelProperty(value = "入职日期", index = 7)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startJobDate;
    @ExcelProperty(value = "离职日期", index = 8)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endJobDate;
    @ExcelProperty(value = "家庭住址", index = 9)
    private String teacherAddress;
    @ExcelProperty(value = "备注", index = 10)
    private String teacherRemark;
}
