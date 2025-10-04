package com.messaging.TwilioController;

import com.messaging.Dto.TwilioDto;
import com.messaging.TwilioService.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwilioController {

    @Autowired
    TwilioService service;

    //api to send transaction SMS
    @PostMapping("/sms")
    public void Sendsms(@RequestBody TwilioDto twilioDto){
        service.TransactionSms(twilioDto.getPhoneNo(),twilioDto.getContent());
    }

    //api to send refund SMS
    @PostMapping("/refund/sms")
    public void RefundSms(@RequestBody TwilioDto twilioDto){
        service.RefundSms(twilioDto.getPhoneNo(),twilioDto.getContent());
    }
}
