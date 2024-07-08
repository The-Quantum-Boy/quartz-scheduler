package com.quartz.sumit.examples.example12;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  This example demonstrates how Quartz can be used in a client/server
 *  environment to remotely scheudle jobs on a remote server using
 *  RMI (Remote Method Invocation).
 *
 *  This example will run a server that will execute the schedule.  The
 *  server itself will not schedule any jobs.   This example will also
 *  execute a client that will connect to the server (via RMI) to
 *  schedule the job.  Once the job is remotely scheduled, the sceduler on
 *  the server will run the job (at the correct time).
 *  Note:  This example works best when you run the client and server on
 *  different computers.  However, you can certainly run the server and
 *  the client on the same box!
 *
 *  Port # used for RMI connection can be modified in the example's
 *  property files
 *
 * @author Sumit dhanorkar
 */
public class RemoteServerExample {

  /**
   * This example will spawn a large number of jobs to run
   * 
   * @author Sumit dhanorkar
   */
  public void run() throws Exception {
    Logger log = LoggerFactory.getLogger(RemoteServerExample.class);

    // First we must get a reference to a scheduler
    SchedulerFactory sf = new StdSchedulerFactory();
    Scheduler sched = sf.getScheduler();

    log.info("------- Initialization Complete -----------");

    log.info("------- (Not Scheduling any Jobs - relying on a remote client to schedule jobs --");

    log.info("------- Starting Scheduler ----------------");

    // start the schedule
    sched.start();

    log.info("------- Started Scheduler -----------------");

    log.info("------- Waiting ten minutes... ------------");

    // wait five minutes to give our jobs a chance to run
    try {
//      Thread.sleep(600L * 1000L);
      Thread.sleep(100L * 1000L);
    } catch (Exception e) {
      //
    }

    // shut down the scheduler
    log.info("------- Shutting Down ---------------------");
    sched.shutdown(true);
    log.info("------- Shutdown Complete -----------------");

    SchedulerMetaData metaData = sched.getMetaData();
    log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
  }

  public static void main(String[] args) throws Exception {

    RemoteServerExample example = new RemoteServerExample();
    example.run();
  }

}
