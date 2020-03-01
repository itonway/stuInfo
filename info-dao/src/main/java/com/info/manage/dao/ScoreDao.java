package com.info.manage.dao;

import com.info.manage.entity.InfoScore;
import com.info.manage.entity.InfoStu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreDao extends BaseDao<InfoScore> {
    List<InfoScore> findScoreList(InfoScore infoScore);

    void deleteScoreBatch(@Param("ids") Long[] ids);

    void deleteScoreBatchByStuList(@Param("stuList") List<InfoStu> stuList);

}