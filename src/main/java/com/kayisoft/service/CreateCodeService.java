package com.kayisoft.service;

import com.kayisoft.vo.Result;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/4
 */
public interface CreateCodeService {
    /**
     * 生成二维码
     * @param accessNo 检查号
     * @return result
     */
    String createCode(String accessNo);

    /**
     * 获取二维码过期时间设置
     * @return Integer
     */
    Integer getQrCodeExpire();
}
