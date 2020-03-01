package com.info.manage.dao;

import com.info.manage.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleDao extends BaseDao<UserRole> {

    List<UserRole> findUserRoleListByUserId(Long userId);

    void deleteUserRoleByUserIds(@Param("userIds") Long[] userIds);

    void saveUserRole(@Param(value = "roleIds") Long[] roleIds, @Param(value = "userId") Long userId);

    void deleteUserRoleByRoleIds(@Param("roleIds") Long[] roleIds);

}