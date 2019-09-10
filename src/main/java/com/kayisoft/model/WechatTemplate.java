package com.kayisoft.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/9
 */
@Getter
@Setter
public class WechatTemplate {

    private String touser;

    private String template_id;

    private String topcolor;

    private String url;

    private Map<String, TemplateData> data;
}
