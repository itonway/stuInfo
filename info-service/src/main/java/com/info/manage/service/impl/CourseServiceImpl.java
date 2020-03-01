package com.info.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.info.manage.dao.CourseDao;
import com.info.manage.dao.TeacherDao;
import com.info.manage.dao.UserDao;
import com.info.manage.entity.InfoCourse;
import com.info.manage.entity.InfoTeacher;
import com.info.manage.entity.User;
import com.info.manage.service.ICourseService;
import com.info.manage.util.Dates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author xxy
 * @Date 2019/7/15 9:59
 * @Description TODO
 **/
@Service
public class CourseServiceImpl implements ICourseService {
    @Autowired
    CourseDao courseDao;
    @Autowired
    UserDao userDao;
    @Autowired
    TeacherDao teacherDao;

    @Override
    public void saveAndUpdateCourse(InfoCourse infoCourse) {
        if (infoCourse.getId () != null) {
            infoCourse.setUpdateTime ( new Date () );
            courseDao.update ( infoCourse );
        } else {
            infoCourse.setCreateTime ( new Date () );
            courseDao.insert ( infoCourse );
        }
    }

    @Override
    public void deleteById(Long id) {
        courseDao.deleteById ( id );
    }

    @Override
    public void deleteCourseBatch(Long[] ids) {
        courseDao.deleteCourseBatch ( ids );
    }

    @Override
    public InfoCourse findById(Long id) {
        return courseDao.findById ( id );
    }

    @Override
    public PageInfo<InfoCourse> findCourseList(Integer page, Integer limit, InfoCourse infoCourse) {
        if (page != null && limit != null) {
            PageHelper.startPage ( page, limit, "id desc" );
        }
        List<InfoCourse> infoClassList = courseDao.findCourseList ( infoCourse );
        for (InfoCourse infoCourse1 : infoClassList) {
            User user = userDao.findById ( infoCourse1.getCreaterId () );
            infoCourse1.setCreater ( user == null ? "" : user.getUserName () );
            infoCourse1.setCreateTimeStr ( Dates.getDateTime ( infoCourse1.getCreateTime (), Dates.DEFAULT_DATETIME_FORMAT ) );
            InfoTeacher infoTeacher = teacherDao.findById ( infoCourse1.getTeacherId () );
            infoCourse1.setTeacherName ( infoTeacher == null ? "" : infoTeacher.getTeacherName () );
        }
        PageInfo<InfoCourse> pageInfo = new PageInfo<> ( infoClassList );
        return pageInfo;
    }

}
