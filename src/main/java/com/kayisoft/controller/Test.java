package com.kayisoft.controller;

import com.kayisoft.model.QueueBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/3
 */
@Controller
@PropertySource(value = {"classpath:test.properties"})
public class Test {

    @Value("${test}")
    private String test;

    @RequestMapping(value = "/show")
    @ResponseBody
    public String show() {
        String str = test;
        return str;
    }

//    @RequestMapping(value = "/refresh")
//    @ResponseBody
//    public String refresh(){
//        String str = test;
//        return str;
//    }


    @RequestMapping(value = "/test")
    public String test(HttpServletRequest request) {
        QueueBean queueBean = new QueueBean();
        queueBean.setHospitalCode("1");
        queueBean.setHospitalName("二医");
        QueueBean queueBean1 = new QueueBean();
        queueBean1.setHospitalCode("2");
        queueBean1.setHospitalName("中医院");
        List<QueueBean> list = new ArrayList<>();
        list.add(queueBean);
        list.add(queueBean1);
        request.setAttribute("users", list);
        return "test";
    }
//    public static void readProperties() {
//        Properties props = new Properties();
//        InputStream fis = Createxsxl.class
//                .getResourceAsStream("/test.properties");
//        try {
//            props.load(fis);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        String name = props.getProperty(key);
//        System.out.println(name);
//    }

    /**
     * 添加配置文件信息
     *
     * @param filePath
     * @param content
     */
    public static void addMessageFile(String filePath, String content) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(filePath, "rw");
            raf.seek(raf.length());
            raf.write(content.getBytes());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    @RequestMapping(value = "/test1")
    @ResponseBody
    public String test1(HttpServletRequest request) {
        String key = request.getParameter("key");
        String value = request.getParameter("value");
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        return "";
    }

}
