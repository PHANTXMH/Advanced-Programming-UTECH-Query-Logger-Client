package covid.client.httpclient.service;

import covid.client.enumeration.ComplainStatus;
import covid.client.httpclient.bulider.ServerApiBuilder;
import covid.client.models.*;
import covid.client.models.request.LoginRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
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
}
