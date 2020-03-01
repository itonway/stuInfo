package com.info.manage.dao;

import com.info.manage.entity.Dict;
import com.info.manage.form.DictForm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictDao extends BaseDao<Dict> {
    List<Dict> findDictList(DictForm dictForm);

    Dict findDictByDictCode(String dictCode);

}
