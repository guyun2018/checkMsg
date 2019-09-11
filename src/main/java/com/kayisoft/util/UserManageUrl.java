package com.kayisoft.util;

/**
 * @author: ruopeng.cheng
 * @date: 2019/4/12 13:26
 */

public enum UserManageUrl {



    /**
     * 获取Token
     */
    getToken("https://sso.kayicloud.com/oauth2/token"),
    /**
     * 分页获取角色列表
     */
    getRolePageList("https://sso.kayicloud.com/api/role/pagelist"),

    /**
     * 添加或修改角色
     */
    addOrUpdateRole("https://sso.kayicloud.com/api/role/merge"),

    /**
     * 获取角色信息
     */
    getRoleInfo("https://sso.kayicloud.com/api/role/mod"),

    /**
     * 获取权限列表
     */
    getAuthorityList("https://sso.kayicloud.com/api/right/pagelist"),
    /**
     * 新增或修改权限
     */
    addOrUpdateAuthority("https://sso.kayicloud.com/api/right/merge"),

    /**
     * 获取权限信息
     */
    getAuthorityInfo("https://sso.kayicloud.com/api/right/mod"),

    /**
     * 获取权限树
     */
    getAuthorityTreeInfo("https://sso.kayicloud.com/api/right/treelist"),

    /**
     * 获取父级权限列表
     */
    getAuthorityParentList("https://sso.kayicloud.com/api/right/parentlist"),

    /**
     * 删除
     */
    deleteAuthority("https://sso.kayicloud.com/api/right/delete"),

    /**
     * 删除
     */
    deleteRole("https://sso.kayicloud.com/api/role/delete"),
    /**
     * 获取用户列表
     */
    getUserList("https://sso.kayicloud.com/api/user/pagelist"),
    /**
     * 修改 新增用户
     */
    addOrUpdateUser("https://sso.kayicloud.com/api/user/merge"),
    /**
     *   获取用户信息
      */
    getUserInfo("https://sso.kayicloud.com/api/user/mod"),
    /**
     * 删除用户
     */
    deleteUser("https://sso.kayicloud.com/api/user/delete"),

    /**
     * 检查登录
     */
    checkLogin("https://sso.kayicloud.com/api/check/login"),

    /**
     * updateToken
     */
    updateToken("https://sso.kayicloud.com/oauth2/token"),

    /**
     * roleRightTree
     */
    roleRightTree("https://sso.kayicloud.com/api/user/rolerighttree"),
    /**
     * 权限设置
     */
    roleRightSet("https://sso.kayicloud.com/api/user/rolerightset"),

    /**
     * 重设密码
     */
    resetPassword("https://sso.kayicloud.com/api/user/rstpwd")
    ;


    private String url;



    UserManageUrl(String url){
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
