package com.messaging.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MailService {

    @Autowired
    JavaMailSender mailSender;


    //method to send mail
    @Async
    public void mailMsg(String to,String from,String content) {
            try {
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setSubject("REGISTRATION OF CARD");
                simpleMailMessage.setText(content);
                simpleMailMessage.setTo(to);
                simpleMailMessage.setFrom(from);
                mailSender.send(simpleMailMessage);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }
}
