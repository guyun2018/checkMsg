package com.kayisoft.service;

import com.kayisoft.model.QueueBean;
import com.kayisoft.model.QueueUserInfo;
import com.kayisoft.vo.Result;

import java.util.List;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/4
 */
public interface QueueService {
    /**
     * 获取检查相关信息
     * @param queueBean bean
     * @return result
     */
    QueueBean getQueueInfo(QueueBean queueBean);


    /**
     * 获取openId判断是否已关注
     * @param queueUserInfo queueUserInfo
     * @return result
     */
    Result getOpenId(QueueUserInfo queueUserInfo);

    /**
     * 发送模板消息
     * @param openId openId
     * @return result
     */
    Result sendMsg(String openId);

    /**
     * 插入openId（关注）
     * @param uuid uuid
     * @param openId openId
     */
    void addOpenId(String uuid,String openId);

    /**
     * 删除openId(取消关注)
     * @param openId openId
     */
    void deleteOpenId(String openId);

    /**
     * 发送消息模板（定时）
     * @param queueBean bean
     * @return result
     */
    Result sendTemplateMsg(QueueBean queueBean);

    /**
     * 一键签到
     * @param queueBean queueBean
     * @return result
     */
    Result checkSignIn(QueueBean queueBean);

    Result sendTemplateByOpenId(String openId);
}
