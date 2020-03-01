package com.info.manage.dao;

import com.info.manage.entity.InfoTeacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherDao extends BaseDao<InfoTeacher> {
    List<InfoTeacher> findTeacherList(InfoTeacher teacher);

    void deleteTeacherBatch(@Param("ids") Long[] ids);
}