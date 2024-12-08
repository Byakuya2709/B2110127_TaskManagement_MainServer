/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author admin
 */
@Service
public class RecaptchaService {
  private static final Logger logger = LoggerFactory.getLogger(RecaptchaService.class);
    
    private String secretKey="6LeM92gqAAAAAMUGRSCcUSJowbyrP2KkaIeWPmx1";

    private final RestTemplate restTemplate;

    @Autowired
    public RecaptchaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean verify(String token) {
        String url = "https://www.google.com/recaptcha/api/siteverify?secret=" + secretKey + "&response=" + token;
        RecaptchaResponse response = restTemplate.postForObject(url, null, RecaptchaResponse.class);
        if (response != null) {
            logger.info("Recaptcha Response: success={}, challenge_ts={}, hostname={}, score={}",
                    response.isSuccess(), response.challenge_ts, response.hostname, response.score);
        } else {
            logger.warn("Recaptcha response is null.");
        }
        return response != null && response.isSuccess();
    }

    private static class RecaptchaResponse {
        private boolean success;
        private String challenge_ts;
        private String hostname;
        private float score;

        // Getters and setters

        public boolean isSuccess() {
            return success;
        }
    }
}