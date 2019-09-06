package com.kayisoft.util;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/6
 */
public enum GzUrl {
    getSendMsgUrl("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=");

    private String url;
    GzUrl(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
