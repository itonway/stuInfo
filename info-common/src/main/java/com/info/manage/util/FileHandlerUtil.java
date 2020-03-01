package com.info.manage.util;

import sun.misc.BASE64Encoder;

import java.net.URLEncoder;

/**
 * @Author xxy
 * @Date 2019/7/9 18:05
 * @Description TODO
 **/
public class FileHandlerUtil {
    private static final String FIREFOX = "Firefox";
    private static final String SAFARI = "Safari";
    private static final String EDGE = "Edge";

    private static final String UTF8 = "utf-8";


    public static String getFileName(String fileName, String agent) {
        try {
            if (agent != null && agent.contains ( FIREFOX )) {
                String realName = "" + new BASE64Encoder ().encode ( fileName.getBytes ( UTF8 ) ) + "?=";
                return realName.replaceAll ( "\r\n", "" );
            } else if (agent != null && agent.contains ( SAFARI ) && !agent.contains ( EDGE )) {
                return new String ( fileName.getBytes ( UTF8 ), "iso8859-1" );
            } else {
                String realName = URLEncoder.encode ( fileName, UTF8 );
                return realName.replace ( "+", " " );
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return fileName;

    }


}
