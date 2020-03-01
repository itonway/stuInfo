package com.info.manage.service;

import com.github.pagehelper.PageInfo;
import com.info.manage.entity.InfoTeacher;

public interface ITeacherService {
    PageInfo<InfoTeacher> findTeacherList(Integer page, Integer limit, InfoTeacher infoTeacher);

    void deleteById(Long id);

    void deleteTeacherBatch(Long[] ids);

    void saveAndUpdateTeacher(InfoTeacher infoTeacher);
}
