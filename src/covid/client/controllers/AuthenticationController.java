package covid.client.controllers;

import covid.client.configuration.ApplicationConfiguration;
import covid.client.logging.LoggingManager;
import covid.client.models.AuthResponse;
import covid.client.models.request.LoginRequest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import static covid.client.models.Constants.AUTHENTICATION_SERVICE_URL_PATH;
import static covid.client.models.Constants.SERVICE_BASE_URL;

public class AuthenticationController {

    public AuthResponse authenticateUser(final LoginRequest loginRequest){

        AuthResponse authResponse = null;

        final String authPath = AUTHENTICATION_SERVICE_URL_PATH;

        RestTemplate restTemplate = new RestTemplate(); // library used to call the API

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("username", loginRequest.getUsername());

        hashMap.put("password", loginRequest.getPassword());

        HttpEntity<Object> request =
                new HttpEntity<Object>(loginRequest, headers);  // request // headers

        LoggingManager.getLogger(this).info("Executing the request");

        ResponseEntity<AuthResponse> responseEntity =
                restTemplate.postForEntity(SERVICE_BASE_URL.concat(authPath), request, AuthResponse.class);

        responseEntity.getStatusCode(); // 200, 401, 500 etc

        LoggingManager.getLogger(this).info("Finished executing the request");

        LoggingManager.getLogger(this).info(String.format("Response code: %s", responseEntity.getStatusCode()));

        if(responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            authResponse = responseEntity.getBody();
        }
        return authResponse;
    }

}
