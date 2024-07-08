package com.sumit.web;

import com.sumit.payload.EmailRequest;
import com.sumit.payload.EmailResponse;
import com.sumit.quartz.job.EmailJob;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RestController
public class EmailSchedulerController {


    @Autowired
    private Scheduler scheduler;

    @GetMapping("/get")
    public ResponseEntity<String> getApiTest(){
        return ResponseEntity.ok("get api test ");
    }


    @PostMapping(value = "/schedule/email",produces = "application/json")
    public ResponseEntity<EmailResponse> scheduleMail(@Valid @RequestBody EmailRequest emailRequest){
        try {
            ZonedDateTime dateTime=ZonedDateTime.of(emailRequest.getDateTime(),emailRequest.getTimeZone());
            if(dateTime.isBefore(ZonedDateTime.now())){
                EmailResponse emailResponse=new EmailResponse(false,"Datetime must be after current time");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(emailResponse);
            }
            //schedule job
            JobDetail jobDetail=buildJobDetail(emailRequest);
            Trigger trigger=buildTrigger(jobDetail,dateTime);
            scheduler.scheduleJob(jobDetail,trigger);

            EmailResponse emailResponse=new EmailResponse(
                    true,
                    jobDetail.getKey().getName(),
                    jobDetail.getKey().getGroup(),
                    "Email scheduled successfully"
            );
            log.info("EMAIL : ",jobDetail.getDescription());
            return ResponseEntity.ok(emailResponse);

        }catch (SchedulerException exception){
            log.error("Error while scheduling email: ",exception);
            EmailResponse emailResponse=new EmailResponse(false,"Error while scheduling email. please try again");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(emailResponse);
        }
    }

    private JobDetail buildJobDetail(EmailRequest scheduleEmailRequest){
        JobDataMap jobDataMap=new JobDataMap();
        jobDataMap.put("email",scheduleEmailRequest.getEmail());
        jobDataMap.put("subject",scheduleEmailRequest.getSubjects());
        jobDataMap.put("body",scheduleEmailRequest.getBody());

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(),"email-jobs")
                .withDescription("send email job")
                .usingJobData(jobDataMap)
                .storeDurably() //store job without trigger
                .build();
    }


    private Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime startAt){
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(),"email-trigger")
                .withDescription("send email trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(
                        SimpleScheduleBuilder
                                .simpleSchedule()
                                .withMisfireHandlingInstructionFireNow()
                )
                .build();
    }



}
