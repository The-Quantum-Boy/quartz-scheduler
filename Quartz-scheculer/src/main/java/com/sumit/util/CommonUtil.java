package com.sumit.util;

import com.sumit.model.TriggerDetail;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommonUtil {

    public JobDetail getJobDetail(Class className, TriggerDetail triggerDetail){

        JobDataMap jobDataMap=new JobDataMap();
        jobDataMap.put(className.getSimpleName(),triggerDetail);

        return JobBuilder.newJob(className)
                .withIdentity(className.getSimpleName(),"group1")//how job will be identified
                .setJobData(jobDataMap)
                .build();
    }

    public JobDetail getJobDetail(Class className){

        return JobBuilder.newJob(className)
                .withIdentity(className.getSimpleName(),"group1")//how job will be identified
                .build();
    }

    public Trigger getTriggerdDetail(Class className, TriggerDetail trigger){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInMilliseconds(trigger.getTimeInterval());

        if(trigger.isRunForever()){
            scheduleBuilder.repeatForever();
        }else{
            scheduleBuilder.withRepeatCount(trigger.getTriggerCount());
        }

        return TriggerBuilder
                .newTrigger()
                .startAt(new Date(System.currentTimeMillis()+trigger.getInitialOffset()))
                .withSchedule(scheduleBuilder)
                .build();
    }

    public TriggerDetail getTriggerDetailObj(int triggerCount, boolean runForever,Long repeatValue, Long initialOffset, String info){
        TriggerDetail triggerDetail= new TriggerDetail();

        triggerDetail.setTriggerCount(triggerCount);
        triggerDetail.setInitialOffset(initialOffset);
        triggerDetail.setInfo(info);
        triggerDetail.setTimeInterval(repeatValue);
        triggerDetail.setRunForever(runForever);

        return triggerDetail;

    }


    public Trigger getTriggerByCronExpression(Class className, String expression){
        return TriggerBuilder
                .newTrigger()
                .withIdentity(className.getSimpleName())
                .withSchedule(CronScheduleBuilder.cronSchedule(expression))
                .build();
    }
}

