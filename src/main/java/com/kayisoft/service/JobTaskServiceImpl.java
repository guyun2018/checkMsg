package com.kayisoft.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kayisoft.mapper.ZxSysJobMapper;

import com.kayisoft.model.account.QuartzBean;
import com.kayisoft.model.account.ZxSysJob;
import com.kayisoft.util.CommonUtil;
import com.kayisoft.vo.KendoGrid;
import com.kayisoft.vo.Result;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;


/**
 * @author Administrator
 */
@Service
@Log4j2
public class JobTaskServiceImpl implements JobTaskService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private ZxSysJobMapper zxSysJobMapper;


    /**
     * true 存在 false 不存在
     *
     * @param job model
     * @return bool
     */
    @Override
    public boolean checkJob(ZxSysJob job) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), Scheduler.DEFAULT_GROUP);
        try {
            if (scheduler.checkExists(triggerKey)) {
                return true;
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 开启
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean startJob(ZxSysJob job) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            Class aClass = Class.forName(job.getClassPath());
            JobDetail jobDetail = JobBuilder.newJob(aClass).build();
            // 触发器
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), Scheduler.DEFAULT_GROUP);
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron())).build();
            scheduler.scheduleJob(jobDetail, trigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
                log.info("---任务[" + triggerKey.getName() + "]启动成功-------");
                return true;
            } else {
                log.info("---任务[" + triggerKey.getName() + "]已经运行，请勿再次启动-------");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * 更新
     */
    @Override
    public boolean updateJob(ZxSysJob job) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        String createTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");

        TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), Scheduler.DEFAULT_GROUP);
        try {
            if (scheduler.checkExists(triggerKey)) {
                return false;
            }

            JobKey jobKey = JobKey.jobKey(job.getId(), Scheduler.DEFAULT_GROUP);

            CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(job.getCron())
                    .withMisfireHandlingInstructionDoNothing();
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
                    .withDescription(createTime).withSchedule(schedBuilder).build();
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            HashSet<Trigger> triggerSet = new HashSet<>();
            triggerSet.add(trigger);
            scheduler.scheduleJob(jobDetail, triggerSet, true);
            log.info("---任务[" + triggerKey.getName() + "]更新成功-------");
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除
     */

    @Override
    public boolean remove(ZxSysJob job) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), Scheduler.DEFAULT_GROUP);
        try {
            if (checkJob(job)) {
                scheduler.pauseTrigger(triggerKey);
                scheduler.unscheduleJob(triggerKey);
                scheduler.deleteJob(JobKey.jobKey(job.getId(), Scheduler.DEFAULT_GROUP));
                log.info("---任务[" + triggerKey.getName() + "]删除成功-------");
                return true;
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取列表
     *
     * @param quartzBean bean
     * @return kendo
     */
    @Override
    public KendoGrid getQuartzList(QuartzBean quartzBean) {
        KendoGrid kendoGrid = new KendoGrid();
        ZxSysJob zxSysJob = new ZxSysJob();
        BeanUtils.copyProperties(quartzBean,zxSysJob);
        zxSysJob.setSystemCode("MDT");
        PageHelper.startPage(zxSysJob.getPage(), zxSysJob.getPageSize(), false);
        List<ZxSysJob> list = zxSysJobMapper.selectAll(zxSysJob);
        PageInfo<ZxSysJob> page = new PageInfo<>(list);
        kendoGrid.setResults(list);
        kendoGrid.setCount((int) page.getTotal());
        return kendoGrid;
    }

    /**
     * 获取model
     *
     * @param id id
     * @return model
     */
    @Override
    public ZxSysJob selectQuartzById(String id) {
        return zxSysJobMapper.selectByPrimaryKey(id);
    }

    /**
     * 编辑
     *
     * @param quartzBean model
     * @return result
     */
    @Override
    public Result addAndUpdateQuartz(QuartzBean quartzBean) {
        Result result = new Result();
        ZxSysJob zxSysJob = new ZxSysJob();
        BeanUtils.copyProperties(quartzBean,zxSysJob);
        if (!"".equals(quartzBean.getId())) {
            zxSysJobMapper.updateById(zxSysJob);
            result.setSuccess(true);
            result.setMsg("修改成功");
            return result;
        }else{
            zxSysJob.setId(CommonUtil.getGUID());
            zxSysJobMapper.insert(zxSysJob);
            result.setSuccess(true);
            result.setMsg("添加成功");
            return result;
        }
    }

    @Override
    public Result deleteById(QuartzBean quartzBean) {
        Result result = new Result();
        zxSysJobMapper.deleteByPrimaryKey(quartzBean.getId());
        result.setMsg("删除成功");
        result.setSuccess(true);
        return result;
    }

    /**
     * 启动作业
     * @param quartzBean bean
     * @return result
     */
    @Override
    public Result updateStartJob(QuartzBean quartzBean) {

        Result result = new Result();
        ZxSysJob zxSysJob = zxSysJobMapper.selectByPrimaryKey(quartzBean.getId());
        startJob(zxSysJob);
        zxSysJob.setJobStatus(1);
        zxSysJobMapper.updateByPrimaryKey(zxSysJob);
        result.setSuccess(true);
        result.setMsg("启动成功");
        return result;
    }

    /**
     * 停止作业
     *
     * @param quartzBean bean
     * @return result
     */
    @Override
    public Result updateStopJob(QuartzBean quartzBean) {
        Result result = new Result();
        ZxSysJob zxSysJob = zxSysJobMapper.selectByPrimaryKey(quartzBean.getId());
        remove(zxSysJob);
        zxSysJob.setJobStatus(0);
        zxSysJobMapper.updateByPrimaryKey(zxSysJob);
        result.setSuccess(true);
        result.setMsg("停止成功");
        return result;
    }

    /**
     * 获取列表
     *
     * @param job model
     * @return list
     */
    @Override
    public List<ZxSysJob> selectListByPage(ZxSysJob job) {

        return zxSysJobMapper.selectListByPage(job);
    }
}
