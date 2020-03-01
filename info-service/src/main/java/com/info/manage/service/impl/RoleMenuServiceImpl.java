package com.info.manage.service.impl;

import com.info.manage.dao.RoleMenuDao;
import com.info.manage.entity.RoleMenu;
import com.info.manage.service.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xxy
 * @ClassName RoleMenuServiceImpl
 * @Description todo
 * @Date 2019/3/12 11:08
 **/
@Service
public class RoleMenuServiceImpl implements IRoleMenuService {
    @Autowired
    RoleMenuDao roleMenuDao;

    @Override
    public List<RoleMenu> selectRoleMenuByRoleId(Long roleId) {
        return roleMenuDao.selectRoleMenuByRoleId ( roleId );
    }

}
