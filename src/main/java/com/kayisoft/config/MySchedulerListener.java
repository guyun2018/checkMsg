package com.kayisoft.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author Administrator
 */
@Configuration
public class MySchedulerListener {
      
    @Autowired
    MyJobFactory myJobFactory;

      
    @Bean(name ="schedulerFactoryBean")
    public SchedulerFactoryBean schedulerFactory() {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setJobFactory(myJobFactory);
        return bean;  
    }  
  
}  