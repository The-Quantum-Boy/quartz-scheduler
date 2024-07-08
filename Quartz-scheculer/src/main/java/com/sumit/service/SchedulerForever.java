package com.sumit.service;

import com.sumit.job.SecondJob;
import com.sumit.model.TriggerDetail;
import com.sumit.scheduler.MainScheduler;
import com.sumit.util.CommonUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class SchedulerForever {

    @Autowired
    private  MainScheduler scheduler;

    @Autowired
    private   CommonUtil util;


    @PostConstruct
    public void init(){
        TriggerDetail triggerDetail=util.getTriggerDetailObj(1,true, 1000L, 1000L,"hello");
        scheduler.scheduleJob(SecondJob.class,triggerDetail);
    }
}
