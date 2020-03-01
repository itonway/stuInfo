package com.info.manage.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.github.pagehelper.PageInfo;
import com.info.manage.entity.DictItem;
import com.info.manage.entity.Role;
import com.info.manage.entity.User;
import com.info.manage.entity.UserRole;
import com.info.manage.filter.ExcelListener;
import com.info.manage.form.UserForm;
import com.info.manage.form.UserImportForm;
import com.info.manage.service.IDictItemService;
import com.info.manage.service.IRoleService;
import com.info.manage.service.IUserRoleService;
import com.info.manage.service.IUserService;
import com.info.manage.util.*;
import com.info.manage.validator.ChangePwdValidated;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Author xxy
 * @Description //TODO 用户管理
 * @Date 2019/7/5 11:17
 **/
@RestController
@RequestMapping(value = "user")
public class UserController {
    //ctrl shift F9
    //https://blog.csdn.net/hzr0523/article/details/85053062  layui
    private static  final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    IUserService userService;
    @Autowired
    IRoleService roleService;
    @Autowired
    IUserRoleService userRoleService;
    @Autowired
    IDictItemService dictItemService;

    /**
     * @return org.springframework.web.servlet.ModelAndView
     * @Author xxy
     * @Description //TODO
     * @Date 2019/7/5 11:18
     * @Param [modelAndView]
     **/
    @RequestMapping(value = "/jumpUserList")
    public ModelAndView   jumpUserList(ModelAndView modelAndView ){
        modelAndView.setViewName("userManage/userList");
        return modelAndView;
    }

    /**
     * @Author xxy
     * @Description //TODO  分页查询所有的用户信息
     * @Date 2019/7/21 15:48
     * @Param [page, limit, userForm]
     * @return com.info.manage.util.PageResult<com.info.manage.entity.User>
     **/
    @RequestMapping(value = "findUserList", method = RequestMethod.POST)
    public PageResult<User> findUserList(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "limit",defaultValue = "10")Integer limit,
            UserForm userForm){
        logger.info ( "接收到的查询参数："+userForm.toString () );
        PageInfo<User> pageInfo = userService.findUserList ( page,limit,userForm );
        PageResult<User> result = new PageResult<> ();
        result.setData ( pageInfo.getList () );
        result.setCount ( pageInfo.getTotal ());
        return result;
    }

    /**
     * @Author xxy
     * @Description //TODO  保存更新数据
     * @Date 2019/7/21 15:48
     * @Param [user, roleIds]
     * @return com.info.manage.util.PageResult<java.lang.Object>
     **/
    @RequestMapping(value = "/saveAndUpdate",method = RequestMethod.POST)
    public PageResult<Object> saveAndUpdate(
            User user,
            HttpServletRequest request,
            @RequestParam(value = "roleIds[]", required = false) Long[] roleIds) {
        PageResult<Object> result = new PageResult<> ();
        User loginUser = (User) request.getSession ().getAttribute ( "loginUser" );
        try {
            String loginName = user.getLoginName ();
            User userByLoginName = userService.findByLoginName ( loginName );
            if(userByLoginName != null && user.getId () == null){
                result.setCode ( Const.FAILCODE );
                result.setMessage ( "保存失败，登录名已存在" );
                return result;
            }
            user.setSelectRoleIds ( roleIds );
            user.setCreaterId ( loginUser.getId () );
            user.setUpdaterId ( loginUser.getId () );
            userService.saveAndUpdateUser ( user );
            result.setCode ( Const.SUCCESSCODE );
            result.setMessage ( "保存成功" );
        }catch (Exception e){
            result.setCode ( Const.FAILCODE );
            result.setMessage ( "保存失败" );
        }
        return result;
    }


    /**
     * @Author xxy
     * @Description //TODO 根据id查询数据
     * @Date 2019/7/21 15:48
     * @Param [id]
     * @return com.info.manage.entity.User
     **/
    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public User findById(Long id){
        User user = userService.findById ( id );
        System.out.println ( user.getCreateTime () + "-----" + user.getUpdateTime () );
        return user;
    }


    /**
     * @Author xxy
     * @Description //TODO 单个删除用户
     * @Date 2019/7/21 15:47
     * @Param [id]
     * @return com.info.manage.util.PageResult<java.lang.Object>
     **/
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    public PageResult<Object> deleteById(Long id){
        PageResult<Object> result = new PageResult<> ();
       try {
           userService.deleteById ( id );
           result.setCode ( Const.SUCCESSCODE );
           result.setMessage ( "删除成功" );
       }catch (Exception e){
           result.setCode ( Const.FAILCODE );
           result.setMessage ( "删除失败" );
       }
       return result;
    }

    /**
     * @return com.info.manage.util.PageResult<java.lang.Object>
     * @Author xxy
     * @Description 批量删除用户
     * @Date 2019/7/5 11:23
     * @Param [ids]
     **/
    @PostMapping(value = "/deleteUserBatch")
    public PageResult<Object> deleteUserBatch(@RequestParam(value = "ids[]", required = true) Long[] ids) {
        PageResult<Object> result = new PageResult<> ();
        try {
            logger.info ( "接收到的参数：" + ids.toString () );
            userService.deleteUserBatch ( ids );
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
     * @Description //TODO  根据用户查询该用户拥有的角色
     * @Date 2019/7/21 15:37
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @RequestMapping(value = "findUserRoleList", method = {RequestMethod.POST, RequestMethod.POST})
    public Map<String, Object> findRoleList(@RequestParam(value = "userId", required = false) Long userId){
        List<Role> roleListAll = roleService.findRoleAllList ( null );//所有角色
        List<Role> roleListChecked = new ArrayList<> (  );
        List<UserRole> userRoleList = userRoleService.findUserRoleListByUserId ( userId );//用户所拥有的角色
        for(UserRole userRole : userRoleList){
            Role userRole1 = roleService.selectById ( userRole.getRoleId () );
            roleListChecked.add ( userRole1 );
        }
        if (CollectionUtils.isNotEmpty ( roleListChecked )) {
            Iterator<Role> it = roleListAll.iterator ();
            while (it.hasNext ()) {
                Role role = it.next ();
                for (Role role1 : roleListChecked) {
                    if (role.getId ().compareTo ( role1.getId () ) == 0) {
                        it.remove ();
                    }
                }
            }
        }
        List<Role> roleListAllNoChecked = roleListAll;
        Map<String,Object> result = new HashMap<> (  );
        result.put ( "roleListAll",roleListAllNoChecked );
        result.put ( "userRoleChecked", roleListChecked);
        return result;
    }

    
    /**
     * @Author xxy
     * @Description //TODO  批量导入用户
     * @Date 2019/7/21 15:36
     * @Param [file]
     * @return BussinessMsg
     **/
    @PostMapping(value = "importUserBatch")
    public BussinessMsg importUserBatch(@RequestParam MultipartFile file, HttpServletRequest request) {
        User loginUser = (User) request.getSession ().getAttribute ( "loginUser" );
        InputStream inputStream = null;
        BussinessMsg msg = null;
        try {
            inputStream = new BufferedInputStream ( file.getInputStream () );
            List<Object> data = new ArrayList<> ();
            ExcelListener excelListener = new ExcelListener ( data );
            Sheet sheet = new Sheet ( 1, 1, UserImportForm.class );
            EasyExcelFactory.readBySax ( inputStream, sheet, excelListener );
            if (CollectionUtils.isEmpty ( data )) {
                msg = BussinessMsgUtil.returnCodeMessage ( BussinessCode.UPLOAD_ERROR_EMPTY );
                return msg;
            }
            for (Object object : data) {
                UserImportForm userImportForm = (UserImportForm) object;
                User user = new User ();
                BeanUtils.copyProperties ( userImportForm, user );
                DictItem dictItem = dictItemService.getDictItemCodeByItemName ( DictEnum.SEX.getValue (), user.getSex () );
                user.setSex ( dictItem == null ? "" : dictItem.getDictItemCode () );
                user.setCreaterId ( loginUser.getId () );
                userService.saveAndUpdateUser ( user );
            }
            msg = BussinessMsgUtil.returnCodeMessage ( BussinessCode.GLOBAL_SUCCESS );
        } catch (Exception e) {
            msg = BussinessMsgUtil.returnCodeMessage ( BussinessCode.UPLOAD_ERROR );
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
        }
        return msg;
    }


    /**
     * @Author xxy
     * @Description //TODO 下载用户导入模板
     * @Date 2019/7/21 15:36
     * @Param [response]
     * @return void
     **/
    @RequestMapping(value = "downUserTemplet", method = RequestMethod.POST)
    public void downUserTemplet(HttpServletResponse response) {
        Workbook workbook = null;
        ServletOutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread ().getContextClassLoader ().getResourceAsStream ( "exceltemplet/user.xlsx" );
            if (inputStream == null) {
                logger.error ( "没找到对应的模板文件。。。。。。。。。。。" );
            }
            if (inputStream != null && inputStream.available () == 0) {
                logger.error ( "模板为空。。。。。。。。。" );
            }
            //workbook = new XSSFWorkbook ( inputStream );
            workbook = new HSSFWorkbook ( inputStream );
            response.setContentType ( "application/binary;charset=ISO8859-1" );
            String fileName = java.net.URLEncoder.encode ( "批量导入用户模板", "UTF-8" );
            response.setHeader ( "Content-disposition", "attachment; filename=" + fileName + ".xlsx" );
            outputStream = response.getOutputStream ();
            workbook.write ( outputStream );
            outputStream.flush ();
        } catch (Exception e) {
            logger.error ( "下载失败。。。。。。。。" );
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
            if (workbook != null) {
                try {
                    workbook.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
        }
    }


    /**
     * @return com.info.manage.util.PageResult<java.lang.Object>
     * @Author xxy
     * @Description //TODO   修改密码
     * @Date 2019/7/19 17:51
     * @Param []
     **/
    @RequestMapping(value = "changeUserPwd", method = RequestMethod.POST)
    public PageResult<Object> changeUserPwd(@Validated({ChangePwdValidated.class}) User user, BindingResult bindingResult) {
        PageResult<Object> result = new PageResult<> ();
        String newPwd = user.getNewPwd ();
        String confirmNewPwd = user.getConfirmNewPwd ();
        if (!confirmNewPwd.equals ( newPwd )) {
            result.setCode ( Const.FAILCODE );
            result.setMessage ( "修改失败，确认密码与新密码不一致" );
            return result;
        }
        Long userId = user.getId ();
        User userSearch = userService.findById ( userId );
        String loginName = userSearch.getLoginName ();
        String password = userSearch.getPassword ();
        String md5Pwd = MD5Utils.getMD5 ( user.getOldPwd (), loginName );
        if (!password.equals ( md5Pwd )) {
            result.setCode ( Const.FAILCODE );
            result.setMessage ( "修改失败，旧密码错误" );
            return result;
        }
        try {
            userService.changeUserPwd ( user );
            result.setCode ( Const.SUCCESSCODE );
            result.setMessage ( "修改成功" );
        } catch (Exception e) {
            result.setCode ( Const.FAILCODE );
            result.setMessage ( "修改失败" );
        }
        return result;
    };

}
