package com.info.manage.dao;

import com.info.manage.entity.BaseEntity;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author xxy
 * @ClassName BaseDaoNew
 * @Description todo
 * @Date 2019/3/18 14:44
 **/
public interface BaseDaoNew<T extends BaseEntity> {

    Long add(T t);

    Long update(T t);

    void deleteById(Long id);

    T findById(Long id);
}
