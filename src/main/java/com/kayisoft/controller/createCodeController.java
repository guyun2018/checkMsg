package com.kayisoft.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kayisoft.service.AccessTokenService;
import com.kayisoft.service.CreateCodeService;
import com.kayisoft.util.HttpUtils;
import com.kayisoft.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/2
 */
@Controller
@PropertySource(value = {"classpath:systemConfig.properties"})
public class createCodeController {

    @Autowired
    AccessTokenService accessTokenService;

    @Autowired
    CreateCodeService createCodeService;

    /**
     *
     * @param accessNo 检查号
     * @return str
     */
    @RequestMapping(value = "/createCode")
    @ResponseBody
    public String getWXPublicQRCode(String accessNo) {
        String code = createCodeService.createCode(accessNo);
        return code;
    }

}
