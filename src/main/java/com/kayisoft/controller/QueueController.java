package com.kayisoft.controller;

import com.kayisoft.model.QueueBean;
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
        String accessNo = request.getParameter("accessNo");
        String hospitalCode = request.getParameter("hospitalCode");
        List<QueueBean> list = queueService.getQueueInfo(accessNo, hospitalCode);
        request.setAttribute("queueList",list);
        request.setAttribute("accessNo",accessNo);
        request.setAttribute("hospitalCode",hospitalCode);
        return "queue";
    }

    /**
     * 获取预约排队信息
     * @param accessNo acc
     * @return result
     */
//    @RequestMapping(value = "/getQueueInfo",method = RequestMethod.POST)
//    @ResponseBody
//    public Result getQueueInfo(@RequestParam("accessNo") String accessNo){
//        System.out.println(accessNo);
//        return queueService.getQueueInfo(accessNo);
//    }

    @RequestMapping(value = "/getOpenId",method = RequestMethod.POST)
    @ResponseBody
    public Result getOpenId(@RequestParam("accessNo") String accessNo){
        return queueService.getOpenId(accessNo);
    }
}
