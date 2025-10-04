package com.messaging.Dto;


import lombok.Data;


//DTO for SMS
@Data
public class TwilioDto {
    String phoneNo;
    String content;

}
