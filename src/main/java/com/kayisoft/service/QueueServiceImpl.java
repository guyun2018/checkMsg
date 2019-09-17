package com.kayisoft.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kayisoft.mapper.QueueUserInfoMapper;
import com.kayisoft.model.QueueBean;
import com.kayisoft.model.QueueUserInfo;
import com.kayisoft.model.TemplateData;
import com.kayisoft.model.WechatTemplate;
import com.kayisoft.util.DateUtil;
import com.kayisoft.util.GzUrl;
import com.kayisoft.util.PropertiesUtil;
import com.kayisoft.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${start_template_id}")
    private String startTemplateId;

    @Value("${not_start_template_id}")
    private String notStartTemplateId;

    /**
     * 获取检查相关信息（前置机获取信息）
     *
     * @param queueBean bean
     * @return result
     */
    @Override
    public List<QueueBean> getQueueInfo(QueueBean queueBean) {
        Map<String, Object> map = PropertiesUtil.getProfileByClassLoader("hospitalUrl.properties");
        String serverUrl= map.get(queueBean.getHospitalCode()).toString();
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
            QueueBean queueBean2 = new QueueBean("3383075", "47068179533038211A1001", "乐清市第二人民医院",
                    "邹乙丑", "A55", "A100", 5, "USMF", 10, 1);
            queueBean2.setCheckDep("DR机房");
            list.add(queueBean2);
            QueueBean queueBean1 = new QueueBean("201807015487", "47000593033030211A1001", "温州医科大学附属第二医院",
                    "邹乙丑", "A101", "A188", 10, "CT", DateUtil.dayDiff(DateUtil.getCurrentDate("yyyy-MM-dd"), "2019-9-11"), 0, "2019-9-11", DateUtil.getCurrentDate("yyyy-MM-dd"));
            list.add(queueBean1);
        }
        return list;
    }


    /**
     * 获取openId判断是否已关注
     * @param queueUserInfo queueUserInfo
     * @return result
     */
    @Override
    public Result getOpenId(QueueUserInfo queueUserInfo) {
        //从表中查询openId
        QueueUserInfo userInfo = queueUserInfoMapper.selectOpenId(queueUserInfo);
        if (userInfo != null) {
            return new Result(true, "已关注",userInfo.getOpenId());
        } else {
            return new Result(false, "未关注",null);
        }
    }

    /**
     * 发送模板消息(自主查询)
     * @param openId openId
     * @return result
     */
    @Override
    public Result sendMsg(String openId) {
        //获取检查排队信息List(包括姓名、检查号、检查时间、前面几人、排队名次？)
//        String forObject = restTemplate.getForObject("XXXX?accessNo=abcde", String.class);
//        JSONObject ojb = JSONObject.parseObject(forObject);
//        QueueBean queueBean = JSON.toJavaObject(ojb, QueueBean.class);
//        String opendId = "ou_oy1gWWxaoGhyljUVqrmh3mKrU";
        //通过openId到数据库查询病人ID,hospitalCode
        List<QueueUserInfo> list = queueUserInfoMapper.selectInfoByOpenId(openId);

        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTouser(openId);
        wechatTemplate.setTopcolor("#FF0000");
        if (list.size()>0) {
            for (int i = 0; i < list.size(); i++) {
                QueueBean queueBean = new QueueBean();
                queueBean.setTemp(1);
                BeanUtils.copyProperties(list.get(i), queueBean);
                //根据医院代码和访问号去每个医院查询相关检查信息（多条）
                List<QueueBean> queueInfo = getQueueInfo(queueBean);
                for (QueueBean bean : queueInfo) {
                    //检查未开始
                    Map<String, TemplateData> mapdata = new HashMap<>();
                    if (bean.getIsStart() == 0) {
                        //未开始的模板消息
                        mapdata.put("name", new TemplateData(bean.getName(), "#173177"));
                        mapdata.put("modality", new TemplateData(bean.getModality(), "#173177"));
                        mapdata.put("today", new TemplateData(bean.getToday(), "#173177"));
                        mapdata.put("time", new TemplateData(bean.getTime().toString(), "#173177"));
                        mapdata.put("hospitalName", new TemplateData(bean.getHospitalName(), "#173177"));
                        mapdata.put("checkDate", new TemplateData(bean.getCheckDate(), "#173177"));
                        wechatTemplate.setData(mapdata);
                        wechatTemplate.setTemplate_id(notStartTemplateId);
                    } else {
                        //正在排队的模板消息
                        mapdata.put("name", new TemplateData(bean.getName(), "#173177"));
                        mapdata.put("queueNo", new TemplateData(bean.getQueueNo(), "#173177"));
                        mapdata.put("peopleNo", new TemplateData(bean.getPeopleNo(), "#173177"));
                        mapdata.put("modality", new TemplateData(bean.getModality(), "#173177"));
                        mapdata.put("number", new TemplateData(bean.getNumber().toString(), "#173177"));
                        mapdata.put("time", new TemplateData(bean.getTime().toString(), "#173177"));
                        wechatTemplate.setData(mapdata);
                        wechatTemplate.setTemplate_id(startTemplateId);

                    }
                    ResponseEntity<String> responseEntity = restTemplate.postForEntity(GzUrl.getSendMsgUrl.getUrl() +
                            accessTokenService.getAccToken("AccessTokenCache"), JSON.toJSONString(wechatTemplate), String.class);
                    String body = responseEntity.getBody();
                }
            }
            return new Result(true, "已发送");
        }else {
            return new Result(false,"查询不到数据");
        }
    }

    /**
     * 插入openId（关注）
     * @param uuid uuid
     * @param openId openId
     */
    @Override
    public void addOpenId(String uuid, String openId) {
        QueueUserInfo queueUserInfo = new QueueUserInfo();
        queueUserInfo.setId(uuid);
        queueUserInfo.setOpenId(openId);
        queueUserInfoMapper.updateByPrimaryKeySelective(queueUserInfo);

    }

    /**
     * 删除openId(取消关注)
     * @param openId openId
     */
    @Override
    public void deleteOpenId(String openId) {
        queueUserInfoMapper.deleteByOpenId(openId);
    }

    /**
     * 发送消息模板（定时）
     * @param queueBean bean
     * @return result
     */
    @Override
    public Result sendTemplateMsg(QueueBean queueBean) {
        QueueUserInfo queueUserInfo = new QueueUserInfo();
        queueUserInfo.setAccessNo(queueBean.getAccessNo());
        queueUserInfo.setHospitalCode(queueBean.getHospitalCode());
        Result openIdInfo = getOpenId(queueUserInfo);
        if (!openIdInfo.isSuccess()){
            return new Result(false,"找不到openId",null);
        }
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTouser(openIdInfo.getObj().toString());
        wechatTemplate.setTopcolor("#FF0000");
        Map<String, TemplateData> mapdata = new HashMap<>();
        mapdata.put("name", new TemplateData(queueBean.getName(), "#173177"));
        mapdata.put("queueNo", new TemplateData(queueBean.getQueueNo(), "#173177"));
        mapdata.put("peopleNo", new TemplateData(queueBean.getPeopleNo(), "#173177"));
        mapdata.put("modality", new TemplateData(queueBean.getModality(), "#173177"));
        mapdata.put("number", new TemplateData(queueBean.getNumber().toString(), "#173177"));
        mapdata.put("time", new TemplateData(queueBean.getTime().toString(), "#173177"));
        wechatTemplate.setData(mapdata);
        wechatTemplate.setTemplate_id(startTemplateId);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GzUrl.getSendMsgUrl.getUrl() +
                accessTokenService.getAccToken("AccessTokenCache"), JSON.toJSONString(wechatTemplate), String.class);
        String msg = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
        String errcode = "errcode";
        if (jsonObject.getInteger(errcode) == 0) {
            return new Result(true,"模板发送成功",null);
        }else {
            return new Result(false,"消息发送失败",msg);
        }
    }
}
