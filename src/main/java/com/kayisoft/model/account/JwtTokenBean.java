package com.kayisoft.model.account;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author: ruopeng.cheng
 * @date: 2019/4/18 9:19
 */
@Setter
@Getter
public class JwtTokenBean {

    private String accessToken;


    private String tokenType;


    private Integer expiresIn;


    private String refreshToken;

    private Boolean overdue =false;

    private Date date;
}
