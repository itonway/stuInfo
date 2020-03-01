package com.info.manage.entity;

import com.info.manage.validator.ChangePwdValidated;
import com.info.manage.validator.FirstValidated;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author xxy
 * @Description //TODO   用户信息
 * @Date 2019/7/16 11:36
 **/
@Data
public class User extends BaseEntity {

    private String userName;
    @NotBlank(message = "密码不能为空", groups = FirstValidated.class)
    private String password;
    @NotBlank(message = "用户名不能为空", groups = FirstValidated.class)
    private String loginName;
    private Integer age;
    private String sex;
    private String sexStr;
    private String email;
    private String mobile;
    @NotBlank(message = "验证码不能为空", groups = FirstValidated.class)
    private String code;
    List<Role> roleLsit;
    List<Menu> menuList;

    Long[] selectRoleIds;

    @NotBlank(message = "老密码不能为空", groups = ChangePwdValidated.class)
    private String oldPwd;

    @NotBlank(message = "新密码不能为空", groups = ChangePwdValidated.class)
    private String newPwd;

    @NotBlank(message = "确认密码不能为空", groups = ChangePwdValidated.class)
    private String confirmNewPwd;

}
