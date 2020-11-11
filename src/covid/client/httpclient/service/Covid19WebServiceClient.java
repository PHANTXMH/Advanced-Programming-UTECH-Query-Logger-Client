package covid.client.httpclient.service;

import covid.client.logging.LoggingManager;
import covid.client.models.Services;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

public abstract class Covid19WebServiceClient<T> {

    public static final String AUTHENTICATE_USER = "/auth/login";

    private Covid19Client covid19Client;

    private RestTemplate restTemplate;

    private RestTemplate getClient(){
        if (restTemplate ==null)
            restTemplate = new RestTemplate();
        return this.restTemplate;
    }

    public Covid19WebServiceClient(){
        super();
    }

    protected Covid19WebServiceClient setUpClient(Covid19Client covid19Client) throws Exception{
        if(covid19Client == null)
            throw new Exception("Covid 19 Client was null");
        this.covid19Client = covid19Client;
        return this;
    }

    protected ResponseEntity<T> invoke(final String url, final Object request, final Class<T> response, HttpHeaders httpHeaders, HttpMethod httpMethod){
        LoggingManager.getLogger(this).info(String.format("Attempting to get date from URL:  %s", url));
        try {
            HttpEntity<Object> entity = new HttpEntity<>(request, httpHeaders);
            return getClient().exchange(url, httpMethod, entity, response);
        }catch (Throwable e){
            LoggingManager.getLogger(this).error(e.getCause());
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
    }

    protected ResponseEntity<T> invoke(final String url, final Object request, ParameterizedTypeReference<T> parameterizedTypeReference, HttpHeaders httpHeaders, HttpMethod httpMethod){
        try {
            LoggingManager.getLogger(this).info(String.format("Attempting to get date from URL:  %s", url));
            HttpEntity<Object> entity = new HttpEntity<>(request, httpHeaders);
            return getClient().exchange(url, httpMethod, entity, parameterizedTypeReference);
        }catch (Throwable e){
            LoggingManager.getLogger(this).error(e.getCause());
        }
        return null;
    }


    public Covid19Client getCovid19Client() {
        return covid19Client;
    }

    public void setCovid19Client(Covid19Client covid19Client) {
        this.covid19Client = covid19Client;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



}
