package com.info.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.info.manage.dao.*;
import com.info.manage.entity.*;
import com.info.manage.service.IScoreService;
import com.info.manage.util.Dates;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author xxy
 * @Date 2019/7/17 17:25
 * @Description TODO
 **/
@Service
public class ScoreServiceImpl implements IScoreService {
    @Autowired
    ScoreDao scoreDao;
    @Autowired
    UserDao userDao;
    @Autowired
    StuDao stuDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    ClassDao classDao;

    @Override
    public void saveAndUpdateScore(InfoScore infoScore) {
        if (infoScore.getId () != null) {
            infoScore.setUpdateTime ( new Date () );
            scoreDao.update ( infoScore );
        } else {
            String stuNo = infoScore.getStuNo ();
            if (StringUtils.isNotEmpty ( stuNo )) {//导入时候用
                InfoStu infoStuParam = new InfoStu ();
                infoStuParam.setStuNo ( stuNo );
                InfoStu infoStu = stuDao.findInfoStu ( infoStuParam );
                infoScore.setStuId ( infoStu == null ? 1L : infoStu.getId () );
            }
            String courseName = infoScore.getCourseName ();
            if (StringUtils.isNotEmpty ( courseName )) {//导入时候用
                InfoCourse infoCourseParam = new InfoCourse ();
                infoCourseParam.setCourseName ( courseName );
                InfoCourse infoCourse = courseDao.findInfoCourse ( infoCourseParam );
                infoScore.setCourseId ( infoCourse == null ? 1L : infoCourse.getId () );
            }
            infoScore.setCreateTime ( new Date () );
            scoreDao.insert ( infoScore );
        }
    }

    @Override
    public PageInfo<InfoScore> findScoreList(Integer page, Integer limit, InfoScore infoScore) {
        if (page != null && limit != null) {
            PageHelper.startPage ( page, limit, "id desc" );
        }
        List<InfoScore> infoScoreList = scoreDao.findScoreList ( infoScore );
        for (InfoScore score : infoScoreList) {
            User user = userDao.findById ( score.getCreaterId () );
            score.setCreater ( user.getUserName () );
            score.setCreateTimeStr ( Dates.getDateTime ( score.getCreateTime (), Dates.DEFAULT_DATETIME_FORMAT ) );

//            Long stuId = score.getStuId ();
//            InfoStu infoStu = stuDao.findById ( stuId );
//            score.setStuNo ( infoStu.getStuNo () );
//            score.setStuName ( infoStu.getStuName () );
//
//            Long classId = infoStu.getClassId ();
//            if (classId != null) {
//                InfoClass infoClass = classDao.findById ( classId );
//                score.setClassNo ( infoClass.getClassNo () );
//                score.setClassName ( infoClass.getClassName () );
//            }
//
//            Long courseId = score.getCourseId ();
//            InfoCourse infoCourse = courseDao.findById ( courseId );
//            score.setCourseName ( infoCourse.getCourseName () );

        }
        PageInfo<InfoScore> pageList = new PageInfo<> ( infoScoreList );
        return pageList;
    }

    @Override
    public InfoScore findById(Long id) {
        return scoreDao.findById ( id );
    }

    @Override
    public void deleteById(Long id) {
        scoreDao.deleteById ( id );
    }

    @Override
    public void deleteScoreBatch(Long[] ids) {
        scoreDao.deleteScoreBatch ( ids );
    }
}
