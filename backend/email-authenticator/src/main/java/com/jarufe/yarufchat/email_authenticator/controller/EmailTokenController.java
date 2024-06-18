package com.jarufe.yarufchat.email_authenticator.controller;

import com.jarufe.yarufchat.email_authenticator.dto.EmailDTO;
import com.jarufe.yarufchat.email_authenticator.dto.EmailTokenDTO;
import com.jarufe.yarufchat.email_authenticator.dto.ResponseDTO;
import com.jarufe.yarufchat.email_authenticator.service.EmailTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
public class EmailTokenController {
    @Autowired
    EmailTokenService emailTokenService;

    @PostMapping("/send")
    public ResponseEntity<ResponseDTO> sendEmailToken(@RequestBody(required = false) EmailDTO body){
        return ResponseEntity.ok(emailTokenService.sendEmailToken(body.getEmail()));
    }

    @GetMapping("/validate")
    public ResponseEntity<ResponseDTO> verifyEmail(@RequestBody(required = false) EmailTokenDTO body){
        return ResponseEntity.ok(emailTokenService.verifyEmailToken(body.getEmail(), body.getToken()));
    }
}
