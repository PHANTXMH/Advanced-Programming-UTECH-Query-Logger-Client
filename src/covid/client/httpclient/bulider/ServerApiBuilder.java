package covid.client.httpclient.bulider;

import covid.client.httpclient.service.Covid19Client;
import covid.client.models.AuthResponse;

public abstract class ServerApiBuilder extends Covid19Client {

    private String serviceURl;

    private AuthResponse authResponse;

    protected ServerApiBuilder(){
        super();
    }

    protected ServerApiBuilder(final String serviceURl, AuthResponse authResponse){
        super();
        this.authResponse = authResponse;
        this.serviceURl = serviceURl;
    }

    public ServerApiBuilder build(String serviceURl, AuthResponse authResponse) throws Exception{
        this.authResponse = authResponse;
        this.serviceURl = serviceURl;
        return this;
    }

}
