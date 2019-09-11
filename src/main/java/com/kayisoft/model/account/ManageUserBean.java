package com.kayisoft.model.account;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: ruopeng.cheng
 * @Date: 2018/11/8 15:38
 */
@Setter
@Getter
public class ManageUserBean implements Serializable {

    private String id;

    private String username;

    private String password;

    private String telephone;

    private String code;

    private String manageUserId;

    private String manageUserTrueName;

    private String userId;
}
