package covid.client.httpclient.service;

import covid.client.httpclient.bulider.ServerApiBuilder;
import covid.client.models.AuthResponse;
import covid.client.models.Constants;
import covid.client.models.Services;
import covid.client.models.request.LoginRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
}
