package com.info.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.info.manage.dao.ClassDao;
import com.info.manage.dao.ScoreDao;
import com.info.manage.dao.StuDao;
import com.info.manage.dao.UserDao;
import com.info.manage.entity.InfoClass;
import com.info.manage.entity.InfoStu;
import com.info.manage.entity.User;
import com.info.manage.service.IClassService;
import com.info.manage.util.Dates;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author xxy
 * @Date 2019/7/11 10:50
 * @Description TODO
 **/
@Service
public class ClassServiceImpl implements IClassService {
    @Autowired
    ClassDao classDao;
    @Autowired
    UserDao userDao;
    @Autowired
    StuDao stuDao;
    @Autowired
    ScoreDao scoreDao;

    @Override
    public void saveAndUpdateClass(InfoClass infoClass) {
        if (infoClass.getId () != null) {
            classDao.update ( infoClass );
        } else {
            classDao.insert ( infoClass );
        }
    }

    @Override
    public void deleteById(Long id) {
        classDao.deleteById ( id );//删除班级
        InfoStu infoStuParam = new InfoStu ();
        infoStuParam.setClassId ( id );
        List<InfoStu> stuList = stuDao.findStuList ( infoStuParam );
        if(CollectionUtils.isNotEmpty ( stuList )){
            scoreDao.deleteScoreBatchByStuList ( stuList );//先删除学生成绩
        }
        Long[] ids = new Long[1];
        ids[0] = id;
        stuDao.deleteByClassIds ( ids );//删除学生
    }

    @Override
    public void deleteClassBatch(Long[] ids) {
        classDao.deleteClassBatch ( ids );//删除班级
        InfoStu infoStuParam = new InfoStu ();
        infoStuParam.setClassIds ( ids );
        List<InfoStu> stuList = stuDao.findStuList ( infoStuParam );
        if(CollectionUtils.isNotEmpty ( stuList )){
            scoreDao.deleteScoreBatchByStuList ( stuList );//先删除学生成绩
        }
        stuDao.deleteByClassIds ( ids );//再删除学生
    }

    @Override
    public InfoClass findById(Long id) {
        return classDao.findById ( id );
    }

    @Override
    public PageInfo<InfoClass> findClassList(Integer page, Integer limit, InfoClass infoClass) {
        if (page != null && limit != null) {
            PageHelper.startPage ( page, limit, "id desc" );
        }
        List<InfoClass> infoClassList = classDao.findClassList ( infoClass );
        for (InfoClass aClass : infoClassList) {
            User user = userDao.findById ( aClass.getCreaterId () );
            aClass.setCreater ( user.getUserName () );
            aClass.setCreateTimeStr ( Dates.getDateTime ( aClass.getCreateTime (), Dates.DEFAULT_DATETIME_FORMAT ) );
        }
        PageInfo<InfoClass> pageInfo = new PageInfo<> ( infoClassList );
        return pageInfo;
    }
}
