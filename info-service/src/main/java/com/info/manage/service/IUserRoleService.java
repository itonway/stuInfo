package com.info.manage.service;

import com.info.manage.entity.UserRole;

import java.util.List;

public interface IUserRoleService {
    List<UserRole> findUserRoleListByUserId(Long userId);

    void deleteUserRoleByRoleIds(Long[] ids);
}
