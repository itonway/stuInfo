package com.info.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.info.manage.dao.*;
import com.info.manage.entity.DictItem;
import com.info.manage.entity.InfoClass;
import com.info.manage.entity.InfoStu;
import com.info.manage.entity.User;
import com.info.manage.service.IStuService;
import com.info.manage.util.Dates;
import com.info.manage.util.DictEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author xxy
 * @Date 2019/7/8 10:55
 * @Description TODO
 **/
@Service
public class StuServiceImpl implements IStuService {
    @Autowired
    StuDao stuDao;
    @Autowired
    UserDao userDao;
    @Autowired
    DictItemDao dictItemDao;
    @Autowired
    ClassDao classDao;
    @Autowired
    ScoreDao scoreDao;

    @Override
    public void deleteById(Long id) {
        InfoStu infoStu = stuDao.findById ( id );
        scoreDao.deleteScoreBatchByStuList ( Collections.singletonList ( infoStu ) );//先删除成绩
        stuDao.deleteById ( id );//再删除学生
    }

    @Override
    public void deleteStuBatch(Long[] ids) {
        InfoStu infoStuParams = new InfoStu ();
        infoStuParams.setIds ( ids );
        List<InfoStu> stuList = stuDao.findStuList ( infoStuParams );
        scoreDao.deleteScoreBatchByStuList ( stuList );//先删除成绩
        stuDao.deleteStuBatch ( ids );//再删除学生
    }

    @Override
    public InfoStu findById(Long id) {
        return stuDao.findById ( id );
    }

    @Override
    public PageInfo<InfoStu> findStuList(Integer page, Integer limit, InfoStu infoStu) {
        if (page != null && limit != null) {
            PageHelper.startPage ( page, limit, "id desc" );
        }
        List<InfoStu> infoStuList = stuDao.findStuList ( infoStu );
        for (InfoStu stu : infoStuList) {
            stu.setStuBirthDateStr ( stu.getStuBirthDate () == null ? "" : Dates.getDateTime ( stu.getStuBirthDate (), Dates.DEFAULT_DATE_FORMAT ) );
            stu.setStuStartDateStr ( stu.getStuStartDate () == null ? "" : Dates.getDateTime ( stu.getStuStartDate (), Dates.DEFAULT_DATE_FORMAT ) );
            stu.setStuEndDateStr ( stu.getStuEndDate () == null ? "" : Dates.getDateTime ( stu.getStuEndDate (), Dates.DEFAULT_DATE_FORMAT ) );
            User user = userDao.findById ( stu.getCreaterId () );
            stu.setCreater ( user == null ? "" : user.getUserName () );
            DictItem dictitem = dictItemDao.findDictItemByDictCodeAndItemCode ( DictEnum.SEX.getValue (), stu.getStuSex () );
            stu.setStuSexStr ( dictitem == null ? "" : dictitem.getDictItemName () );
            stu.setCreateTimeStr ( Dates.getDateTime ( stu.getCreateTime (), Dates.DEFAULT_DATETIME_FORMAT ) );
            InfoClass infoClass = classDao.findById ( stu.getClassId () );
            stu.setClassName ( infoClass == null ? "" : infoClass.getClassName () );
            stu.setClassNo ( infoClass == null ? "" : infoClass.getClassNo () );
            stu.setValue ( stu.getId ().toString () );
            stu.setTitle ( stu.getStuName () );
        }
        PageInfo<InfoStu> pageList = new PageInfo<> ( infoStuList );
        return pageList;
    }

    @Override
    public void saveAndUpdateStu(InfoStu infoStu) {
        if (infoStu.getId () != null) {
            infoStu.setUpdateTime ( new Date () );
            stuDao.update ( infoStu );
        } else {
            infoStu.setCreateTime ( new Date () );
            stuDao.insert ( infoStu );
        }
    }
}
