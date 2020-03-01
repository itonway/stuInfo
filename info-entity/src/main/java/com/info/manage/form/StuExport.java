package com.info.manage.form;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;


@Data
public class StuExport extends BaseRowModel {
    @ExcelProperty(value = "学号", index = 0)
    private String stuNo;
    @ExcelProperty(value = "姓名", index = 1)
    private String stuName;
    @ExcelProperty(value = "年龄", index = 2)
    private Integer stuAge;
    @ExcelProperty(value = "性别", index = 3)
    private String stuSexStr;
    @ExcelProperty(value = "出生年月", index = 4)
    private String stuBirthDateStr;
    @ExcelProperty(value = "身份证号", index = 5)
    private String stuCard;
    @ExcelProperty(value = "手机号", index = 6)
    private String stuMobile;
    @ExcelProperty(value = "邮箱", index = 7)
    private String stuEmail;
    @ExcelProperty(value = "入学日期", index = 8)
    private String stuStartDateStr;
    @ExcelProperty(value = "毕业日期", index = 9)
    private String stuEndDateStr;
    @ExcelProperty(value = "家庭住址", index = 10)
    private String stuAddress;
    @ExcelProperty(value = "创建人", index = 11)
    private String creater;
    @ExcelProperty(value = "创建时间", index = 12)
    private String createTimeStr;
    @ExcelProperty(value = "备注", index = 13)
    private String stuRemark;

}
