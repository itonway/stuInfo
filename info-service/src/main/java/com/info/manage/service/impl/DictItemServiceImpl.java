package com.info.manage.service.impl;


import com.info.manage.dao.DictItemDao;
import com.info.manage.entity.DictItem;
import com.info.manage.service.IDictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictItemServiceImpl implements IDictItemService {
    @Autowired
    DictItemDao dictItemDao;

    @Override
    public List<DictItem> findDictItemListByDictCode(String dictCode) {
        return dictItemDao.findDictItemListByDictCode ( dictCode );
    }

    @Override
    public DictItem getDictItemCodeByItemName(String dictCode, String dictItemName) {
        return dictItemDao.findDictItemCodeByItemName ( dictCode, dictItemName );
    }


}
