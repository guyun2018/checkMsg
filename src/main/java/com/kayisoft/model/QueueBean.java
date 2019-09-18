package com.kayisoft.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/4
 */
@Setter
@Getter
public class QueueBean {

    private String patientId;

    private String accessionNo;

    private String hospitalCode;

    private String hospitalName;

    private String patientName;

    private String queueNo;

    private String checkStatus;

    private String callId;

    private Integer beforeNum;

    private String scheduledModality;

    private String scheduledDate;

    private Integer time;

    private Integer isStart;

    private String checkDate;

    private String medcine;

    private String openId;

    private String callRoom;

    private Integer type;

    private String msg;

    public QueueBean() {
    }
}
