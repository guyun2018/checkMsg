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

    private Integer peopleNo;

    private String modality;

    public QueueBean() {
    }

    public QueueBean(String accessNo, String hospitalCode, String hospitalName, String name, String queueNo, Integer peopleNo,String modality) {
        this.accessNo = accessNo;
        this.hospitalCode= hospitalCode;
        this.hospitalName = hospitalName;
        this.name = name;
        this.queueNo = queueNo;
        this.peopleNo = peopleNo;
        this.modality = modality;
    }
}
