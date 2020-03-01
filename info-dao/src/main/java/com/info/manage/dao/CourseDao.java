package com.info.manage.dao;

import com.info.manage.entity.InfoCourse;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDao extends BaseDao<InfoCourse> {

    void deleteCourseBatch(@Param("ids") Long[] ids);

    List<InfoCourse> findCourseList(InfoCourse infoCourse);

    InfoCourse findInfoCourse(InfoCourse infoCourseParam);

}