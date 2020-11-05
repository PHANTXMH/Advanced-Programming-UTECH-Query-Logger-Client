package covid.client.httpclient.service;

import covid.client.models.AuthResponse;
import covid.client.models.Services;
import covid.client.models.request.LoginRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface Covid19Messaging {

    AuthResponse authenticateUser(LoginRequest loginRequest);

    ResponseEntity<String> dummyEmployeeApiTest();

    List<Services> getAllServices();


}
