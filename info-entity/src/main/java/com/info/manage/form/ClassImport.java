package com.info.manage.form;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;


@Data
public class ClassImport extends BaseRowModel {
    @ExcelProperty(value = "班级编号", index = 0)
    private String classNo;
    @ExcelProperty(value = "班级名称", index = 1)
    private String className;
    @ExcelProperty(value = "备注", index = 2)
    private String classRemark;
}
