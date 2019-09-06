package com.kayisoft.mapper;

import com.kayisoft.model.WeixinAccesstoken;

/**
 * @author Administrator
 */
public interface WeixinAccesstokenMapper extends  BaseMapper<WeixinAccesstoken,Long> {

    /**
     * 修改时间
     * @param weixinAccesstoken model
     */
    void updateAccessToken(WeixinAccesstoken weixinAccesstoken);
}