package com.kayisoft.service;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/4
 */
public interface AccessTokenService {
    /**
     * 获取token
     * @param key
     * @return
     */
    String getAccToken(String key);
}
