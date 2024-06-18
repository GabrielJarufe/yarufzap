package com.jarufe.yarufchat.email_authenticator.service.impl;

import com.jarufe.yarufchat.email_authenticator.config.exceptions.BusinessException;
import com.jarufe.yarufchat.email_authenticator.dto.ResponseDTO;
import com.jarufe.yarufchat.email_authenticator.service.EmailTokenService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class EmailTokenServiceImpl implements EmailTokenService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public ResponseDTO verifyEmailToken(String email, String token) {
        if(token.length() != 6 || token.matches("[a-zA-Z]+")){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Invalid Token");
        }

        Optional<String> optToken = Optional.ofNullable(redisTemplate.opsForValue().get(email));
        boolean verified = optToken.filter(storedToken -> storedToken.equals(token)).isPresent();
        if(verified){
            return ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("Verification Code OK")
                    .build();
        }else{
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Invalid Token");
        }
    }

    @Override
    public ResponseDTO sendEmailToken(String email) {
        if(ObjectUtils.isEmpty(email)){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Email is empty");
        }
        try{
            Random random =  new Random();
            int code = 100000 + random.nextInt(900000); // limits to 999999

            redisTemplate.opsForValue().set(email,String.valueOf(code));
            redisTemplate.expire(email, 5L, TimeUnit.MINUTES);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            String htmlBody = this.buildHTMLTemplate(String.valueOf(code));
            helper.setTo(email);
            helper.setSubject("Email Verification Code");
            helper.setText(htmlBody, true);

            mailSender.send(message);
            return ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("Verification Code send to email with success")
                    .build();
        }catch (Exception e){
            e.getStackTrace();
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,"Error sending the email: " + e.getMessage());
        }
    }

    private String buildHTMLTemplate(String token) {
        Context context = new Context();
        context.setVariable("token", token);
        return templateEngine.process("email-token", context);
    }

}
