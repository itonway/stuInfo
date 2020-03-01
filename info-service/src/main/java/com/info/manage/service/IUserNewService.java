package com.info.manage.service;

import com.info.manage.entity.User;

import java.util.List;

/**
 * @author xxy
 * @ClassName IUserNewService
 * @Description todo
 * @Date 2019/3/18 14:54
 **/
public interface IUserNewService {
    List<User> findByOrgId(Long orgId);
}
