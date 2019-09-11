package com.kayisoft.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kayisoft.model.account.JwtTokenBean;
import com.kayisoft.model.account.ManageUserBean;
import com.kayisoft.model.account.UserManageBean;
import com.kayisoft.util.EhCacheUtil;
import com.kayisoft.util.Md5Util;
import com.kayisoft.util.UserManageUrl;
import com.kayisoft.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * 用户管理接口
 *
 * @author: ruopeng.cheng
 * @date: 2019/4/12 10:15
 */
@Service
public class UserManageInterFaceServiceImpl implements UserManageInterfaceService {

    private static final String SYSTEM_ID = "3eebd86383c7402592803bb468d719c4";

    @Autowired
    RestTemplate restTemplate;






    @Override
    public Result getToken(ManageUserBean userBean) {

        Result result = new Result();
        HttpHeaders header = new HttpHeaders();
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("username", userBean.getUsername());
        String password = Md5Util.md5(Md5Util.md5(userBean.getPassword()).toUpperCase()).toUpperCase();
        postParameters.add("password",password );
        postParameters.add("grant_type", "password");
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(postParameters, header);
        //请求体

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(UserManageUrl.getToken.getUrl(), requestEntity, String.class);
        String body = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);

        JwtTokenBean jwtTokenBean = new JwtTokenBean();
        jwtTokenBean.setAccessToken(jsonObject.getString("access_token"));
        jwtTokenBean.setExpiresIn(jsonObject.getInteger("expires_in"));
        jwtTokenBean.setRefreshToken(jsonObject.getString("refresh_token"));
        jwtTokenBean.setTokenType(jsonObject.getString("token_type"));
        jwtTokenBean.setDate(new Date());

        HttpHeaders header1 = new HttpHeaders();
        // 需求需要传参为form-data格式
        header1.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        header.set("Authorization", jwtTokenBean.getTokenType() + " " + jwtTokenBean.getAccessToken());
        HttpEntity<MultiValueMap<String, Object>> requestEntity1 = new HttpEntity<>(null, header);

        ResponseEntity<String> responseEntity1 = restTemplate.postForEntity(UserManageUrl.checkLogin.getUrl(), requestEntity1, String.class);
        String body1 = responseEntity1.getBody();
        JSONObject jsonObject1 = JSONObject.parseObject(body1);
        userBean.setUserId(jsonObject1.getString("userid"));
        userBean.setUsername(jsonObject1.getString("username"));
        EhCacheUtil.getInstance().put("TokenCache",jsonObject1.getString("userid"),jwtTokenBean);
        result.setSuccess(true);
        result.setObj(userBean);
        return result;
    }

    /**
     * 获取用户信息
     *
     * @param userManageBean model
     * @return result
     */
    @Override
    public Result getUserInfo(UserManageBean userManageBean) {
        HttpHeaders header = new HttpHeaders();
        JwtTokenBean jwtTokenBean = (JwtTokenBean) EhCacheUtil.getInstance().get("TokenCache",userManageBean.getCurrentUser());
//        JwtTokenBean jwtTokenBean1 = checkToken(jwtTokenBean);
//        if(jwtTokenBean1.getOverdue()){
//            jwtTokenBean = jwtTokenBean1;
//            EhCacheUtil.getInstance().put("TokenCache",userManageBean.getCurrentUser(),jwtTokenBean);
//        }
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        header.set("Authorization", jwtTokenBean.getTokenType() + " " + jwtTokenBean.getAccessToken());
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("UserId", userManageBean.getUserId());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(postParameters, header);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(UserManageUrl.getUserInfo.getUrl(), requestEntity, String.class);
        String body = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);

        Integer success = 1;
        Integer code = jsonObject.getInteger("Code");
        Result result = new Result();
        if (code.equals(success)) {
            JSONObject content = JSONObject.parseObject(jsonObject.getString("Content"));
            UserManageBean manageBean = JSON.toJavaObject(content, UserManageBean.class);
            result.setObj(manageBean);
            result.setSuccess(true);
        }
        return result;
    }
}
