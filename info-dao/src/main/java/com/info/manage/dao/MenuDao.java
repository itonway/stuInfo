package com.info.manage.dao;

import com.info.manage.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao extends BaseDao<Menu> {

    List<Menu> findMenuList(Menu menu);

    List<Menu> findMenuListByPid(Long pId);

    Integer findMenuCountByPid(Long pId);


}