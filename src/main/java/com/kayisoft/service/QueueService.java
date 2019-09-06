package com.kayisoft.service;

import com.kayisoft.model.QueueBean;
import com.kayisoft.vo.Result;

import java.util.List;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/4
 */
public interface QueueService {
    /**
     * 获取检查相关信息
     * @param accessNo 检查号
     * @return result
     */
    List<QueueBean> getQueueInfo(String accessNo,String hospitalCode);

    /**
     * 去关注
     * @param accessNo acc
     * @return result
     */
    Result toFollow(String accessNo);

    /**
     *
     * @param accessNo
     * @return
     */
    Result getOpenId(String accessNo);

    Result sendMsg(String openId);
}
