package com.messaging.Dto;

import lombok.Data;

//DTO for sending mail
@Data
public class Mail {
    String to;
    String from;
    String content;

}