package com.kayisoft.model.account;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: ruopeng.cheng
 * @Date: 2018/9/18 9:57
 */
@Getter
@Setter
public class QuartzBean extends BasePageBean {

    private String id;

    private String jobName;

    private String cron;

    private Integer jobStatus;

    private String classPath;

    private String jobDesc;

    private Date createTime;

    private String systemCode;
}
