package com.info.manage.service;

import com.github.pagehelper.PageInfo;
import com.info.manage.entity.Role;

import java.util.List;


public interface IRoleService {

    void deleteById(Long id);

    void deleteRoleBatch(Long[] ids);

    PageInfo<Role> findRoleListPage(int page, int limit,Role role);

    List<Role> findRoleAllList(Role role);

    Role selectById(Long id);

    void saveAndUpdateRole(Role role);
}
