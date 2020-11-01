package covid.client;

import covid.client.controllers.AuthenticationController;
import covid.client.httpclient.bulider.ServerApiClientBuilder;
import covid.client.httpclient.service.Covid19Client;
import covid.client.logging.LoggingManager;
import covid.client.models.AuthResponse;
import covid.client.models.Constants;
import covid.client.models.Services;
import covid.client.models.request.LoginRequest;

import java.util.List;

public class DRIVER {

	public static void main(String[] args) throws Exception {


		AuthenticationController authenticationController = new AuthenticationController();

		AuthResponse authResponse = authenticationController.authenticateUser(new LoginRequest(1605048, "password"));

		if(authResponse != null && authResponse.getToken() != null){

			// create only one instance of this class and use it to consume the API's
			Covid19Client covid19Client = ServerApiClientBuilder
					.defaultClient()
					.buildWithToken(Constants.SERVICE_BASE_URL, authResponse);

			// sample api call after you redirect user to dashboard

			List<Services> services = covid19Client.getAllServices();

			LoggingManager.getLogger(DRIVER.class).info(services.size());

			services.forEach(r -> System.out.println(r.getName()));


		}else{
			LoggingManager.getLogger(DRIVER.class).error("Authentication failed");
		}
	}
}
