package com.kayisoft.controller;

import com.kayisoft.model.QueueBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/3
 */
@Controller
public class Test {
    @RequestMapping(value = "/test")
    public String test(HttpServletRequest request){
        QueueBean queueBean = new QueueBean();
        queueBean.setHospitalCode("1");
        queueBean.setHospitalName("二医");
        QueueBean queueBean1 = new QueueBean();
        queueBean1.setHospitalCode("2");
        queueBean1.setHospitalName("中医院");
        List<QueueBean> list = new ArrayList<>();
        list.add(queueBean);
        list.add(queueBean1);
        request.setAttribute("users",list);
        return "test";
    }
}
