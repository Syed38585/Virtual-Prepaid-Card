package com.vpc.Feign;

import com.vpc.Dto.MailDto;
import com.vpc.Dto.TwilioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "MESSAGING-SERVICE", url = "http://localhost:8081")
public interface MsgFeign {

    @PostMapping("/sms")
     void TransactionSms(@RequestBody TwilioDto twilioDto);


    @PostMapping("/refund/sms")
     void RefundSms(@RequestBody TwilioDto twilioDto);


    @PostMapping("/sendMail")
    void sendEmail(@RequestBody MailDto mailDto);
}
