package com.info.manage.service;

import com.github.pagehelper.PageInfo;
import com.info.manage.entity.InfoStu;


public interface IStuService {

    void deleteById(Long id);

    void deleteStuBatch(Long[] ids);

    InfoStu findById(Long id);

    PageInfo<InfoStu> findStuList(Integer page, Integer limit, InfoStu infoStu);

    void saveAndUpdateStu(InfoStu infoStu);
}
