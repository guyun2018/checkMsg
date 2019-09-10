package com.kayisoft.service;

import com.alibaba.fastjson.JSON;
import com.kayisoft.mapper.QueueUserInfoMapper;
import com.kayisoft.model.QueueBean;
import com.kayisoft.model.QueueUserInfo;
import com.kayisoft.model.TemplateData;
import com.kayisoft.model.WechatTemplate;
import com.kayisoft.util.DateUtil;
import com.kayisoft.util.GzUrl;
import com.kayisoft.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    QueueUserInfoMapper queueUserInfoMapper;

    /**
     * 获取检查相关信息
     *
     * @param queueBean bean
     * @return result
     */
    @Override
    public List<QueueBean> getQueueInfo(QueueBean queueBean) {
        //获取检查排队信息List(包括姓名、检查号、检查时间、前面几人、排队名次？)
//        String forObject = restTemplate.getForObject("XXXX?accessNo=abcde", String.class);
//        JSONObject ojb = JSONObject.parseObject(forObject);
//        QueueBean queueBean = JSON.toJavaObject(ojb, QueueBean.class);
        List<QueueBean> list = new ArrayList<>();
        if (queueBean.getHospitalCode().equals("fer110")) {
            list.add(new QueueBean("3383075", "47068179533038211A1001", "乐清市第二人民医院",
                    "周宇婷", "A55", "A100", 5, "USMF", 10, 1));
            list.add(new QueueBean("201807015487", "47000593033030211A1001", "温州医科大学附属第二医院",
                    "周宇婷", "A101", "A188", 10, "CT", DateUtil.dayDiff(DateUtil.getCurrentDate("yyyy-MM-dd"), "2019-9-15"), 0, "2019-9-15", DateUtil.getCurrentDate("yyyy-MM-dd")));
        } else {
            list.add(new QueueBean("3383075", "47068179533038211A1001", "乐清市第二人民医院",
                    "邹乙丑", "A55", "A100", 5, "USMF", 10, 1));
            list.add(new QueueBean("201807015487", "47000593033030211A1001", "温州医科大学附属第二医院",
                    "邹乙丑", "A101", "A188", 10, "CT", DateUtil.dayDiff(DateUtil.getCurrentDate("yyyy-MM-dd"), "2019-9-11"), 0, "2019-9-11", DateUtil.getCurrentDate("yyyy-MM-dd")));
        }
        return list;
    }


    /**
     * @param
     * @return
     */
    @Override
    public Result getOpenId(QueueUserInfo queueUserInfo) {
        //从表中查询openId
        QueueUserInfo userInfo = queueUserInfoMapper.selectOpenId(queueUserInfo);
        if (userInfo != null) {
            return new Result(true, "已关注");
        } else {
            return new Result(false, "未关注");
        }
    }

    @Override
    public Result sendMsg(String openId) {
        //获取检查排队信息List(包括姓名、检查号、检查时间、前面几人、排队名次？)
//        String forObject = restTemplate.getForObject("XXXX?accessNo=abcde", String.class);
//        JSONObject ojb = JSONObject.parseObject(forObject);
//        QueueBean queueBean = JSON.toJavaObject(ojb, QueueBean.class);
//        String opendId = "ou_oy1gWWxaoGhyljUVqrmh3mKrU";
        //通过openId到数据库查询病人ID,hospitalCode
        //附二医的检查
        QueueBean queueBean = new QueueBean();
        queueBean.setAccessNo("dfsfsdfds");
        queueBean.setHospitalCode("fer110");

        //中医院的检查
        QueueBean queueBean1 = new QueueBean();
        queueBean1.setAccessNo("11dfsc3");
        queueBean1.setHospitalCode("zyy101");

        List<QueueBean> list = new ArrayList<>();
        list.add(queueBean);
        list.add(queueBean1);


        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTouser(openId);
        wechatTemplate.setTopcolor("#FF0000");

        for (int i = 0; i < list.size(); i++) {
            //根据医院代码和访问号去每个医院查询相关检查信息（多条）
            List<QueueBean> queueInfo = getQueueInfo(list.get(i));
            for (QueueBean bean : queueInfo) {
                //检查未开始
                if (bean.getIsStart() == 0) {
                    //使用未开始的消息模板
                    Map<String, TemplateData> mapdata = new HashMap<>();
                    TemplateData templateData = new TemplateData(bean.getName(), "#173177");
                    TemplateData templateData1 = new TemplateData(bean.getModality(), "#173177");
                    TemplateData templateData2 = new TemplateData(bean.getToday(), "#173177");
                    TemplateData templateData3 = new TemplateData(bean.getTime().toString(), "#173177");
                    TemplateData templateData4 = new TemplateData(bean.getHospitalName(), "#173177");
                    mapdata.put("name", templateData);
                    mapdata.put("modality", templateData1);
                    mapdata.put("today", templateData2);
                    mapdata.put("time", templateData3);
                    mapdata.put("hospitalName", templateData4);
                    mapdata.put("checkDate", new TemplateData(bean.getCheckDate(), "#173177"));
                    wechatTemplate.setData(mapdata);
                    wechatTemplate.setTemplate_id("v42XdeqUZ-Q8PHUg7xJ1idVYwV0e8NMKNteJi_H8dec");
                } else {
                    Map<String, TemplateData> mapdata = new HashMap<>();
                    TemplateData templateData = new TemplateData(bean.getName(), "#173177");
                    TemplateData templateData1 = new TemplateData(bean.getQueueNo(), "#173177");
                    TemplateData templateData2 = new TemplateData(bean.getPeopleNo(), "#173177");
                    TemplateData templateData3 = new TemplateData(bean.getModality(), "#173177");
                    TemplateData templateData4 = new TemplateData(bean.getNumber().toString(), "#173177");
                    TemplateData templateData5 = new TemplateData(bean.getTime().toString(), "#173177");
                    mapdata.put("name", templateData);
                    mapdata.put("queueNo", templateData1);
                    mapdata.put("peopleNo", templateData2);
                    mapdata.put("modality", templateData3);
                    mapdata.put("number", templateData4);
                    mapdata.put("time", templateData5);
                    wechatTemplate.setData(mapdata);
                    wechatTemplate.setTemplate_id("cbQZ0GVvr903-KU_8nqxvbVNwgsOG-kioznel2XeENo");

                }
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(GzUrl.getSendMsgUrl.getUrl() +
                        accessTokenService.getAccToken("AccessTokenCache"), JSON.toJSONString(wechatTemplate), String.class);
                String body = responseEntity.getBody();
            }
        }
        return new Result(true, "已发送");
    }

    @Override
    public void addOpenId(String uuid, String openId) {
        QueueUserInfo queueUserInfo = new QueueUserInfo();
        queueUserInfo.setId(uuid);
        queueUserInfo.setOpenId(openId);
        queueUserInfoMapper.updateByPrimaryKeySelective(queueUserInfo);

    }

    @Override
    public void deleteOpenId(String openId) {
        queueUserInfoMapper.deleteByOpenId(openId);
    }
}
