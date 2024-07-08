package com.sumit.service;


import com.sumit.job.FirstJob;
import com.sumit.model.TriggerDetail;
import com.sumit.scheduler.MainScheduler;
import com.sumit.util.CommonUtil;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FirstJobRun {


    private final MainScheduler scheduler;

    private final CommonUtil util;


    @PostConstruct
    public void init(){

        TriggerDetail triggerDetail=util
                .getTriggerDetailObj(5, false, 1000L,1000L,"info");

        scheduler.scheduleJob(FirstJob.class,triggerDetail);
    }
}
