package com.info.manage.dao;

import com.info.manage.entity.InfoClass;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassDao extends BaseDao<InfoClass> {

    void deleteClassBatch(@Param("ids") Long[] ids);

    List<InfoClass> findClassList(InfoClass infoClass);


}