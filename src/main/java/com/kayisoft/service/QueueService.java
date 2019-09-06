package com.kayisoft.service;

import com.kayisoft.vo.Result;

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
    Result getQueueInfo(String accessNo);

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
}
