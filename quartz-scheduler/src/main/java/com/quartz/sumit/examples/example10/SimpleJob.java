package com.quartz.sumit.examples.example10;

import java.util.Date;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is just a simple job that gets fired off many times by example 1
 *
 * @author Sumit dhanorkar
 */
public class SimpleJob implements Job {

    private static Logger _log = LoggerFactory.getLogger(SimpleJob.class);

    /**
     * Empty constructor for job initilization
     */
    public SimpleJob() {
    }

    /**
     *
     * @throws JobExecutionException   if there is an exception while executing the job.
     */
    @SuppressWarnings("unchecked")
    public void execute(JobExecutionContext context)
        throws JobExecutionException {

        // This job simply prints out its job name and the
        // date and time that it is running
        JobKey jobKey = context.getJobDetail().getKey();
        _log.info("Executing job: " + jobKey + " executing at " + new Date() + ", fired by: " + context.getTrigger().getKey());
        
        if(context.getMergedJobDataMap().size() > 0) {
            Set<String> keys = context.getMergedJobDataMap().keySet();
            for(String key: keys) {
                String val = context.getMergedJobDataMap().getString(key);
                _log.info(" - jobDataMap entry: " + key + " = " + val);
            }
        }
        
        context.setResult("hello");
    }

}
