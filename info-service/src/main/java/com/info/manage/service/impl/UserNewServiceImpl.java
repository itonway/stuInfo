package com.info.manage.service.impl;

import com.info.manage.dao.UserDaoNew;
import com.info.manage.entity.User;
import com.info.manage.service.IUserNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xxy
 * @ClassName UserNewServiceIMpl
 * @Description todo
 * @Date 2019/3/18 14:54
 **/
@Service
public class UserNewServiceImpl implements IUserNewService {
    @Autowired
    UserDaoNew userDaoNew;

    public void add(User user) {
        userDaoNew.add ( user );
    }

    public void update(User user) {
        userDaoNew.update ( user );
    }

    public void deleteById(Long id) {
        userDaoNew.deleteById ( id );
    }

    public User findById(Long id) {
        return userDaoNew.findById ( id );
    }

    @Override
    public List<User> findByOrgId(Long orgId) {
        return null;
    }
}
