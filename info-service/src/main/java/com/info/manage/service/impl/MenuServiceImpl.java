package com.info.manage.service.impl;

import com.info.manage.dao.MenuDao;
import com.info.manage.dao.RoleMenuDao;
import com.info.manage.dao.UserRoleDao;
import com.info.manage.entity.Menu;
import com.info.manage.entity.RoleMenu;
import com.info.manage.entity.UserRole;
import com.info.manage.form.MenuCardForm;
import com.info.manage.service.IMenuService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements IMenuService {
    @Autowired
    MenuDao menuDao;
    @Autowired
    RoleMenuDao roleMenuDao;
    @Autowired
    UserRoleDao userRoleDao;



    @Override
    public Menu findById(Long id) {
        return menuDao.findById ( id );
    }


    @Override
    public List<Menu> findMenuList(Menu menu) {
        List<Menu> menuList = menuDao.findMenuList ( menu );
        return menuList;
    }

    @Override
    public List<Menu> findMenuListByPid(Long pid,Long roleId) {
        List<Long> roleMenuId = null;
        if(roleId != null){
            List<RoleMenu> roleMenuList = roleMenuDao.selectRoleMenuByRoleId ( roleId );
            roleMenuId = new ArrayList<> (  );
            for(RoleMenu roleMenu : roleMenuList){
                roleMenuId.add ( roleMenu.getMenuId () );
            }
        }
        List<Menu> resultList = new ArrayList<> (  );
        List<Menu> menuList = menuDao.findMenuListByPid ( pid );
        for(Menu menu : menuList){
            Long id = menu.getId ();
            if(roleMenuId != null && roleMenuId.contains ( id )){
                menu.setChecked ( true );
            }
            Integer count = menuDao.findMenuCountByPid ( id );
            if(count == 0){
                menu.setOpen ( false );
                //menu.setIsParent ( false );
                menu.setParent ( false );
                resultList.add ( menu );
                continue;
            }else{
                menu.setOpen ( true );
                //menu.setIsParent ( true );
                menu.setParent ( true );
                menu.setChildren (findMenuListByPid ( id , roleId));
                resultList.add ( menu );
            }
        }
        return resultList;
    }

    @Override
    public List<MenuCardForm> findMenuTreeCard(Long pid) {
        List<MenuCardForm> resultList = new ArrayList<> (  );
        Menu menuCard = new Menu ();
        menuCard.setPId ( pid );
        menuCard.setType ( "1" );//只查询菜单
        List<Menu> menuList = menuDao.findMenuList ( menuCard );
        for(Menu menu : menuList){
            Long id = menu.getId ();
            MenuCardForm cardForm = new MenuCardForm ();
            cardForm.setId ( menu.getId ()  );
            cardForm.setName ( menu.getName ()  );
            cardForm.setpId ( menu.getPId () );
            cardForm.setUrl (menu.getUrl ()  );
            cardForm.setPermission ( menu.getPermission () );
            cardForm.setIcon ( menu.getIcon () );
            Integer count = menuDao.findMenuCountByPid ( id );
            if(count == 0){
                resultList.add ( cardForm );
                continue;
            }else{
                cardForm.setChildren ( findMenuTreeCard( id ) );
                resultList.add ( cardForm );
            }
        }
        return resultList;
    }

    @Override
    public List<Menu> findMenuLeftByLoginUser(Long userId) {
        //List<MenuCardForm> menuCardFormList = new ArrayList<> (  );
        List<Menu> menuList = new ArrayList<> (  );
        List<UserRole> userRoleList = userRoleDao.findUserRoleListByUserId ( userId );
        if(CollectionUtils.isEmpty ( userRoleList )){
            return menuList;
        }
        List<RoleMenu> roleMenuList = roleMenuDao.findRoleMenuListByRoleIds(userRoleList);
        if(CollectionUtils.isEmpty ( roleMenuList )){
            return menuList;
        }
        List<Long> menuIds = new ArrayList<> (  );
        for(RoleMenu roleMenu : roleMenuList ){
            Long menuId = roleMenu.getMenuId ();
            menuIds.add ( menuId );
        }
        Menu menuSearch = new Menu ();
        menuSearch.setMenuIds ( menuIds );
        List<Menu> menuListSearch = menuDao.findMenuList (menuSearch);
        // 暂时考虑实现两层菜单
        Map<Long, List<Menu>> menuMap = new HashMap<> ();
        for (Menu menu : menuListSearch) {
            Long pId = menu.getPId ();
            List<Menu> specList = menuMap.get(pId);
            if (specList == null) {
                specList = new ArrayList<>();
                specList.add(menu);
            } else {
                specList.add(menu);
            }
            menuMap.put(pId, specList);
        }
        List<Menu> rootMenu = menuMap.get(0L);
        if (rootMenu == null) {
            return new ArrayList<>();
        }
        for (Menu spec : rootMenu) {
            List<Menu> subMenuList = menuMap.get(spec.getId());
            spec.setChildren (subMenuList);
            menuList.add(spec);
        }
        return menuList;
    }
}
