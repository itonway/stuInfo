package com.info.manage.dao;

import com.info.manage.entity.BaseEntity;

/**
 * @Author xxy
 * @Description
 * @Date 11:34 2019/3/18
 * @Param
 * @return
 **/
public interface BaseDao<T extends BaseEntity>{
    Long insert(T object);

    int update(T t);

    void deleteById(Long id);

    /**
     * @return T
     * @Author xxy
     * @Description
     * @Date 14:10 2019/3/18
     * @Param [id]
     **/
    T findById(Long id);

}
