package com.info.manage.form;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;


@Data
public class ClassExport extends BaseRowModel {
    @ExcelProperty(value = "班级编号", index = 0)
    private String classNo;
    @ExcelProperty(value = "班级名称", index = 1)
    private String className;
    @ExcelProperty(value = "创建人", index = 2)
    private String creater;
    @ExcelProperty(value = "创建时间", index = 3)
    private String createTimeStr;
    @ExcelProperty(value = "备注", index = 4)
    private String classRemark;

}
