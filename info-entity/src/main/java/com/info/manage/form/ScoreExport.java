package com.info.manage.form;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class ScoreExport extends BaseRowModel {
    @ExcelProperty(value = "学号", index = 0)
    private String stuNo;
    @ExcelProperty(value = "学生姓名", index = 1)
    private String stuName;
    @ExcelProperty(value = "班级编号", index = 2)
    private String classNo;
    @ExcelProperty(value = "班级名称", index = 3)
    private String className;
    @ExcelProperty(value = "课程名称", index = 4)
    private String courseName;
    @ExcelProperty(value = "分数", index = 5)
    private BigDecimal score;
    @ExcelProperty(value = "备注", index = 6)
    private String scoreRemark;
    @ExcelProperty(value = "创建人", index = 7)
    private String creater;
    @ExcelProperty(value = "创建时间", index = 8)
    private String createTimeStr;


}
