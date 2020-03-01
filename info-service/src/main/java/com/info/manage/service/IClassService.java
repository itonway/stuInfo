package com.info.manage.service;

import com.github.pagehelper.PageInfo;
import com.info.manage.entity.InfoClass;

public interface IClassService {

    void saveAndUpdateClass(InfoClass infoClass);

    void deleteById(Long id);

    void deleteClassBatch(Long[] ids);

    InfoClass findById(Long id);

    PageInfo<InfoClass> findClassList(Integer page, Integer limit, InfoClass infoClass);
}
