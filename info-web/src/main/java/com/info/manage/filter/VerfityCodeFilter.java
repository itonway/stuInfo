package com.info.manage.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zhuxiaomeng
 * @date 2017/12/29.
 * @email 154040976@qq.com
 * <p>
 * 验证码拦截
 */
public class VerfityCodeFilter extends AccessControlFilter {
    /**
     * 是否开启验证码验证   默认true
     */
    private boolean verfitiCode = true;

    /**
     * 前台提交的验证码name
     */
    private String jcaptchaParam = "code";

    /**
     * 验证失败后setAttribute key
     */
    private String failureKeyAttribute = "shiroLoginFailure";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        //String sessionCode = (String) httpRequest.getSession().getAttribute("_code");
        Subject sub = SecurityUtils.getSubject();
        String sessionCode = sub.getSession ().getAttribute ( "_code" ).toString ();
        if(StringUtils.isEmpty ( sessionCode )){
            throw new Exception ( "获取session中保存的验证码失败" );
        }
        String currentCode = httpRequest.getParameter ( jcaptchaParam );
        boolean flag = StringUtils.equalsIgnoreCase ( sessionCode, currentCode );
        return flag;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        servletRequest.setAttribute ( failureKeyAttribute, "code.error" );
        return true;
    }

    public boolean isVerfitiCode() {
        return verfitiCode;
    }

    public void setVerfitiCode(boolean verfitiCode) {
        this.verfitiCode = verfitiCode;
    }

    public String getJcaptchaParam() {
        return jcaptchaParam;
    }

    public void setJcaptchaParam(String jcaptchaParam) {
        this.jcaptchaParam = jcaptchaParam;
    }

    public String getFailureKeyAttribute() {
        return failureKeyAttribute;
    }

    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }
}
