package covid.client.httpclient.service;

import covid.client.enumeration.ComplainStatus;
import covid.client.enumeration.Role;
import covid.client.models.*;
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

    List<User> getAllUsers();

    List<User> getAllUsersByRole(Role role);

    ApiResponse<ComplaintResponses> reply(ComplaintResponses complaintResponses);

    void readResponse(Long id);

    ApiResponse<Complaints> updateComplaintStatus(Long complaintID, ComplainStatus complainStatus);

    List<ApiResponse<LiveChatAvailability>> createLiveChatAvailability(List<LiveChatAvailability> liveChatAvailability);

    ApiResponse<LiveChatAvailableTime> createTimeSlots(LiveChatAvailableTime liveChatAvailableTime);

    List<Chat> getMessagesForUser();

    Chat getMessagesByChatID(Long chatID);

    Chat getAllMessagesByToAndFrom(Long to, Long from);

}
