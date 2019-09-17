package com.kayisoft.controller;


import com.kayisoft.model.account.QuartzBean;
import com.kayisoft.model.account.ZxSysJob;
import com.kayisoft.service.JobTaskService;
import com.kayisoft.vo.KendoGrid;
import com.kayisoft.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: ruopeng.cheng
 * @date: 2018/9/18 9:53
 */
@Controller
@RequestMapping(value = "/quartz")
@Slf4j
public class QuartzController{

    @Autowired
    JobTaskService jobTaskService;

    @RequestMapping(value = "/init",method = RequestMethod.GET)
    public String init(){

        return "manager/quartzManager";
    }

    @RequestMapping(value = "/getQuartzList", method = RequestMethod.POST)
    @ResponseBody
    public KendoGrid getQuartzList(QuartzBean quartzBean){
        return jobTaskService.getQuartzList(quartzBean);
    }
    @RequestMapping(value = "/quartzManagerEdit/{id}",method = RequestMethod.GET)
    public String quartzManagerEdit(@PathVariable("id") String id, HttpServletRequest request){

        ZxSysJob zxSysJob = new ZxSysJob();
        String zero = "0";
        if (!StringUtils.isEmpty(id) && !zero.equals(id)) {
            zxSysJob = jobTaskService.selectQuartzById(id);
        }
        request.setAttribute("zxSysJob",zxSysJob);
        return "manager/quartzManagerEdit";
    }

    @RequestMapping(value = "/addAndUpdateQuartz",method = RequestMethod.POST)
    @ResponseBody
    public Result addAndUpdateQuartz(@RequestBody QuartzBean quartzBean){

        return jobTaskService.addAndUpdateQuartz(quartzBean);
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteById(@RequestBody QuartzBean quartzBean) {
        return jobTaskService.deleteById(quartzBean);
    }

    @RequestMapping(value = "/startJob", method = RequestMethod.POST)
    @ResponseBody
    public Result startJob(@RequestBody QuartzBean quartzBean){

        return jobTaskService.updateStartJob(quartzBean);
    }

    @RequestMapping(value = "/stopJob", method = RequestMethod.POST)
    @ResponseBody
    public Result stopJob(@RequestBody QuartzBean quartzBean){


        return jobTaskService.updateStopJob(quartzBean);
    }


    @PostConstruct
    public void run() {
        try {

            ZxSysJob job = new ZxSysJob();
            job.setJobStatus(1);
            job.setSystemCode("MDT");
            List<ZxSysJob> jobList = jobTaskService.selectListByPage(job);
            //开启任务
            jobList.forEach(jobs -> {
                        log.info("---任务[" + jobs.getId() + "]系统 init--开始启动---------");
                        if(!jobTaskService.checkJob(jobs)){
                            jobTaskService.startJob(jobs);
                        }
                    }
            );
            if (jobList.size() == 0) {
                log.info("---数据库暂无启动的任务---------");
            } else{
                System.out.println("---任务启动完毕---------");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}


