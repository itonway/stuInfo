package com.info.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.info.manage.dao.RoleDao;
import com.info.manage.dao.RoleMenuDao;
import com.info.manage.dao.UserDao;
import com.info.manage.dao.UserRoleDao;
import com.info.manage.entity.Role;
import com.info.manage.entity.User;
import com.info.manage.service.IRoleService;
import com.info.manage.service.IUserRoleService;
import com.info.manage.util.Dates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    RoleDao roleDao;
    @Autowired
    UserDao userDao;
    @Autowired
    RoleMenuDao roleMenuDao;
    @Autowired
    UserRoleDao userRoleDao;
    @Autowired
    IUserRoleService userRoleService;

    @Override
    @Transactional
    public void deleteById(Long id) {
        roleDao.deleteById ( id );
        roleMenuDao.deleteRoleMenuByRoleId ( id );//删除角色权限
        Long[] ids = new Long[1];
        ids[0] = id;
        userRoleService.deleteUserRoleByRoleIds ( ids );//删除用户拥有的角色
    }

    @Override
    public void deleteRoleBatch(Long[] ids) {
        roleDao.deleteRoleBatch ( ids );
        userRoleDao.deleteUserRoleByRoleIds ( ids );//删除用户拥有的角色
    }

    @Override
    public PageInfo<Role> findRoleListPage(int page, int limit,Role role) {
        PageHelper.startPage ( page, limit, "id desc" );
        List<Role> roleList = roleDao.findRoleList ( role );
        for (Role role1 : roleList){
            User user = userDao.findById ( role1.getCreaterId () );
            role1.setCreater (user == null ? "admin" : user.getUserName () );
            role1.setCreateTimeStr ( Dates.getDateTime ( role1.getCreateTime (), Dates.DEFAULT_DATETIME_FORMAT ) );
        }
        PageInfo<Role> pageInfo = new PageInfo<> ( roleList );
        return pageInfo;
    }

    @Override
    public List<Role> findRoleAllList(Role role) {
        return roleDao.findRoleList ( role );
    }

    @Override
    public Role selectById(Long id) {
        return roleDao.findById ( id );
    }

    @Override
    public void saveAndUpdateRole(Role role) {
        Long[] selectMenuIds = role.getSelectMenuIds ();
        Long roleId = role.getId ();
        if (roleId != null) {
            role.setUpdateTime ( new Date () );
            roleDao.update ( role );
            if (selectMenuIds != null && selectMenuIds.length > 0) {
                roleMenuDao.deleteRoleMenuByRoleId ( roleId );
                roleMenuDao.saveRoleMenu ( selectMenuIds, roleId );
            }
        } else {
            role.setCreateTime ( new Date () );
            roleDao.insert ( role );
            if (selectMenuIds != null) {
                roleMenuDao.saveRoleMenu ( selectMenuIds, role.getId () );
            }
        }
    }
}
