package com.quartz.sumit.examples.example9;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is just a simple job that gets fired off many times by example 1
 * @author Sumit Dhanorkar
 */
public class SimpleJob1 implements Job {

    private static Logger _log = LoggerFactory.getLogger(SimpleJob1.class);

    /**
     * Empty constructor for job initilization
     */
    public SimpleJob1() {
    }

    /**
     * @throws JobExecutionException if there is an exception while executing the job.
     */
    public void execute(JobExecutionContext context)
        throws JobExecutionException {

        // This job simply prints out its job name and the
        // date and time that it is running
        JobKey jobKey = context.getJobDetail().getKey();
        _log.info("SimpleJob1 says: " + jobKey + " executing at " + new Date());
    }

}
