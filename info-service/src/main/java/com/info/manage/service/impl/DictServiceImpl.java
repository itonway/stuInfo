package com.info.manage.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.info.manage.dao.DictDao;
import com.info.manage.dao.UserDao;
import com.info.manage.entity.Dict;
import com.info.manage.entity.User;
import com.info.manage.form.DictForm;
import com.info.manage.form.UserForm;
import com.info.manage.service.IDictService;
import com.info.manage.service.IUserService;
import com.info.manage.util.Dates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DictServiceImpl implements IDictService {
    @Autowired
    DictDao dictDao;

    @Override
    public List<Dict> findDictList(DictForm dictForm) {
        return dictDao.findDictList ( dictForm );
    }

    @Override
    public Dict findDictByDictCode(String dictCode) {
        return dictDao.findDictByDictCode ( dictCode );
    }
}
