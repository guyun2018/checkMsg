package com.kayisoft.service;

import com.alibaba.fastjson.JSONObject;
import com.kayisoft.mapper.WeixinAccesstokenMapper;
import com.kayisoft.model.WeixinAccesstoken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/4
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    WeixinAccesstokenMapper weixinAccesstokenMapper;

    @Autowired
    RestTemplate restTemplate;

    @Value("${app_id}")
    private String appId;

    @Value("${secret}")
    private String secret;

    /**
     * 获取token
     *
     * @param key
     * @return
     */
    @Override
    public String getAccToken(String key) {
        String validAccessToken = getValidAccessToken(key);
        boolean outTime = tokenExpiredGZ(validAccessToken);
        if (outTime) {
            System.out.println("accessToken有效");
            return validAccessToken;
        } else {
            System.out.println("accessToken无效");
            String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?" +
                    "grant_type=client_credential" +
                    "&appid=" + appId +
                    "&secret=" + secret;
            String res = restTemplate.getForObject(accessTokenUrl, String.class);
            JSONObject jsonObject = JSONObject.parseObject(res);
            if (null != jsonObject) {
                String accessToken = jsonObject.getString("access_token");
                System.out.println("accessToken重新生成:" + jsonObject.getString("access_token"));

                insertAccessToken(accessToken);
                return accessToken;
            }
        }
        return null;
    }

    /**
     * 缓存有数据则返回，无数据则查询数据库
     * @param key
     * @return
     */
    @Cacheable(value = "AccessTokenCache", key = "#key")
    public String getValidAccessToken(String key) {
        WeixinAccesstoken weixinAccesstoken = weixinAccesstokenMapper.selectByPrimaryKey((long) 1);
        return weixinAccesstoken.getAccessToken();
    }

    /**数据库执行更新操作
     *
     * @param accessToken accessToken
     * @return
     */
    @CachePut(value = "AccessTokenCache", key = "#p0")
    public String insertAccessToken(String accessToken) {
        WeixinAccesstoken weixinAccesstoken = new WeixinAccesstoken();
        weixinAccesstoken.setAccessToken(accessToken);
        weixinAccesstoken.setId((long) 1);
        weixinAccesstokenMapper.updateAccessToken(weixinAccesstoken);
        return accessToken;
    }

    /**
     * 公众号号验证accessToken
     *
     * @param accessToken
     * @return
     */
    private boolean tokenExpiredGZ(String accessToken) {

        String str = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=" + accessToken;
        String res = restTemplate.getForObject(str, String.class);
        JSONObject jsonObject = JSONObject.parseObject(res);
        String errcode = jsonObject.getString("errcode");
        //为null代表验证通过
        if (errcode == null) {
            return true;
        }
        return false;
    }
}
