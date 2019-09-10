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
    List<QueueBean> getQueueInfo(QueueBean queueBean);



    /**
     *生成二维码
     * @param queueUserInfo queueUserInfo
     * @return result
     */
    Result getOpenId(QueueUserInfo queueUserInfo);

    Result sendMsg(String openId);


    void addOpenId(String uuid,String openId);

    void deleteOpenId(String openId);
}
