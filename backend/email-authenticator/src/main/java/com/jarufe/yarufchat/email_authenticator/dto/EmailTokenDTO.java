package com.jarufe.yarufchat.email_authenticator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailTokenDTO {
    String email;
    String token;
}
