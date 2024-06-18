package com.jarufe.yarufchat.email_authenticator.service;

import com.jarufe.yarufchat.email_authenticator.dto.ResponseDTO;

public interface EmailTokenService {

    ResponseDTO verifyEmailToken(String email, String token);

    ResponseDTO sendEmailToken(String email);

}
