package com.clone.instagram.backend.business;

import com.clone.instagram.backend.exception.BaseException;
import com.clone.instagram.backend.exception.EmailException;


import com.clone.instagram.common.EmailRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class EmailBusiness {
    private final KafkaTemplate<String, EmailRequest> kafkaTemplate;

    public EmailBusiness(KafkaTemplate<String, EmailRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendActivateUserEmail(String email, String username, String token) throws BaseException {
        String html;
        try {
            html = readEmailTemplate("email-activate-user.html");
        } catch (Exception e) {
            throw EmailException.templateNotFound();
        }

        log.info("Token: " + token);

        String finalLink = "http://localhost:4200/activate/" + token;
        html = html.replace("${USERNAME}", username);
        html = html.replace("${ACTIVATE_LINK}", finalLink);

        EmailRequest request = new EmailRequest();
        request.setTo(email);
        request.setContent(html);
        request.setSubject("Please Activate Your Account");

        CompletableFuture<SendResult<String, EmailRequest>> future = kafkaTemplate.send("activation-email",request);
        future.whenComplete((result, ex)->{
            if (ex == null) {
                log.info("Kafka send success");
                log.info(result.getRecordMetadata().topic());
            } else {
                log.error("Kafka send fail");
                log.error(ex.getMessage());
            }
        });
    }

    public void sendResetPassword(String email, String username, String resetToken) throws BaseException{
        String html;
        try {
            html = readEmailTemplate("email-reset-password.html");
        } catch (Exception e) {
            throw EmailException.templateNotFound();
        }

        log.info("Reset Token: " + resetToken);

        String finalLink = "http://localhost:4200/reset-password/" + resetToken;
        html = html.replace("${USERNAME}", username);
        html = html.replace("${NEWPASSWORD_LINK}", finalLink);

        EmailRequest request = new EmailRequest();
        request.setTo(email);
        request.setContent(html);
        request.setSubject("Reset your password.");

        CompletableFuture<SendResult<String, EmailRequest>> future = kafkaTemplate.send("reset-password",request);
        future.whenComplete((result, ex)->{
            if (ex == null) {
                log.info("Kafka send success");
                log.info(result.getRecordMetadata().topic());
            } else {
                log.error("Kafka send fail");
                log.error(ex.getMessage());
            }
        });
    }

    private String readEmailTemplate(String filename) throws IOException {
        File file = ResourceUtils.getFile("classpath:email/" + filename);
        return FileCopyUtils.copyToString(new FileReader(file));

    }

}
