package com.info.manage.service.impl;

import com.info.manage.dao.UserRoleDao;
import com.info.manage.entity.UserRole;
import com.info.manage.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleServiceImpl implements IUserRoleService {
    @Autowired
    UserRoleDao userRoleDao;

    @Override
    public List<UserRole> findUserRoleListByUserId(Long userId) {
        return userRoleDao.findUserRoleListByUserId ( userId );
    }

    @Override
    @Transactional
    public void deleteUserRoleByRoleIds(Long[] ids) {
        userRoleDao.deleteUserRoleByRoleIds ( ids );
    }
}
