package com.info.manage.dao;

import com.info.manage.entity.InfoStu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StuDao extends BaseDao<InfoStu> {

    void deleteStuBatch(@Param("ids") Long[] ids);

    List<InfoStu> findStuList(InfoStu infoStu);

    void deleteByClassIds(@Param("classIds") Long[] classIds);

    InfoStu findInfoStu(InfoStu infoStu);

}