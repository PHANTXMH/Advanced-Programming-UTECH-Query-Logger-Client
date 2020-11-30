package covid.client.httpclient.service;

import covid.client.enumeration.ComplainStatus;
import covid.client.enumeration.Role;
import covid.client.httpclient.bulider.ServerApiBuilder;
import covid.client.models.*;
import covid.client.models.request.LoginRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static covid.client.models.Constants.*;

public class Covid19Client extends Covid19WebServiceClient implements Covid19Messaging {

    private String serviceURl;

    private AuthResponse authResponse;

    private ServerApiBuilder serverApiBuilder;

    private RestTemplate restTemplate = new RestTemplate();

    private HttpHeaders httpHeaders;

    public String getServiceURl() {
        return serviceURl;
    }

    public AuthResponse getAuthResponse() {
        return authResponse;
    }


    public ServerApiBuilder getServerApiBuilder() {
        return serverApiBuilder;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }


    public Covid19Client(){

    }

    protected Covid19Client(String serviceURl, AuthResponse authResponse) {
        super();
        this.authResponse = authResponse;
        this.serviceURl = serviceURl;
    }

    protected Covid19Client build(String serviceURl, AuthResponse authResponse) throws Exception {
        this.serviceURl = serviceURl;
        this.authResponse = authResponse;
        setUpClient(this);
        this.setUpDefaultAuthHeader();
        return this;
    }

    private void setUpDefaultAuthHeader() {
        Assert.notNull(authResponse, "JWT Token is null");
        this.httpHeaders = new HttpHeaders();
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        this.httpHeaders.set("Authorization", "Bearer " + authResponse.getToken());
    }

    @Override
    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public ResponseEntity<String> dummyEmployeeApiTest() {
        return (ResponseEntity<String>) invoke(serviceURl, null, String.class, httpHeaders, HttpMethod.GET);
    }

    @Override
    public List<Services> getAllServices() {
        List<Services> servicesList = new ArrayList<>();
        ResponseEntity<List<Services>> services =  (ResponseEntity<List<Services>>) invoke(serviceURl.concat(Constants.GET_ALL_SERVICES_URL), null, new ParameterizedTypeReference<List<Services>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        }, httpHeaders, HttpMethod.GET);
        if (services != null && services.getStatusCode() == HttpStatus.OK){
            servicesList = services.getBody();
        }
        return servicesList;
    }

    @Override
    public ApiResponse<Complaints> createUserComplaint(Complaints complaints) {
        ResponseEntity<ApiResponse<Complaints>> response =
                invoke(serviceURl.concat(CREATE_COMPLAINTS_SERVICES_URL), complaints, ApiResponse.class, httpHeaders, HttpMethod.POST);
        return response.getBody();
    }

    @Override
    public ApiResponse<Complaints> deleteUserComplaint(Long userID) {
        ResponseEntity<ApiResponse<Complaints>> response =
                invoke(serviceURl.concat(DELETE_COMPLAINT_BY_ID_SERVICES_URL), null, ApiResponse.class, httpHeaders, HttpMethod.DELETE);
        return response.getBody();
    }

    @Override
    public List<Complaints> getComplaintsByStudentID(Long studentID) {
        ResponseEntity<List<Complaints>> response = (ResponseEntity<List<Complaints>>)  invoke((serviceURl.concat(LIST_COMPLAINTS_BY_STUDENT_ID_SERVICES_URL)
                .replaceAll("student_id", String.valueOf(studentID))), null, new ParameterizedTypeReference<List<Complaints>>() {
                            @Override
                            public Type getType() {
                                return super.getType();
                            }
                        }, httpHeaders, HttpMethod.GET);
        return response.getBody();
    }

    @Override
    public List<Complaints> getComplaintsByStatusAndStudentID(Long studentID, ComplainStatus complainStatus) {
        ResponseEntity<List<Complaints>> response = (ResponseEntity<List<Complaints>>)  invoke((serviceURl.concat(LIST_COMPLAINTS_BY_STUDENT_ID_AND_STATUS_SERVICES_URL)
                .replaceAll("student_id", String.valueOf(studentID))
                .replaceAll("status_", complainStatus.name())), null, new ParameterizedTypeReference<List<Complaints>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        }, httpHeaders, HttpMethod.GET);
        return response.getBody();
    }

    @Override
    public List<Complaints> getComplaintsByStatus(ComplainStatus complainStatus) {
        ResponseEntity<List<Complaints>> response = (ResponseEntity<List<Complaints>>)  invoke((serviceURl.concat(LIST_COMPLAINTS_BY_STATUS_SERVICES_URL)
                .replaceAll("status_", complainStatus.name())), null, new ParameterizedTypeReference<List<Complaints>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        }, httpHeaders, HttpMethod.GET);
        return response.getBody();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        ResponseEntity<List<User>> users =  (ResponseEntity<List<User>>) invoke(serviceURl.concat(ALL_USERS), null, new ParameterizedTypeReference<List<User>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        }, httpHeaders, HttpMethod.GET);
        if (users != null && users.getStatusCode() == HttpStatus.OK){
            userList = users.getBody();
        }
        return userList;
    }

    @Override
    public List<User> getAllUsersByRole(Role role) {
        List<User> userList = new ArrayList<>();
        ResponseEntity<List<User>> users =  (ResponseEntity<List<User>>) invoke(serviceURl.concat(ALL_USERS_BY_ROLE).replace("role_", role.name()), null, new ParameterizedTypeReference<List<User>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        }, httpHeaders, HttpMethod.GET);
        if (users != null && users.getStatusCode() == HttpStatus.OK){
            userList = users.getBody();
        }
        return userList;
    }

    @Override
    public ApiResponse<ComplaintResponses> reply(ComplaintResponses complaintResponses) {
        ResponseEntity<ApiResponse<ComplaintResponses>> response =
                invoke(serviceURl.concat(REPLY_TO_COMPLAINT_SERVICE_URL), complaintResponses, ApiResponse.class, httpHeaders, HttpMethod.POST);
        return response.getBody();
    }

    @Async
    @Override
    public void readResponse(Long id) {
                invoke(serviceURl.concat(READ_RESPONSE_SERVICE_URL.replace("response_id", String.valueOf(id))), null, ApiResponse.class, httpHeaders, HttpMethod.GET);
    }

    @Override
    public ApiResponse<Complaints> updateComplaintStatus(Long complaintID, ComplainStatus complainStatus) {
        ResponseEntity<ApiResponse<Complaints>> response = invoke(serviceURl.concat(UPDATE_COMPLAINT_STATUS)
                .replaceAll("id_", String.valueOf(complaintID))
                .replaceAll("status_", complainStatus.name()), null, ApiResponse.class, httpHeaders, HttpMethod.PUT);
        return response.getBody();
    }

    @Override
    public ApiResponse<LiveChatAvailability> createLiveChatAvailability(LiveChatAvailability liveChatAvailability) {
        ResponseEntity<ApiResponse<LiveChatAvailability>> responseEntity =  (ResponseEntity<ApiResponse<LiveChatAvailability>>) invoke(serviceURl.concat(CREATE_LIVE_CHAT_AVAILABILITY), liveChatAvailability, new ParameterizedTypeReference<ApiResponse<LiveChatAvailability>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        }, httpHeaders, HttpMethod.POST);
        if(responseEntity.getStatusCode() == HttpStatus.OK)
            return responseEntity.getBody();
        return null;
    }


    @Override
    public List<Chat> getMessagesForUser() {
        List<Chat> chatList = new ArrayList<Chat>();
        ResponseEntity<List<Chat>> chats =  (ResponseEntity<List<Chat>>) invoke(serviceURl.concat(Constants.GET_ALL_CHATS), null, new ParameterizedTypeReference<List<Chat>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        }, httpHeaders, HttpMethod.GET);
        if (chats != null && chats.getStatusCode() == HttpStatus.OK){
            chatList = chats.getBody();
        }
        return chatList;
    }

    @Override
    public Chat getMessagesByChatID(Long chatID) {
        ResponseEntity<Chat> chat =
                invoke(serviceURl.concat(GET_CHAT_BY_ID).replace("id_", String.valueOf(chatID)), null, Chat.class, httpHeaders, HttpMethod.GET);
        if (chat != null && chat.getStatusCode() == HttpStatus.OK){
            return chat.getBody();
        }
        return new Chat(); // chat was null or server error
    }

    @Override
    public Chat getAllMessagesByToAndFrom(Long to, Long from) {
        ResponseEntity<Chat> chat =
                invoke(serviceURl.concat(GET_CHAT_MESSAGES_BY_FROM_AND_TO_USER)
                        .replaceAll("to_", String.valueOf(to))
                        .replaceAll("from_", String.valueOf(from)), null, Chat.class, httpHeaders, HttpMethod.GET);
        if (chat != null && chat.getStatusCode() == HttpStatus.OK){
            return chat.getBody();
        }
        return new Chat();
    }

}

