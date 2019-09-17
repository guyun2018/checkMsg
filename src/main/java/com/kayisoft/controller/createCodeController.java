package com.kayisoft.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kayisoft.model.QueueUserInfo;
import com.kayisoft.service.AccessTokenService;
import com.kayisoft.service.CreateCodeService;
import com.kayisoft.util.HttpUtils;
import com.kayisoft.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class CreateCodeController {

    @Autowired
    AccessTokenService accessTokenService;

    @Autowired
    CreateCodeService createCodeService;

    /**
     * 生成官方二维码
     * @param queueUserInfo queueUserInfo
     * @return 二维码地址
     */
    @RequestMapping(value = "/createCode",method = RequestMethod.POST)
    @ResponseBody
    public Result getWXPublicQRCode(@RequestBody QueueUserInfo queueUserInfo) {
        try {
            String code = createCodeService.createCode(queueUserInfo);
            return new Result(true,"二维码生成成功",code);
        } catch (Exception e) {
            return new Result(false,"二维码生成失败");
        }
    }

}
