package com.sumit.scheduler;

import com.sumit.model.TriggerDetail;
import com.sumit.util.CommonUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainScheduler  {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private CommonUtil util;


    @PostConstruct
    public void startScheduler(){
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }


    public void scheduleJob(Class className, TriggerDetail trigger){
        JobDetail jobDetail= util.getJobDetail(className,trigger);
        Trigger triggerDetail=util.getTriggerdDetail(className,trigger);

        try {
            scheduler.scheduleJob(jobDetail,triggerDetail);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void scheduleJob(Class className,  String cronExpression){
        JobDetail jobDetail= util.getJobDetail(className);

        Trigger triggerDetail=util.getTriggerByCronExpression(className,cronExpression);

        try {
            scheduler.scheduleJob(jobDetail,triggerDetail);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void closeScheduler(){
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }


}



