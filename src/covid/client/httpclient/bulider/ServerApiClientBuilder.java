package covid.client.httpclient.bulider;

import covid.client.httpclient.service.Covid19Client;
import covid.client.models.AuthResponse;

public class ServerApiClientBuilder extends Covid19Client {

    public static ServerApiClientBuilder defaultClient(){
        return new ServerApiClientBuilder();
    }

    public Covid19Client buildWithToken(final String serviceURl, final AuthResponse authResponse) throws Exception{
        return defaultClient().build(serviceURl, authResponse);
    }


}
