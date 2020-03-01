package com.info.manage.form;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.Date;


@Data
public class UserExport extends BaseRowModel {
    @ExcelProperty(value = "用户ID", index = 0)
    private Long id;
    @ExcelProperty(value = "姓名", index = 1)
    private String userName;
    @ExcelProperty(value = "年龄", index = 2)
    private int age;
    @ExcelProperty(value = "性别", index = 3)
    private String sex;
    @ExcelProperty(value = "邮箱", index = 4)
    private String email;
    @ExcelProperty(value = "手机", index = 5)
    private String mobile;
    @ExcelProperty(value = "创建时间", index = 6, format = "yyyy/MM/dd")
    private Date createTime;
    @ExcelProperty(value = "修改时间", index = 7, format = "yyyy-MM-dd")
    private Date updateTime;


}
