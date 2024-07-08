package com.sumit.service;

import com.sumit.job.SecondJob;
import com.sumit.model.TriggerDetail;
import com.sumit.scheduler.MainScheduler;
import com.sumit.util.CommonUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CronJob {

    @Autowired
    private MainScheduler scheduler;

    @Autowired
    private CommonUtil util;

    @PostConstruct
    public void init(){
        //run in every 2 second
        scheduler.scheduleJob(SecondJob.class,"0/2****?");
    }
}

