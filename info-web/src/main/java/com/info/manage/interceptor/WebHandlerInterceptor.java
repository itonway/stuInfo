package com.info.manage.interceptor;

import com.info.manage.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xxy
 * @ClassName WebHandlerInterceptor
 * @Description 访问后台方法时，加载此类
 * @Date 2019/1/30 15:41
 **/
@Component
public class WebHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String path = httpServletRequest.getContextPath();
        String scheme = httpServletRequest.getScheme();
        String serverName = httpServletRequest.getServerName();
        int port = httpServletRequest.getServerPort();
        String basePath = scheme + "://" + serverName + ":" + port + path;
        //System.out.println ("项目的根路径："+basePath);
        httpServletRequest.setAttribute("basePath", basePath);
        return true;
    }
}
