package com.messaging.TwilioService;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;



@EnableAsync
@Service
public class TwilioService {

    @Value("${twilio.account_sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth_token}")
    private String AUTH_TOKEN;

    @Value("${twilio.phone_no}")
    private String no;

    @PostConstruct
    public void Setup() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }


    //method to send SMS(transaction)
    @Async
    public void TransactionSms(String phoneNo, String content) {
        System.out.println("PREPARING");
            try {
                Message message = Message.creator(
                                new PhoneNumber(phoneNo),
                                new PhoneNumber(no),
                                content)
                        .create();
                System.out.println("Twilio Message SID: " + message.getSid());
            } catch (Exception e) {
                System.err.println("Twilio SMS failed: " + e.getMessage());
                e.printStackTrace();
            }
    }


    //method to send SMS(refund)
    @Async
    public void RefundSms(String phoneNo, String content) {
        System.out.println("PREPARING");
            Message message = Message.creator(
                            new PhoneNumber(phoneNo),
                            new PhoneNumber(no),
                            content)
                    .create();
            System.out.println("DONE");
    }
}
