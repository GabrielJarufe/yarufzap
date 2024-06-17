package com.jarufe.yarufchat.email_authenticator.config.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException{
    private HttpStatus status;
    private String message;
    public BusinessException(HttpStatus status,String message){
        super();
        this.message = message;
        this.status = status;
    }

}
