package com.kayisoft.controller;

import com.kayisoft.model.QueueBean;
import com.kayisoft.model.QueueUserInfo;
import com.kayisoft.service.QueueService;
import com.kayisoft.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/4
 */
@Controller
@RequestMapping(value = "/api")
public class QueueController {

    @Autowired
    QueueService queueService;
    /**
     * 描扫检查单上二维码跳转至h5页面
     * @param request quest
     * @return str
     */
    @RequestMapping(value = "/checkQueue",method = RequestMethod.GET)
    public String checkQueue(HttpServletRequest request){
        String patientId = request.getParameter("patientId");
        String accessNo = request.getParameter("accessNo");
        String hospitalCode = request.getParameter("hospitalCode");
        QueueBean queueBean = new QueueBean();
        queueBean.setPatientId(patientId);
        queueBean.setAccessionNo(accessNo);
        queueBean.setHospitalCode(hospitalCode);
        queueBean.setType(0);
        QueueBean queueBean1 = queueService.getQueueInfo(queueBean);
        queueBean1.setPatientId(patientId);
        request.setAttribute("info",queueBean1);
        return "queue";
    }

//    /**
//     * 获取预约排队信息
//     * @param queueBean bean
//     * @return result
//     */
//    @RequestMapping(value = "/getQueueInfo",method = RequestMethod.POST)
//    @ResponseBody
//    public Result getQueueInfo(@RequestBody QueueBean queueBean){
//        return queueService.getQueueInfo(queueBean);
//    }

    /**
     * 根据accessNo和hospitalCode查询表中是否有openId
     * @param queueUserInfo bean
     * @return result
     */
    @RequestMapping(value = "/getOpenId",method = RequestMethod.POST)
    @ResponseBody
    public Result getOpenId(@RequestBody QueueUserInfo queueUserInfo){
        return queueService.getOpenId(queueUserInfo);
    }

    /**
     * 发送模板消息（定时）需要patientId
     * @param queueBean bean
     * @return result
     */
    @RequestMapping(value = "/sendTemplateMsg",method = RequestMethod.POST)
    @ResponseBody
    public Result sendTemplateMsg(@RequestBody QueueBean queueBean){
        return queueService.sendTemplateMsg(queueBean);
    }

    /**
     * 一键签到
     * @return
     */
    @RequestMapping(value = "/checkSignIn")
    @ResponseBody
    public Result checkSignIn(@RequestBody QueueBean queueBean){
        Result result = queueService.checkSignIn(queueBean);
        return null;
    }
}
