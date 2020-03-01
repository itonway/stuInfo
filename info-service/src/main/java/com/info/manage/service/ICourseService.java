package com.info.manage.service;

import com.github.pagehelper.PageInfo;
import com.info.manage.entity.InfoCourse;

public interface ICourseService {
    void saveAndUpdateCourse(InfoCourse infoCourse);

    void deleteById(Long id);

    void deleteCourseBatch(Long[] ids);

    InfoCourse findById(Long id);

    PageInfo<InfoCourse> findCourseList(Integer page, Integer limit, InfoCourse infoCourse);

}
