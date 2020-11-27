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
    public List<ApiResponse<LiveChatAvailability>> createLiveChatAvailability(List<LiveChatAvailability> liveChatAvailabilities) {
        ResponseEntity<List<ApiResponse<LiveChatAvailability>>> responseList =  (ResponseEntity<List<ApiResponse<LiveChatAvailability>>>) invoke(serviceURl.concat(CREATE_LIVE_CHAT_AVAILABILITY), liveChatAvailabilities, new ParameterizedTypeReference<List<ApiResponse<LiveChatAvailability>>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        }, httpHeaders, HttpMethod.POST);
        if(responseList.getStatusCode() == HttpStatus.OK)
            return responseList.getBody();
        return null;
    }


    @Override
    public ApiResponse<LiveChatAvailableTime> createTimeSlots(LiveChatAvailableTime liveChatAvailableTime) {
        ResponseEntity<ApiResponse<LiveChatAvailableTime>> response =
                invoke(serviceURl.concat(CREATE_LIVE_CHAT_TIME_SLOT), liveChatAvailableTime, ApiResponse.class, httpHeaders, HttpMethod.POST);
        return response.getBody();
    }

}

