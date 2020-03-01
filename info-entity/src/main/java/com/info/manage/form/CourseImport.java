package com.info.manage.form;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;


@Data
public class CourseImport extends BaseRowModel {
    @ExcelProperty(value = "课程编号", index = 0)
    private String courseNo;
    @ExcelProperty(value = "课程名称", index = 1)
    private String courseName;
    @ExcelProperty(value = "备注", index = 2)
    private String courseRemark;
}
