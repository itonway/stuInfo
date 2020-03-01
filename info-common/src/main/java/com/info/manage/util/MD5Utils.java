package com.info.manage.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @Author xxy
 * @Date 2019/7/19 13:46
 * @Description TODO
 **/
public class MD5Utils {

    public static String getMD5(String msg, String salt) {
        Md5Hash md5Hash = new Md5Hash ( msg, salt, 4 );
        return md5Hash.toString ();
    }

}
