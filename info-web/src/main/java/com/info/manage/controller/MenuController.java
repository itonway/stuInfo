package com.info.manage.controller;

import com.alibaba.fastjson.JSON;
import com.info.manage.entity.Menu;
import com.info.manage.entity.User;
import com.info.manage.form.MenuCardForm;
import com.info.manage.service.IMenuService;
import com.info.manage.service.IRoleMenuService;
import com.info.manage.util.TreeResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xxy
 * @Description //TODO 菜单管理
 * @Date 2019/7/21 15:52
 **/
@RestController
@RequestMapping(value = "/menu")
public class MenuController {
    private Logger logger = Logger.getLogger ( MenuController.class );

    @Autowired
    IMenuService menuService;
    @Autowired
    IRoleMenuService roleMenuService;

    @RequestMapping(value = "jumpMenuList",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView jumpUserList(ModelAndView modelAndView ){
        modelAndView.setViewName("menuManage/menuTree");
        return modelAndView;
    }

    /**
     * @return java.lang.String
     * @Author xxy
     * @Description //TODO   暂时无用
     * @Date 2019/7/18 19:06
     * @Param [pId]
     **/
    @RequestMapping(value = "findMenuTop",method = {RequestMethod.GET,RequestMethod.POST})
    public String findMenuTop(Long pId){
        List<Map<String,Object>> mapList = new ArrayList<> (  );
        Map<String,Object> map = new HashMap<> (  );
        Menu menuParam = new Menu ();
        menuParam.setPId ( pId );
        List<Menu> menuList = menuService.findMenuList ( menuParam );
        for(Menu menu : menuList){
            map.put ( "id",menu.getId () );
            map.put ( "name",menu.getName () );
            map.put ( "pId", menu.getPId () );
            map.put ( "url",menu.getUrl () );
            map.put ( "permission",menu.getPermission () );
            map.put ( "icon",menu.getIcon () );
            mapList.add ( map );
        }
        String json = JSON.toJSONString(menuList);
        logger.info ( "头部树的数据：" +  json );
        return  json;
    }


    /**
     * @return java.lang.String
     * @Author xxy
     * @Description //TODO  查询左侧菜单
     * @Date 2019/7/18 19:06
     * @Param [pId]
     **/
    @RequestMapping(value = "findMenuLeft",method = {RequestMethod.GET,RequestMethod.POST})
    public String findMenuLeft(Long pId){
        List<MenuCardForm> menuList = menuService.findMenuTreeCard ( pId);
        String json = JSON.toJSONString(menuList);
        logger.info ( "左侧树的数据2：" + json );
        return  json;
    }

    /**
     * @return java.lang.String
     * @Author xxy
     * @Description //TODO  查询左侧菜单
     * @Date 2019/7/18 19:06
     * @Param [pId]
     **/
    @RequestMapping(value = "findMenuLeftByLoginUser",method = {RequestMethod.GET,RequestMethod.POST})
    public String findMenuLeftByLoginUser(HttpServletRequest request){
        User loginUser = (User) request.getSession ().getAttribute ( "loginUser" );
        Long userId = loginUser.getId ();
        List<Menu> menuList = menuService.findMenuLeftByLoginUser(userId);
        String json = JSON.toJSONString(menuList);
        logger.info ( "左侧树的数据2：" + json );
        return  json;
    }


    /**
     * @Author xxy
     * @Description //TODO  查询菜单树
     * @Date 2019/7/18 18:40
     * @Param [pId]
     * @return java.util.List<com.info.manage.util.TreeResult>
     **/
    @RequestMapping(value = "findMenuTree",method = {RequestMethod.GET,RequestMethod.POST})
    public List<TreeResult> findMenuTree(Long pId){
        List<TreeResult> resultList = new ArrayList<> (  );
        List<Menu> menuList = menuService.findMenuListByPid ( pId ,null);
        for(Menu menu : menuList){
            TreeResult treeResult = new TreeResult();
            treeResult.setId ( menu.getId () );
            treeResult.setName ( menu.getName () );
            treeResult.setpId ( menu.getPId ().toString () );
            treeResult.setOpen ( menu.isOpen () );
            treeResult.setIsParent ( menu.isParent () );
            treeResult.setChildren ( menu.getChildren () );
            resultList.add ( treeResult );
        }
        return  resultList;
    }

    /**
     * @Author xxy
     * @Description //TODO  根据角色id查询对应的菜单权限
     * @Date 2019/7/18 18:40
     * @Param [roleId]
     * @return java.util.List<com.info.manage.util.TreeResult>
     **/
    @RequestMapping(value = "findMenuTreeByRoleId",method = {RequestMethod.GET,RequestMethod.POST})
    public List<TreeResult> findMenuTreeByRoleId(Long roleId){
        Long pId = Long.valueOf ( "0" );
        List<TreeResult> resultList = new ArrayList<> (  );
        List<Menu> menuList = menuService.findMenuListByPid ( pId,roleId);
        for(Menu menu : menuList){
            TreeResult treeResult = new TreeResult();
            treeResult.setId ( menu.getId () );
            treeResult.setName ( menu.getName () );
            treeResult.setpId ( menu.getPId ().toString () );
            treeResult.setOpen ( menu.isOpen () );
            treeResult.setIsParent ( menu.isParent () );
            treeResult.setChildren ( menu.getChildren () );
            treeResult.setChecked ( menu.isChecked () );
            resultList.add ( treeResult );
        }
        return  resultList;
    }


}