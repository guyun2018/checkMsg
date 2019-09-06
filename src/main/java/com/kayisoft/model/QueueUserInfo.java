package com.kayisoft.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/6
 */
@Setter
@Getter
public class QueueUserInfo {
    private String id;
    private String accessNo;
    private String hospitalCode;
    private Date createDate;
    private Date updateDate;
    private String openId;
}
