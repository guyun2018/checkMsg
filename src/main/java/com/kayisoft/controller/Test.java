package com.kayisoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/3
 */
@Controller
public class Test {
    @RequestMapping(value = "/test")
    public String test(){
        System.out.println("AAA");
        return "test";
    }
}
