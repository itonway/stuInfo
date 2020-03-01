package com.info.manage.form;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;


@Data
public class TeacherExport extends BaseRowModel {
    @ExcelProperty(value = "教师编号", index = 0)
    private String teacherNo;
    @ExcelProperty(value = "教师姓名", index = 1)
    private String teacherName;
    @ExcelProperty(value = "身份证号", index = 2)
    private String teacherCard;
    @ExcelProperty(value = "性别", index = 3)
    private String teacherSexStr;
    @ExcelProperty(value = "出生年月", index = 4)
    private String teacherBirthDateStr;
    @ExcelProperty(value = "手机号", index = 5)
    private String teacherMobile;
    @ExcelProperty(value = "邮箱", index = 6)
    private String teacherEmail;
    @ExcelProperty(value = "入职日期", index = 7)
    private String startJobDateStr;
    @ExcelProperty(value = "离职日期", index = 8)
    private String endJobDateStr;
    @ExcelProperty(value = "家庭住址", index = 9)
    private String teacherAddress;
    @ExcelProperty(value = "创建人", index = 10)
    private String creater;
    @ExcelProperty(value = "创建时间", index = 11)
    private String createTimeStr;
    @ExcelProperty(value = "备注", index = 12)
    private String teacherRemark;

}
