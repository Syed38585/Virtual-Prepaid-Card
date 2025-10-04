package com.messaging.MailController;

import com.messaging.Dto.Mail;
import com.messaging.MailService.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MailController {

    @Autowired
    MailService service;

    //api for sending mail
    @PostMapping("/sendMail")
    public void sendEmail(@RequestBody Mail mail){
        service.mailMsg(mail.getTo(),mail.getFrom(),mail.getContent());
    }
}
