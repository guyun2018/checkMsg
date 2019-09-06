package com.kayisoft.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author ruopeng.cheng
 * @date：2018.7.11
 */
@SuppressWarnings(value = "unused")
@Getter
@Setter
public class Result implements Serializable {

    public static final int SUCCESS = 1;
    public static final int FAILURE = -1;

    private static final long serialVersionUID = 5576237395711742681L;

    private boolean success = false;

    private String msg = "";

    private Object obj = null;

    private List<?> results;

    public Result() {
    }

    public Result(boolean status, String msg) {
        this.success = status;
        this.msg = msg;
    }

    public Result(boolean success, String msg, Object obj) {
        this.success = success;
        this.msg = msg;
        this.obj = obj;
    }
}
