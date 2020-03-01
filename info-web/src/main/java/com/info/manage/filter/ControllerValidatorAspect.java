package com.info.manage.filter;

import com.info.manage.util.Const;
import com.info.manage.util.PageResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * @Author xxy
 * @Date 2019/7/10 18:00
 * @Description TODO  com.info.manage.controller;
 **/
@Aspect
@Component
public class ControllerValidatorAspect {
    @Around("execution(* com.info.manage.controller..*.*(..)) && args(..,bindingResult)")
    public Object doAround(ProceedingJoinPoint pjp, BindingResult bindingResult) throws Throwable {
        Object returnVal;
        PageResult pageResult = new PageResult ();
        if (bindingResult.hasErrors ()) {
            StringBuilder stringBuilder = new StringBuilder ();
            for (FieldError fieldError : bindingResult.getFieldErrors ()) {
                stringBuilder.append ( fieldError.getDefaultMessage () + "," );
            }
            String string = stringBuilder.toString ();
            string = string.substring ( 0, string.length () - 1 );
            pageResult.setCode ( Const.FAILCODE );
            pageResult.setMessage ( string );
            return pageResult;
        } else {
            returnVal = pjp.proceed ();
            return returnVal;
        }
    }
}

































