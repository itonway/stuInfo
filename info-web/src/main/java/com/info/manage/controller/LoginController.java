package com.info.manage.controller;

import com.info.manage.entity.User;
import com.info.manage.filter.VerifyCodeUtils;
import com.info.manage.service.IUserService;
import com.info.manage.util.BussinessCode;
import com.info.manage.util.BussinessMsg;
import com.info.manage.util.BussinessMsgUtil;
import com.info.manage.util.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @Author xxy
 * @Description //TODO 登录管理
 * @Date 2019/7/16 11:21
 **/
@RestController
@RequestMapping(value = "login")
public class LoginController {
    private static  final Logger logger = LoggerFactory.getLogger( LoginController.class);
    @Autowired
    IUserService userService;

    @RequestMapping(value = "jumpLogin")
    public ModelAndView jumpLogin(ModelAndView modelAndView,HttpServletRequest request) {
        request.getSession ().removeAttribute ( "loginUser" );
        modelAndView.setViewName("loginManage/login");
        return modelAndView;
    }

    @RequestMapping(value = "jumpMain")
    public ModelAndView jumpMain(ModelAndView modelAndView) {
        modelAndView.setViewName("loginManage/main");
        return modelAndView;
    }

    @RequestMapping(value = "jumpIndex")
    public ModelAndView jumpIndex(ModelAndView modelAndView) {
        modelAndView.setViewName ( "loginManage/index" );
        return modelAndView;
    }

    @RequestMapping(value = "getCode",method = {RequestMethod.POST,RequestMethod.GET})
    public void getCode(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpg");

            //生成随机字串
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
            logger.info("verifyCode:{}", verifyCode);
            //存入会话session
            HttpSession session = request.getSession(true);
            session.setAttribute("_code", verifyCode.toLowerCase());
            //生成图片
            int w = 146, h = 33;
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return com.info.manage.util.BussinessMsg
     * @Author xxy
     * @Description //TODO 登陆系统
     * @Date 2019/7/16 14:48
     * @Param [request, user]
     **/
    @PostMapping(value = "/loginSystem")
    public BussinessMsg loginSystem(HttpServletRequest request, User user) throws Exception {
        String code = user.getCode ();
        Object attribute = request.getSession ().getAttribute ( "_code" );
        if (!code.equals ( attribute.toString () )) {
            return BussinessMsgUtil.returnCodeMessage ( BussinessCode.GLOBAL_CAPTCHA_ERROR );
        }
        String loginName = user.getLoginName ();
        User loginUser = userService.findByLoginName ( loginName );
        if (loginUser == null) {
            return BussinessMsgUtil.returnCodeMessage ( BussinessCode.GLOBAL_LOGIN_NAME_NOT_EXIST );
        }
        String loginUserPassword = loginUser.getPassword ();//登录用户的密码 加密后的密码
        String password = user.getPassword ();//输入的秘密
        String md5Password = MD5Utils.getMD5 ( password, loginName ); //加密后的密码
        if (!loginUserPassword.equals ( md5Password )) {
            return BussinessMsgUtil.returnCodeMessage ( BussinessCode.GLOBAL_LOGIN_FAIL );
        }
        request.getSession ().setAttribute ( "loginUser", loginUser );
        return BussinessMsgUtil.returnCodeMessage ( BussinessCode.GLOBAL_SUCCESS );
    }

    
    /**
     * @Author xxy
     * @Description //TODO   退出
     * @Date 2019/7/21 17:34
     * @Param [request]
     * @return java.lang.String
     **/
    @PostMapping(value = "signOut")
    public String signOut(HttpServletRequest request) {
        request.getSession ().removeAttribute ( "loginUser");
        return "loginManage/login";
    }


    /**
     * @return com.info.manage.entity.User
     * @Author xxy
     * @Description //TODO   获取当前登录人
     * @Date 2019/7/19 15:19
     * @Param []
     **/
    @RequestMapping(value = "findCurrentLoginUser", method = {RequestMethod.POST, RequestMethod.GET})
    public User findCurrentLoginUser(HttpServletRequest request) {
        User loginUser = (User) request.getSession ().getAttribute ( "loginUser" );
        return loginUser;
    }
    




}
