package com.sumit.quartz.job;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class EmailJob extends QuartzJobBean {


    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    //invoked when quartz job is scheduled
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //here we use JobDataMap

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

        String subject=jobDataMap.getString("subject");
        String body=jobDataMap.getString("body");
        String recipientEmail=jobDataMap.getString("email");

        sendMail(mailProperties.getUsername(),recipientEmail,subject,body);

    }

    private void sendMail(String fromEmail, String toEmail,String subject,String body){
        try {
            MimeMessage message=javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper=new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
            messageHelper.setSubject(subject);
            messageHelper.setText(body,true);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(toEmail);

            javaMailSender.send(message);

        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
