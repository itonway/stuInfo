package com.info.manage.dao;

import com.info.manage.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends BaseDao<Role> {

    void deleteRoleBatch(@Param("ids") Long[] ids);

    List<Role> findRoleList(Role role);


}