package com.info.manage.form;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class UserImportForm extends BaseRowModel {
    @ExcelProperty(value = "姓名", index = 0)
    private String userName;
    @ExcelProperty(value = "登录名", index = 1)
    private String loginName;
    @ExcelProperty(value = "性别", index = 2)
    private String sex;
    @ExcelProperty(value = "邮箱", index = 3)
    private String email;
    @ExcelProperty(value = "手机", index = 4)
    private String mobile;

}
