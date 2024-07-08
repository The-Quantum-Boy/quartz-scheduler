package com.quartz.sumit.examples.example4;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is just a simple job that receives parameters and
 * maintains state
 *
 * @author Sumit Dhanorkar
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ColorJob implements Job {

    private static Logger _log = LoggerFactory.getLogger(ColorJob.class);
    
    // parameter names specific to this job
    public static final String FAVORITE_COLOR = "favorite color";
    public static final String EXECUTION_COUNT = "count";
    
    // Since Quartz will re-instantiate a class every time it
    // gets executed, members non-static member variables can
    // not be used to maintain state!
    private int _counter = 1;

    /**
     * Empty constructor for job initialization
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     */
    public ColorJob() {
    }

    /**

     * @throws JobExecutionException
     * if there is an exception while executing the job.
     */
    public void execute(JobExecutionContext context)
        throws JobExecutionException {

        // This job simply prints out its job name and the
        // date and time that it is running
        JobKey jobKey = context.getJobDetail().getKey();

        System.out.println("Job key :: "+jobKey);
        
        // Grab and print passed parameters
        JobDataMap data = context.getJobDetail().getJobDataMap();
        String favoriteColor = data.getString(FAVORITE_COLOR);
        int count = data.getInt(EXECUTION_COUNT);
        _log.info("ColorJob: " + jobKey + " executing at " + new Date() + "\n" +
            "  favorite color is " + favoriteColor + "\n" + 
            "  execution count (from job map) is " + count + "\n" + 
            "  execution count (from job member variable) is " + _counter);
        
        // increment the count and store it back into the 
        // job map so that job state can be properly maintained
        count++;
        data.put(EXECUTION_COUNT, count);
        
        // Increment the local member variable 
        // This serves no real purpose since job state can not 
        // be maintained via member variables!
        _counter++;
    }

}
