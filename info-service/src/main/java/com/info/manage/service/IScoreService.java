package com.info.manage.service;

import com.github.pagehelper.PageInfo;
import com.info.manage.entity.InfoScore;

public interface IScoreService {
    void saveAndUpdateScore(InfoScore infoScore);

    PageInfo<InfoScore> findScoreList(Integer page, Integer limit, InfoScore infoScore);

    InfoScore findById(Long id);

    void deleteById(Long id);

    void deleteScoreBatch(Long[] ids);


}
