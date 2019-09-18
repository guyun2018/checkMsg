package com.kayisoft.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/18
 */
@Setter
@Getter
public class UrlBean{

    private Integer id;

    private String hospitalCode;

    private String hospitalName;

    private String hospitalUrl;

    private String createDate;

    private String updateDate;

}
