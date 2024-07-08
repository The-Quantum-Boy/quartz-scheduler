package com.sumit.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TriggerDetail {

    private int triggerCount;//how many time job schedular run
    // if triggerCount=5 then isRunForever will be false else it will run forever
    private boolean isRunForever;

    //intervel between jobs
    private Long timeInterval;

    //what time it need to wait when scheduler started
    private Long initialOffset;

    private String info;


}

