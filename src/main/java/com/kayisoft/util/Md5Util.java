package com.kayisoft.util;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * @Author: ruopeng.cheng
 * @Date: 2018/7/11 15:10
 */
public class Md5Util {



    public static String passwordMd5(String pwd){
        try {
            // 加一位空数组
            byte[] btKey = Arrays.copyOf(pwd.getBytes(), pwd.getBytes().length + 1);
            // MD5加密
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(btKey);
            byte[] btDigest = md.digest();
            BASE64Encoder base64en = new BASE64Encoder();
            return base64en.encode(btDigest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
    public static void main(String[] args){
//        System.out.println(CommonUtil.getGUID());
//        System.out.println(passwordMd5("AR82RC2R6Y3KI6SD2652A6QWmaster"));
//        System.out.println(passwordMd5("100000master"));
//        System.out.println(md5("appid=wx4d35262e53ce463a&body=测试商品&mch_id=1233083102&nonce_str=565459773065757146&notify_url=https://&openid=EFD6F14AF7FD4CEEAAC796B0B809F628&out_trade_no=1A89D73C6418495484FEDCB66F542591&spbill_create_ip=122.228.180.34&total_fee=10.0&trade_type=JSAPI&key=6B77B7970941EA5D593D5AE4CEEB9B5A"));
//        System.out.println(md5("100000"+"&key=MASTER"));
//        System.out.println(PayUtil.sign("100000","MASTER","utf-8"));
        System.out.println(getMD5("00010101","13968823892"));
    }

    public static String md5(String msg){
        return DigestUtils.md5Hex(msg.getBytes());
//        return null;
    }

    public static String getMD5(String msg,String salt){
        return new Md5Hash(msg,salt,4).toString();
    }
}
