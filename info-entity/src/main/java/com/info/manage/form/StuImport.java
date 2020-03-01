package com.info.manage.form;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class StuImport extends BaseRowModel {
    @ExcelProperty(value = "学号", index = 0)
    private String stuNo;
    @ExcelProperty(value = "姓名", index = 1)
    private String stuName;
    @ExcelProperty(value = "年龄", index = 2)
    private Integer stuAge;
    @ExcelProperty(value = "性别", index = 3)
    private String stuSex;

    @ExcelProperty(value = "出生年月", index = 4)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date stuBirthDate;

    @ExcelProperty(value = "身份证号", index = 5)
    private String stuCard;
    @ExcelProperty(value = "手机号", index = 6)
    private String stuMobile;
    @ExcelProperty(value = "邮箱", index = 7)
    private String stuEmail;

    @ExcelProperty(value = "入学日期", index = 8)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date stuStartDate;

    @ExcelProperty(value = "毕业日期", index = 9)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date stuEndDate;

    @ExcelProperty(value = "家庭住址", index = 10)
    private String stuAddress;
    @ExcelProperty(value = "备注", index = 11)
    private String stuRemark;
}
