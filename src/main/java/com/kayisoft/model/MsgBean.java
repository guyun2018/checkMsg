package com.kayisoft.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/6
 */
@Setter
@Getter
public class MsgBean {
    private String value;

    private String color;
    public MsgBean() {
    }

    public MsgBean(String value) {
        this.value = value;
    }

    public MsgBean(String value, String color) {
        this.value = value;

        this.color = color;

    }
}
