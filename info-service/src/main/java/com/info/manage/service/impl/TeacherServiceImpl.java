package com.info.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.info.manage.dao.DictItemDao;
import com.info.manage.dao.TeacherDao;
import com.info.manage.dao.UserDao;
import com.info.manage.entity.DictItem;
import com.info.manage.entity.InfoTeacher;
import com.info.manage.entity.User;
import com.info.manage.service.ITeacherService;
import com.info.manage.util.Dates;
import com.info.manage.util.DictEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author xxy
 * @Date 2019/7/15 15:23
 * @Description TODO
 **/
@Service
public class TeacherServiceImpl implements ITeacherService {
    @Autowired
    TeacherDao teacherDao;
    @Autowired
    UserDao userDao;
    @Autowired
    DictItemDao dictItemDao;


    @Override
    public PageInfo<InfoTeacher> findTeacherList(Integer page, Integer limit, InfoTeacher infoTeacher) {
        if (page != null && limit != null) {
            PageHelper.startPage ( page, limit, "id desc" );
        }
        List<InfoTeacher> teacherList = teacherDao.findTeacherList ( infoTeacher );
        for (InfoTeacher teacher : teacherList) {
            User user = userDao.findById ( teacher.getCreaterId () );
            teacher.setCreater ( user == null ? "" : user.getUserName () );
            teacher.setCreateTimeStr ( Dates.getDateTime ( teacher.getCreateTime (), Dates.DEFAULT_DATETIME_FORMAT ) );
            DictItem dictitem = dictItemDao.findDictItemByDictCodeAndItemCode ( DictEnum.SEX.getValue (), teacher.getTeacherSex () );
            teacher.setTeacherSexStr ( dictitem == null ? "" : dictitem.getDictItemName () );
            teacher.setStartJobDateStr ( teacher.getStartJobDate () == null ? "" : Dates.getDateTime ( teacher.getStartJobDate (), Dates.DEFAULT_DATE_FORMAT ) );
            teacher.setEndJobDateStr ( teacher.getEndJobDate () == null ? "" : Dates.getDateTime ( teacher.getEndJobDate (), Dates.DEFAULT_DATE_FORMAT ) );
            teacher.setTeacherBirthDateStr ( teacher.getTeacherBirthDate () == null ? "" : Dates.getDateTime ( teacher.getTeacherBirthDate (), Dates.DEFAULT_DATE_FORMAT ) );
        }
        PageInfo<InfoTeacher> pageInfo = new PageInfo<> ( teacherList );
        return pageInfo;
    }

    @Override
    public void deleteById(Long id) {
        teacherDao.deleteById ( id );
    }

    @Override
    public void deleteTeacherBatch(Long[] ids) {
        teacherDao.deleteTeacherBatch ( ids );
    }

    @Override
    public void saveAndUpdateTeacher(InfoTeacher infoTeacher) {
        if (infoTeacher.getId () != null) {
            infoTeacher.setUpdateTime ( new Date () );
            teacherDao.update ( infoTeacher );
        } else {
            infoTeacher.setCreateTime ( new Date () );
            teacherDao.insert ( infoTeacher );
        }
    }
}
