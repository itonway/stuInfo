package com.info.manage.dao;

import com.info.manage.entity.RoleMenu;
import com.info.manage.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMenuDao {
    int deleteById(Long id);

    int insert(RoleMenu record);

    RoleMenu selectById(Long id);

    int update(RoleMenu record);

    List<RoleMenu> selectRoleMenuByRoleId(Long roleId);

    void deleteRoleMenuByRoleId(Long roleId);

    void saveRoleMenu(@Param("menuIds") Long[] menuIds, @Param("roleId") Long roleId);

    List<RoleMenu> findRoleMenuListByRoleIds(@Param("userRoleList") List<UserRole> userRoleList);

}