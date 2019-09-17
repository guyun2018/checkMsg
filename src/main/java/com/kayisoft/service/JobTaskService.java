package com.kayisoft.service;


import com.kayisoft.model.account.QuartzBean;
import com.kayisoft.model.account.ZxSysJob;
import com.kayisoft.vo.KendoGrid;
import com.kayisoft.vo.Result;

import java.util.List;

/**
 * @Author: ruopeng.cheng
 * @Date: 2018/9/17 16:48
 */

public interface JobTaskService {

    /**
     * true 存在 false 不存在
     *
     * @param zxSysJob model
     * @return bool
     */
    boolean checkJob(ZxSysJob zxSysJob);

    /**
     * 开启
     *
     * @param job model
     * @return bool
     */
    boolean startJob(ZxSysJob job);

    /**
     * 更新
     *
     * @param job model
     * @return bool
     */
    boolean updateJob(ZxSysJob job);

    /**
     * 删除
     *
     * @param job model
     * @return bool
     */
    boolean remove(ZxSysJob job);

    /**
     * 获取列表
     *
     * @param quartzBean bean
     * @return kendo
     */
    KendoGrid getQuartzList(QuartzBean quartzBean);

    /**
     * 获取model
     *
     * @param id id
     * @return model
     */
    ZxSysJob selectQuartzById(String id);

    /**
     * 编辑
     *
     * @param quartzBean model
     * @return result
     */
    Result addAndUpdateQuartz(QuartzBean quartzBean);

    /**
     * 根据id删除数据
     *
     * @param quartzBean bean
     * @return RESULT
     */
    Result deleteById(QuartzBean quartzBean);

    /**
     * 启动作业
     * @param quartzBean bean
     * @return result
     */
    Result updateStartJob(QuartzBean quartzBean);

    /**
     * 停止作业
     * @param quartzBean bean
     * @return result
     */
    Result updateStopJob(QuartzBean quartzBean);

    /**
     * 获取列表
     * @param job model
     * @return list
     */
    List<ZxSysJob> selectListByPage(ZxSysJob job);
}
