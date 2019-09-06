package com.kayisoft.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kayisoft.model.QueueBean;
import com.kayisoft.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/4
 */
@Service
public class QueueServiceImpl implements QueueService {

    @Autowired
    CreateCodeService createCodeService;
    @Autowired
    RestTemplate restTemplate;
    /**
     * 获取检查相关信息
     *
     * @param accessNo 检查号
     * @return result
     */
    @Override
    public Result getQueueInfo(String accessNo) {
        //获取检查排队信息List(包括姓名、检查号、检查时间、前面几人、排队名次？)
//        String forObject = restTemplate.getForObject("XXXX?accessNo=abcde", String.class);
//        JSONObject ojb = JSONObject.parseObject(forObject);
//        QueueBean queueBean = JSON.toJavaObject(ojb, QueueBean.class);
        List<QueueBean> list  = new ArrayList<>();
        list.add(new QueueBean("201807015487","47000593033030211A1001","温州医科大学附属第二医院","周宇婷","A101",10,"CT"));
        list.add(new QueueBean("3383075","47068179533038211A1001","乐清市第二人民医院","邹乙丑","A55",5,"USMF"));
        Result result = new Result();
        result.setSuccess(true);
        result.setResults(list);
        return result;
    }

    /**
     * 去关注
     *
     * @param accessNo acc
     * @return result
     */
    @Override
    public Result toFollow(String accessNo) {
        //获取二维码地址
        String qrPath = createCodeService.createCode(accessNo);
        return new Result(true,"二维码生成成功",qrPath);
    }

    /**
     * @param accessNo
     * @return
     */
    @Override
    public Result getOpenId(String accessNo) {
        //从表中查询openId
        Result result = new Result();
        result.setSuccess(true);
        result.setObj("");
        return result;
    }
}
