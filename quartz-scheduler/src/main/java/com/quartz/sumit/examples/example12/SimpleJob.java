package com.quartz.sumit.examples.example12;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * A dumb implementation of Job, for unittesting purposes.
 *
 * @author Sumit Dhanorkar
 */
public class SimpleJob implements Job {

    public static final String MESSAGE = "msg";

    private static Logger _log = LoggerFactory.getLogger(SimpleJob.class);

    public SimpleJob() {
    }

    /**
     * @throws JobExecutionException  if there is an exception while executing the job.
     */
    public void execute(JobExecutionContext context)
        throws JobExecutionException {

        // This job simply prints out its job name and the
        // date and time that it is running
        JobKey jobKey = context.getJobDetail().getKey();

        String message = (String) context.getJobDetail().getJobDataMap().get(MESSAGE);

        _log.info("SimpleJob: " + jobKey + " executing at " + new Date());
        _log.info("SimpleJob: msg: " + message);
    }

    

}
