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
    private String accessNo;

    private String hospitalCode;

    private String hospitalName;

    private String name;

    private String queueNo;

    private String peopleNo;

    private Integer number;

    private String modality;

    private Integer time;

    private Integer isStart;

    private String checkDate;

    private String today;

    public QueueBean() {
    }

    public QueueBean( String checkDate, String today) {
        this.checkDate = checkDate;
        this.today = today;
    }

    public QueueBean(String accessNo, String hospitalCode, String hospitalName, String name, String queueNo,
                     String peopleNo, Integer number, String modality, Integer time, Integer isStart, String checkDate, String today) {
        this.accessNo = accessNo;
        this.hospitalCode = hospitalCode;
        this.hospitalName = hospitalName;
        this.name = name;
        this.queueNo = queueNo;
        this.peopleNo = peopleNo;
        this.number = number;
        this.modality = modality;
        this.time = time;
        this.isStart = isStart;
        this.checkDate = checkDate;
        this.today = today;
    }

    public QueueBean(String accessNo, String hospitalCode, String hospitalName, String name, String queueNo, String peopleNo, Integer number, String modality, Integer time, Integer isStart) {
        this.accessNo = accessNo;
        this.hospitalCode= hospitalCode;
        this.hospitalName = hospitalName;
        this.name = name;
        this.queueNo = queueNo;
        this.peopleNo = peopleNo;
        this.number = number;
        this.modality = modality;
        this.time = time;


        this.isStart = isStart;
    }
}
