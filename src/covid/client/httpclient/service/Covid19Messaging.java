package covid.client.httpclient.service;

import covid.client.enumeration.ComplainStatus;
import covid.client.models.ApiResponse;
import covid.client.models.AuthResponse;
import covid.client.models.Complaints;
import covid.client.models.Services;
import covid.client.models.request.LoginRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface Covid19Messaging {

    AuthResponse authenticateUser(LoginRequest loginRequest);

    ResponseEntity<String> dummyEmployeeApiTest();

    List<Services> getAllServices();

    ApiResponse<Complaints> createUserComplaint(Complaints complaints);

    ApiResponse<Complaints> deleteUserComplaint(Long userID);

    List<Complaints> getComplaintsByStudentID(Long studentID);

    List<Complaints> getComplaintsByStatusAndStudentID(Long studentID, ComplainStatus complainStatus);

    List<Complaints> getComplaintsByStatus(ComplainStatus complainStatus);




}
