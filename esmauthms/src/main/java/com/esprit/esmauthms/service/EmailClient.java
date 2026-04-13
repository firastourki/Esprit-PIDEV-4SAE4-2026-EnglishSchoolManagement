// src/main/java/com/esprit/esmauthms/service/EmailClient.java
package com.esprit.esmauthms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String EMAIL_SERVICE_URL = "http://localhost:2001/api/emails/send";

    public void sendEmail(String to, String subject, String text) {
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("to", to);
            body.put("subject", subject);
            body.put("text", text);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    EMAIL_SERVICE_URL,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            log.info("Email service response: status={}, body={}",
                    response.getStatusCode(), response.getBody());
        } catch (Exception e) {
            log.error("Failed to send email via email microservice", e);
        }
    }
}
