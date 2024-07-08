package com.quartz.sumit.examples.example12;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This example is a client program that will remotely 
 * talk to the scheduler to schedule a job.   In this 
 * example, we will need to use the JDBC Job Store.  The 
 * client will connect to the JDBC Job Store remotely to 
 * schedule the job.
 *
 * This example demonstrates how Quartz can be used in a client/server
 * environment to remotely scheudle jobs on a remote server using
 * RMI (Remote Method Invocation).
 *
 * This example will run a server that will execute the schedule.  The
 * server itself will not schedule any jobs.   This example will also
 * execute a client that will connect to the server (via RMI) to
 * schedule the job.  Once the job is remotely scheduled, the sceduler on
 * the server will run the job (at the correct time).
 *
 * Note:  This example works best when you run the client and server on
 * different computers.  However, you can certainly run the server and
 * the client on the same box!
 *
 * Port # used for RMI connection can be modified in the example's
 * property files
 *
 * @author Sumit Dhanorkar
 */
public class RemoteClientExample {

    public void run() throws Exception {

        Logger log = LoggerFactory.getLogger(RemoteClientExample.class);

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        // define the job and ask it to run
        JobDetail job = newJob(SimpleJob.class)
            .withIdentity("remotelyAddedJob", "default")
            .build();
        
        JobDataMap map = job.getJobDataMap();
        map.put("msg", "Your remotely added job has executed!");
        
        Trigger trigger = newTrigger()
            .withIdentity("remotelyAddedTrigger", "default")
            .forJob(job.getKey())
            .withSchedule(cronSchedule("0/5 * * * * ?"))
            .build();

        // schedule the job
        sched.scheduleJob(job, trigger);

        log.info("Remote job scheduled.");
    }

    public static void main(String[] args) throws Exception {

        RemoteClientExample example = new RemoteClientExample();
        example.run();
    }

}
