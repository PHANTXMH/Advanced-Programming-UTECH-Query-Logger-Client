package covid.client.httpclient.service;

import covid.client.httpclient.bulider.ServerApiClientBuilder;
import covid.client.models.AuthResponse;
import covid.client.models.Constants;

public abstract class ServerClient {

    private static  Covid19Client covid19Client;

    public static Covid19Client createClient(AuthResponse authResponse) throws Exception {
        covid19Client =
                ServerApiClientBuilder.defaultClient().buildWithToken(Constants.SERVICE_BASE_URL, authResponse);
        return covid19Client;
    }

    public static Covid19Client getClient(){
        return covid19Client;
    }

}
