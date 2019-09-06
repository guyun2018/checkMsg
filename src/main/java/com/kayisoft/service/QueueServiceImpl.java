package com.kayisoft.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kayisoft.model.MsgBean;
import com.kayisoft.model.QueueBean;
import com.kayisoft.model.QueueUserInfo;
import com.kayisoft.util.GzUrl;
import com.kayisoft.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    @Autowired
    AccessTokenService accessTokenService;
    /**
     * 获取检查相关信息
     *
     * @param accessNo 检查号
     * @return result
     */
    @Override
    public List<QueueBean> getQueueInfo(String accessNo,String hospitalCode) {
        //获取检查排队信息List(包括姓名、检查号、检查时间、前面几人、排队名次？)
//        String forObject = restTemplate.getForObject("XXXX?accessNo=abcde", String.class);
//        JSONObject ojb = JSONObject.parseObject(forObject);
//        QueueBean queueBean = JSON.toJavaObject(ojb, QueueBean.class);
        List<QueueBean> list  = new ArrayList<>();
        list.add(new QueueBean("201807015487","47000593033030211A1001","温州医科大学附属第二医院","周宇婷","A101","A188",10,"CT",5));
        list.add(new QueueBean("3383075","47068179533038211A1001","乐清市第二人民医院","邹乙丑","A55","A100",5,"USMF",10));
        return list;
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

    @Override
    public Result sendMsg(String openId) {
        //获取检查排队信息List(包括姓名、检查号、检查时间、前面几人、排队名次？)
//        String forObject = restTemplate.getForObject("XXXX?accessNo=abcde", String.class);
//        JSONObject ojb = JSONObject.parseObject(forObject);
//        QueueBean queueBean = JSON.toJavaObject(ojb, QueueBean.class);
//        String opendId = "ou_oy1gWWxaoGhyljUVqrmh3mKrU";
        //通过openId到数据库查询病人ID,hospitalCode
        QueueUserInfo queueUserInfo = new QueueUserInfo();
        queueUserInfo.setAccessNo("dfsfsdfds");
        queueUserInfo.setHospitalCode("fer110");
        QueueUserInfo queueUserInfo1 = new QueueUserInfo();
        queueUserInfo1.setAccessNo("11dfsc3");
        queueUserInfo1.setHospitalCode("zyy101");
        List<QueueUserInfo> list = new ArrayList<>();
        list.add(queueUserInfo);
        list.add(queueUserInfo1);
        for (int i = 0;i<list.size();i++){
            //得到每个医院的检查数据（多条）
            List<QueueBean> queueInfo = getQueueInfo(list.get(i).getAccessNo(), list.get(i).getHospitalCode());
            for (QueueBean bean: queueInfo) {

                String str = "{\"first\":10,\"keyword1\":\"test\",\"keyword2\":\"sm\"}";
                MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name",new MsgBean(bean.getName(),"#173177"));
                jsonObject.put("queueNo",new MsgBean(bean.getQueueNo(),"#173177"));
                jsonObject.put("peopleNo",new MsgBean(bean.getPeopleNo(),"#173177"));
                jsonObject.put("modality",new MsgBean(bean.getModality(),"#173177"));
                jsonObject.put("number",new MsgBean(bean.getNumber().toString(),"#173177"));
                jsonObject.put("time",new MsgBean(bean.getTime().toString(),"#173177"));
                postParameters.add("touser",openId);
                postParameters.add("template_id","VyRkBgCRra5tuQ4Q4RKmXUIT-cp8rmUqdMdVLLKze-4");
//                postParameters.add("template_id","KgKKraydHLZWaXgXphe3BHbXU7d5KjAwCeTk_mBKz0s");
                postParameters.add("topcolor","#FF0000");
                postParameters.add("data", JSONObject.toJSONString(jsonObject));

                HttpHeaders headers = new HttpHeaders();
                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(postParameters,headers);
//                HttpEntity<String> requestEntity = new HttpEntity<String>(str);
                System.out.println(requestEntity);
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(GzUrl.getSendMsgUrl.getUrl()+
                        accessTokenService.getAccToken("AccessTokenCache"), requestEntity, String.class);
                String body = responseEntity.getBody();
            }
        }
        return null;
    }
}
