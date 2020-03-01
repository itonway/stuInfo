package com.info.manage.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.info.manage.dao.*;
import com.info.manage.entity.*;
import com.info.manage.form.UserForm;
import com.info.manage.service.IUserService;
import com.info.manage.util.Dates;
import com.info.manage.util.DictEnum;
import com.info.manage.util.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.info.manage.shiroUtil.ShiroUtil;

@Service
public class UserServiceImpl implements IUserService {
    private static  final Logger logger = LoggerFactory.getLogger( UserServiceImpl.class);

    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    MenuDao menuDao;
    @Autowired
    RoleMenuDao roleMenuDao;
    @Autowired
    UserRoleDao userRoleDao;
    @Autowired
    DictItemDao dictItemDao;

    @Override
    public void saveAndUpdateUser(User user) {
        Long[] selectRoleIds = user.getSelectRoleIds ();
        Long userId = user.getId ();
        if (userId != null) {
            user.setUpdateTime ( new Date () );
            userDao.update ( user );
            if (selectRoleIds != null) {
                Long[] userIds = new Long[1];
                userIds[0] = userId;
                userRoleDao.deleteUserRoleByUserIds ( userIds );
                userRoleDao.saveUserRole ( selectRoleIds, userId );//保存用户角色
            }
        } else {
            String password = user.getPassword ();
            user.setPassword ( MD5Utils.getMD5 ( StringUtils.isEmpty ( password ) ? "123456" : password, user.getLoginName () ) );
            user.setCreateTime ( new Date () );
            userDao.insert ( user );
            if (selectRoleIds != null) {
                userRoleDao.saveUserRole ( selectRoleIds, user.getId () );//保存用户角色
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById ( id );
        Long[] userIds = new Long[1];
        userIds[0] = id;
        userRoleDao.deleteUserRoleByUserIds ( userIds );
    }

    @Override
    public void deleteUserBatch(Long[] ids) {
        userDao.deleteUserBatch ( ids );
        userRoleDao.deleteUserRoleByUserIds ( ids );
    }

    @Override
    public void changeUserPwd(User user) {
        User userSearch = userDao.findById ( user.getId () );
        String loginName = userSearch.getLoginName ();
        user.setNewPwd ( MD5Utils.getMD5 ( user.getNewPwd (), loginName ) );
        userDao.update ( user );
    }

    @Override
    public User findById(Long id) {
        return userDao.findById ( id );
    }

    @Override
    public PageInfo<User> findUserList(int page, int limit, UserForm userForm) {
        PageHelper.startPage ( page, limit, "id desc" );
        List<User> listUser = userDao.findUserList(userForm);
        for (User user : listUser){
            Long createrId = user.getCreaterId ();
            User userSearch = userDao.findById ( createrId );
            user.setCreater ( userSearch == null ? "" : userSearch.getUserName () );
            user.setCreateTimeStr ( Dates.getDateTime ( user.getCreateTime (), Dates.DEFAULT_DATETIME_FORMAT ) );
            DictItem dictitem = dictItemDao.findDictItemByDictCodeAndItemCode ( DictEnum.SEX.getValue (), user.getSex () );
            user.setSexStr ( dictitem == null ? "" : dictitem.getDictItemName () );
        }
        PageInfo<User> pageInfo = new PageInfo<> ( listUser );
        return pageInfo;
    }

    @Override
    public User findByLoginName(String loginName) {
        return userDao.findByLoginName ( loginName );
    }

    @Override
    public void setMenuAndRole(String loginName) {

        User user = findByLoginName ( loginName );
        if(user == null){
            logger.error ( "获取用回信息失败,登陆名为>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + loginName );
            return ;
        }

        //Subject subject = ShiroUtil.getSubject();
        //Session session = subject.getSession();


        //JSONArray json = menuService.getMenuJsonByUser(menuList);
        //session.setAttribute("menu", json);

        List<Menu> currentMenuList = new ArrayList<> ();
        List<Role> currentRoleList = new ArrayList<>();

        List<UserRole> userRoleList = userRoleDao.findUserRoleListByUserId ( user.getId () );
        for(UserRole userRole : userRoleList){
            Long roleId = userRole.getRoleId ();
            Role role = roleDao.findById ( roleId );
            currentRoleList.add ( role );

            List<RoleMenu> roleMenuList = roleMenuDao.selectRoleMenuByRoleId ( roleId );
            for(RoleMenu roleMenu : roleMenuList){
                Long menuId = roleMenu.getMenuId ();
                Menu menu = menuDao.findById ( menuId );
                currentMenuList.add ( menu );
            }
        }

        user.setRoleLsit ( currentRoleList );
        user.setMenuList ( currentMenuList );
        //session.setAttribute("curentUser", user);
    }

    @Override
    public List<User> findUserAll() {
        UserForm userForm = new UserForm ();
        return userDao.findUserList ( userForm );
    }


}
