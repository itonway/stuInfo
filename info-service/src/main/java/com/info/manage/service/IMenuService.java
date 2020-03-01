package com.info.manage.service;

import com.info.manage.entity.Menu;
import com.info.manage.form.MenuCardForm;

import java.util.List;

public interface IMenuService {

    Menu findById(Long id);

    List<Menu> findMenuList(Menu menu);

    List<Menu> findMenuListByPid(Long pId,Long roleId);

    List<MenuCardForm> findMenuTreeCard(Long pId);

    List<Menu> findMenuLeftByLoginUser(Long userId);
}
