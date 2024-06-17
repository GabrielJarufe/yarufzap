package com.jarufe.yarufchat.email_authenticator.config.exceptions;

import com.jarufe.yarufchat.email_authenticator.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BusinessExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ResponseDTO> handleBusinessException(BusinessException errorException ){
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(errorException.getStatus())
                .message(errorException.getMessage())
                .build();
        return new ResponseEntity<>(responseDTO,errorException.getStatus());
    }

}
