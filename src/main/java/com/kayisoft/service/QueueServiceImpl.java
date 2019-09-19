package com.kayisoft.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kayisoft.mapper.QueueUserInfoMapper;
import com.kayisoft.mapper.WxHospitalUrlMapper;
import com.kayisoft.model.*;
import com.kayisoft.util.DateUtil;
import com.kayisoft.util.GzUrl;
import com.kayisoft.util.PropertiesUtil;
import com.kayisoft.util.UserManageUrl;
import com.kayisoft.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    @Autowired
    WxHospitalUrlMapper wxHospitalUrlMapper;

    /**
     * 获取检查相关信息（前置机获取信息）
     *
     * @param queueBean bean
     * @return result
     */
    @Override
    public QueueBean getQueueInfo(QueueBean queueBean) throws Exception {
//        Map<String, Object> map = PropertiesUtil.getProfileByClassLoader("hospitalUrl.properties");
//        String serverUrl = map.get(queueBean.getHospitalCode()).toString();
        UrlBean urlBean = wxHospitalUrlMapper.selectByHospitalCode(queueBean.getHospitalCode());
        if (urlBean == null) {
            throw new Exception("找不到服务器路径");
        }
        String url = "http://" + urlBean.getHospitalUrl() + ":10003/api/addList";
        Map map = new HashMap();
        map.put("accessionNo", queueBean.getAccessionNo());
        map.put("hospitalCode", queueBean.getHospitalCode());
        map.put("type", queueBean.getType());
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map> requestEntity = new HttpEntity<>(map, header);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        String body = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        if (jsonObject.getBoolean("success")) {
            QueueBean bean = JSON.toJavaObject(jsonObject.getJSONObject("obj"), QueueBean.class);
            if (bean != null) {
                bean.setScheduledDate(DateUtil.timeStamp2Date(bean.getScheduledDate(), "yyyy-MM-dd HH:mm"));
            }
//        QueueBean queueBean1 = new QueueBean();
//        queueBean1.setHospitalCode("47068179533038211A1001");
//        queueBean1.setAccessionNo("abcde110");
//        queueBean1.setQueueNo("A05");
//        queueBean1.setCallId("A10");
//        queueBean1.setBeforeNum(5);
//        queueBean1.setPatientName("周宇婷");
//        queueBean1.setCallRoom("五楼彩超1室");
//        queueBean1.setScheduledModality("USMF");
//        queueBean1.setScheduledDate("2019-09-17 16:00:36");
//        queueBean1.setIsStart(1);
//        queueBean1.setCheckStatus("签到");
//        queueBean1.setPatientId("125200");
            //模拟type为1时消息模板自动回复
//            if (queueBean.getType() == 1) {
//                Result result = sendTemplateMsg(bean);
//            }
            return bean;
        } else {
            queueBean.setMsg(jsonObject.getString("msg"));
            return queueBean;
        }
    }


    /**
     * 获取openId判断是否已关注
     *
     * @param queueUserInfo queueUserInfo
     * @return result
     */
    @Override
    public Result getOpenId(QueueUserInfo queueUserInfo) {
        //从表中查询openId
        QueueUserInfo userInfo = queueUserInfoMapper.selectOpenId(queueUserInfo);
        if (userInfo != null) {
            return new Result(true, "已关注", userInfo.getOpenId());
        } else {
            return new Result(false, "未关注", null);
        }
    }

    /**
     * 发送模板消息(自主查询，可查询相关检查)
     *
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
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                QueueBean queueBean = new QueueBean();
                queueBean.setType(1);
                BeanUtils.copyProperties(list.get(i), queueBean);
                //根据医院代码和访问号去每个医院查询相关检查信息（多条）
//                List<QueueBean> queueInfo = getQueueInfo(queueBean);
                List<QueueBean> queueInfo = new ArrayList<>();
                for (QueueBean bean : queueInfo) {
                    //检查未开始
                    Map<String, TemplateData> mapdata = new HashMap<>();
                    if (bean.getIsStart() == 0) {
                        //未开始的模板消息
                        mapdata.put("name", new TemplateData(bean.getPatientName(), "#173177"));
                        mapdata.put("modality", new TemplateData(bean.getScheduledModality(), "#173177"));
                        mapdata.put("time", new TemplateData(bean.getTime().toString(), "#173177"));
                        mapdata.put("hospitalName", new TemplateData(bean.getHospitalName(), "#173177"));
                        mapdata.put("checkDate", new TemplateData(bean.getCheckDate(), "#173177"));
                        wechatTemplate.setData(mapdata);
                        wechatTemplate.setTemplate_id(notStartTemplateId);
                    } else {
                        //正在排队的模板消息
                        Result result = sendTemplateMsg(bean);

                    }
                    ResponseEntity<String> responseEntity = restTemplate.postForEntity(GzUrl.getSendMsgUrl.getUrl() +
                            accessTokenService.getAccToken("AccessTokenCache"), JSON.toJSONString(wechatTemplate), String.class);
                    String body = responseEntity.getBody();
                }
            }
            return new Result(true, "已发送");
        } else {
            return new Result(false, "查询不到数据");
        }
    }

    /**
     * 插入openId（关注）
     *
     * @param uuid   uuid
     * @param openId openId
     */
    @Override
    public void addOpenId(String uuid, String openId) throws Exception {
        QueueUserInfo queueUserInfo = new QueueUserInfo();
        queueUserInfo.setId(uuid);
        queueUserInfo.setOpenId(openId);
        //插入openId
        queueUserInfoMapper.updateByPrimaryKeySelective(queueUserInfo);
        //根据uuid前置机发送模板消息
        List<QueueUserInfo> list = queueUserInfoMapper.selectAccNoById(uuid);
        castToSendTemplate(list);
    }


    /**
     * 删除openId(取消关注)
     *
     * @param openId openId
     */
    @Override
    public void deleteOpenId(String openId) {
        queueUserInfoMapper.deleteByOpenId(openId);
    }

    /**
     * 发送消息模板（定时）
     *
     * @param queueBean bean
     * @return result
     */
    @Override
    public Result sendTemplateMsg(QueueBean queueBean) {
        QueueUserInfo queueUserInfo = new QueueUserInfo();
        //通过patiendId查询openId
        queueUserInfo.setPatientId(queueBean.getPatientId());
        queueUserInfo.setHospitalCode(queueBean.getHospitalCode());
        Result openIdInfo = getOpenId(queueUserInfo);
        if (!openIdInfo.isSuccess()) {
            return new Result(false, "找不到openId", null);
        }
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTouser(openIdInfo.getObj().toString());
        wechatTemplate.setTopcolor("#FF0000");
        Map<String, TemplateData> mapdata = new HashMap<>();
        mapdata.put("name", new TemplateData(queueBean.getPatientName(), "#173177"));
        mapdata.put("queueNo", new TemplateData(queueBean.getQueueNo(), "#173177"));
        mapdata.put("peopleNo", new TemplateData(queueBean.getCallId(), "#173177"));
        mapdata.put("modality", new TemplateData(queueBean.getScheduledModality(), "#173177"));
        mapdata.put("number", new TemplateData(queueBean.getBeforeNum().toString(), "#173177"));
        mapdata.put("checkDate", new TemplateData(queueBean.getScheduledDate(), "#173177"));
        mapdata.put("checkDep", new TemplateData(queueBean.getCallRoom(), "#173177"));
        wechatTemplate.setData(mapdata);
        if ("已签到".equals(queueBean.getCheckStatus())) {
            wechatTemplate.setTemplate_id(startTemplateId);
        } else {
            wechatTemplate.setTemplate_id(notStartTemplateId);
        }
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GzUrl.getSendMsgUrl.getUrl() +
                accessTokenService.getAccToken("AccessTokenCache"), JSON.toJSONString(wechatTemplate), String.class);
        String msg = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
        String errcode = "errcode";
        if (jsonObject.getInteger(errcode) == 0) {
            return new Result(true, "模板发送成功", null);
        } else {
            return new Result(false, "消息发送失败", msg);
        }
    }

    /**
     * 一键签到
     *
     * @param queueBean queueBean
     * @return result
     */
    @Override
    public Result checkSignIn(QueueBean queueBean) {
        //根据openId查询accessNo和hospitalCode
        UrlBean urlBean = wxHospitalUrlMapper.selectByHospitalCode(queueBean.getHospitalCode());
        String url = "http://" + urlBean.getHospitalUrl() + ":10003/api/check";
        Map map = new HashMap();
        map.put("accessionNo", queueBean.getAccessionNo());
        map.put("hospitalCode", queueBean.getHospitalCode());
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map> requestEntity = new HttpEntity<>(map, header);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        String body = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        Result result = JSON.toJavaObject(jsonObject, Result.class);
        return result;
    }

    @Override
    public Result sendTemplateByOpenId(String openId) throws Exception {
        List<QueueUserInfo> list = queueUserInfoMapper.selectInfoByOpenId(openId);
        if (list.size() == 0) {
            return new Result(false, "查询失败");
        }
        //根据openId前置机回复模板消息（回复【1】）
        castToSendTemplate(list);
        return new Result(true, "发送成功");
    }

    public void castToSendTemplate(List<QueueUserInfo> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            QueueUserInfo queueUserInfo1 = list.get(i);
            QueueBean queueBean = new QueueBean();
            //类型设置为1，则代表自动推送模板消息
            queueBean.setAccessionNo(queueUserInfo1.getAccessNo());
            queueBean.setHospitalCode(queueUserInfo1.getHospitalCode());
            queueBean.setType(1);
            getQueueInfo(queueBean);
        }
    }
}
