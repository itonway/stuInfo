package com.info.manage.form;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class ScoreImport extends BaseRowModel {
    @ExcelProperty(value = "学号", index = 0)
    private String stuNo;
    @ExcelProperty(value = "学生姓名", index = 1)
    private String stuName;
    @ExcelProperty(value = "课程名称", index = 2)
    private String courseName;
    @ExcelProperty(value = "分数", index = 3)
    private BigDecimal score;
    @ExcelProperty(value = "备注", index = 4)
    private String scoreRemark;
}
