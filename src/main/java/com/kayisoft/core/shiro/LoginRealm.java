package com.kayisoft.core.shiro;


import com.kayisoft.model.account.CustomUsernamePasswordToken;
import com.kayisoft.model.account.ManageUserBean;
import com.kayisoft.model.account.UserManageBean;
import com.kayisoft.service.UserManageInterfaceService;
import com.kayisoft.util.Md5Util;
import com.kayisoft.vo.Result;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: ruopeng.cheng
 * @date: 2019/8/2 9:10
 */
@Service
public class LoginRealm extends AuthorizingRealm {


    @Autowired
    UserManageInterfaceService userManageInterfaceService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        CustomUsernamePasswordToken token = (CustomUsernamePasswordToken)authenticationToken;
        ManageUserBean userBean = new ManageUserBean();
        userBean.setPassword(String.valueOf(token.getPassword()));
        userBean.setUsername(token.getUsername());
        Result result = userManageInterfaceService.getToken(userBean);
        UserManageBean userManageBean = new UserManageBean();
        userManageBean.setUserId(userBean.getUserId());
        userManageBean.setCurrentUser(userBean.getUserId());
        Result result1 = userManageInterfaceService.getUserInfo(userManageBean);
        userManageBean = (UserManageBean) result1.getObj();
        userBean.setManageUserTrueName(userManageBean.getManageUserTrueName());
        userBean.setManageUserId(userManageBean.getManageUserId());
        ByteSource byteSource = ByteSource.Util.bytes(userBean.getUsername());
        if (!result.isSuccess()) {
            throw new UnknownAccountException("用户不存在！");
        } else {
            return new SimpleAuthenticationInfo(userBean, Md5Util.getMD5(userBean.getPassword(),userBean.getUsername()),byteSource, getName());
        }
    }
}
