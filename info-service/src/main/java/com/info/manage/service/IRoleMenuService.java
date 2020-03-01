package com.info.manage.service;

import com.info.manage.entity.RoleMenu;

import java.util.List;

public interface IRoleMenuService {

    /**
     * 根据角色id查询权限菜单
     * @param roleId
     * @return
     */
    List<RoleMenu> selectRoleMenuByRoleId(Long roleId);

}
