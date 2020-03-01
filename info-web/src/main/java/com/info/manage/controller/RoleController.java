package com.info.manage.controller;

import com.github.pagehelper.PageInfo;
import com.info.manage.entity.Role;
import com.info.manage.entity.User;
import com.info.manage.service.IRoleMenuService;
import com.info.manage.service.IRoleService;
import com.info.manage.util.Const;
import com.info.manage.util.PageResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author xxy
 * @Description //TODO  角色管理
 * @Date 2019/7/21 15:53
 **/
@RestController
@RequestMapping(value = "role")
public class RoleController {
    private Logger logger = Logger.getLogger ( RoleController.class );

    @Autowired
    IRoleService roleService;
    @Autowired
    IRoleMenuService roleMenuService;

    @RequestMapping(value = "jumpRoleList")
    public ModelAndView jumpUserList(ModelAndView modelAndView) {
        modelAndView.setViewName ( "roleManage/roleList" );
        return modelAndView;
    }

    /**
     * @return com.info.manage.util.PageResult<com.info.manage.entity.Role>
     * @Author xxy
     * @Description //TODO  查询角色分页
     * @Date 2019/7/18 17:27
     * @Param [page, limit, role]
     **/
    @RequestMapping(value = "findRoleListPage", method = {RequestMethod.GET, RequestMethod.POST})
    public PageResult<Role> findRoleListPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            Role role) {
        PageResult<Role> result = new PageResult<> ();
        PageInfo<Role> rolePageInfo = roleService.findRoleListPage ( page, limit, role );
        result.setData ( rolePageInfo.getList () );
        result.setCount ( rolePageInfo.getTotal () );
        return result;
    }


    /**
     * @Author xxy
     * @Description //TODO  保存更新角色信息
     * @Date 2019/7/18 17:27 
     * @Param [role, menuIds]
     * @return com.info.manage.util.PageResult<java.lang.Object>
     **/
    @RequestMapping(value = "saveAndUpdateRole", method = RequestMethod.POST)
    public PageResult<Object> saveAndUpdateRole(
            Role role,
            @RequestParam(value = "menuIds[]", required = false) Long[] menuIds,
            HttpServletRequest request) {
        PageResult<Object> result = new PageResult<> ();
        User loginUser = (User) request.getSession ().getAttribute ( "loginUser" );
        try {
            role.setCreaterId ( loginUser.getId () );
            role.setUpdaterId ( loginUser.getId () );
            role.setSelectMenuIds ( menuIds );
            roleService.saveAndUpdateRole ( role );
            result.setCode ( Const.SUCCESSCODE );
            result.setMessage ( "保存成功" );
        } catch (Exception e) {
            logger.error ( "保存失败：" + e );
            result.setCode ( Const.FAILCODE );
            result.setMessage ( "保存失败" );
        }
        return result;
    }


    /**
     * @Author xxy
     * @Description //TODO  删除角色
     * @Date 2019/7/18 17:28 
     * @Param [id]
     * @return com.info.manage.util.PageResult<java.lang.Object>
     **/
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    public PageResult<Object> deleteById(Long id) {
        PageResult<Object> result = new PageResult<> ();
        try {
            roleService.deleteById ( id );
            result.setCode ( Const.SUCCESSCODE );
            result.setMessage ( "删除成功" );
        } catch (Exception e) {
            result.setCode ( Const.FAILCODE );
            result.setMessage ( "删除失败" );
        }
        return result;
    }

    /**
     * @Author xxy
     * @Description //TODO   批量删除角色
     * @Date 2019/7/18 17:28 
     * @Param [ids]
     * @return com.info.manage.util.PageResult<java.lang.Object>
     **/
    @RequestMapping(value = "deleteRoleBatch", method = RequestMethod.POST)
    public PageResult<Object> deleteRoleBatch(@RequestParam(value = "ids[]", required = true) Long[] ids) {
        PageResult<Object> result = new PageResult<> ();
        try {
            roleService.deleteRoleBatch ( ids );
            result.setCode ( Const.SUCCESSCODE );
            result.setMessage ( "删除成功" );
        } catch (Exception e) {
            result.setCode ( Const.FAILCODE );
            result.setMessage ( "删除失败" );
        }
        return result;
    }
}