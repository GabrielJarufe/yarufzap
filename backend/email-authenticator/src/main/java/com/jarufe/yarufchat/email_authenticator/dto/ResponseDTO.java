package com.jarufe.yarufchat.email_authenticator.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ResponseDTO {
    public HttpStatus status;
    public String message;
}
