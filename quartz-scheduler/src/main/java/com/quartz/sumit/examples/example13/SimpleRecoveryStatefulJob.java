package com.quartz.sumit.examples.example13;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.PersistJobDataAfterExecution;

/**
 * This job has the same functionality of SimpleRecoveryJob except that this job implements is 'stateful', in that it
 * will have it's data (JobDataMap) automatically re-persisted after each execution, and only one instance of the
 * JobDetail can be executed at a time.
 * 
 * @author Sumit Dhanorkar
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SimpleRecoveryStatefulJob extends SimpleRecoveryJob {

  public SimpleRecoveryStatefulJob() {
    super();
  }
}
