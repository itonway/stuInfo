package com.info.manage.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * @author xxy
 * @ClassName DES3Utils
 * @Description todo  对称加密方式 3DES
 * @Date 2019/5/14 10:26
 **/
public class DES3Utils {

    // 加解密统一使用的编码方式
    private final static String ENCODING = "utf-8";
    //定义算法
    private static final String Algorithm = "DESede";
    //加密方式
    private static final String CipherInstance = "DESede/ECB/PKCS5Padding";

    public static void main(String[] args) throws Exception {
        String data = "xiegl@123";
        System.out.println ( "加密前的数据：" + data );
        Key keyGenerator = initKeyGenerator ();
        System.out.println ( keyGenerator.toString () );
        String encrypt3desstr = encrypt3des ( keyGenerator, data );
        System.out.println ( "加密后的数据：" + encrypt3desstr );
        String decrypt3des = decrypt3des ( keyGenerator, encrypt3desstr );
        System.out.println ( "解密后的数据：" + decrypt3des );
    }

    /**
     * @return void
     * @Author xxy
     * @Description 生成key
     * @Date 14:00 2019/3/14
     * @Param []
     **/
    public static Key initKeyGenerator() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance ( Algorithm );
        keyGenerator.init ( new SecureRandom () );//默认长度
        SecretKey secretKey = keyGenerator.generateKey ();
        byte[] bytesKey = secretKey.getEncoded ();
        //KEY转换
        DESedeKeySpec desedeKeySpec = new DESedeKeySpec ( bytesKey );
        SecretKeyFactory factory = SecretKeyFactory.getInstance ( Algorithm );
        Key convertSecretKey = factory.generateSecret ( desedeKeySpec );
        return convertSecretKey;

    }

    /**
     * @return void
     * @Author xxy
     * @Description 加密
     * @Date 11:41 2019/3/14
     * @Param [convertSecretKey]
     **/
    public static String encrypt3des(Key convertSecretKey, String data) throws Exception {
        Cipher cipher = Cipher.getInstance ( CipherInstance );
        cipher.init ( Cipher.ENCRYPT_MODE, convertSecretKey );
        byte[] encrptBytes = cipher.doFinal ( data.getBytes ( ENCODING ) );
        String result = Base64.encodeBase64String ( encrptBytes );
        return result;
    }


    /**
     * @return void
     * @Author xxy
     * @Description 解密
     * @Date 11:37 2019/3/14
     * @Param [convertSecretKey, result]
     **/
    public static String decrypt3des(Key convertSecretKey, String data) throws Exception {
        Cipher cipher = Cipher.getInstance ( CipherInstance );
        cipher.init ( Cipher.DECRYPT_MODE, convertSecretKey );
        byte[] base64Bytes = Base64.decodeBase64 ( data.getBytes ( ENCODING ) );
        byte[] resultByte = cipher.doFinal ( base64Bytes );
        String result = new String ( resultByte, ENCODING );
        return result;
    }


}
