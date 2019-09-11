package com.kayisoft.service;


import com.kayisoft.model.account.ManageUserBean;
import com.kayisoft.model.account.UserManageBean;
import com.kayisoft.vo.Result;

/**
 * @author: ruopeng.cheng
 * @date: 2019/4/12 10:14
 */
public interface UserManageInterfaceService {

    /**
     *获取TOKEN
     * @param userBean MODEL
     * @return result
     */
     Result getToken(ManageUserBean userBean);


    /**
     * 获取用户信息
     * @param userManageBean model
     * @return result
     */
    Result getUserInfo(UserManageBean userManageBean);

}
