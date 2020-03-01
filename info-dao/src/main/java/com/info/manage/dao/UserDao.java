package com.info.manage.dao;

import com.info.manage.entity.User;
import com.info.manage.form.UserForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends BaseDao<User> {
    List<User> findUserList(UserForm userForm);

    User findByLoginName(String loginName);

    void deleteUserBatch(@Param("ids") Long[] ids);

}
