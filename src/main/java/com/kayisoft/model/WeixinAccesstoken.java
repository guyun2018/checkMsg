package com.kayisoft.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * WEIXIN_ACCESSTOKEN
 * @author 
 */

@Setter
@Getter
public class WeixinAccesstoken implements Serializable {
    private Long id;

    private String accessToken;

    private Date effectiveTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;


}