package com.info.manage.service;

import com.github.pagehelper.PageInfo;
import com.info.manage.entity.User;
import com.info.manage.form.UserForm;

import java.util.List;

public interface IUserService {

    void saveAndUpdateUser(User user);

    void deleteById(Long id);

    User findById(Long id);

    PageInfo<User> findUserList(int page, int limit, UserForm userForm);

    User findByLoginName(String loginName);

    void setMenuAndRole(String loginName);

    List<User> findUserAll();

    void deleteUserBatch(Long[] ids);

    void changeUserPwd(User user);
}
