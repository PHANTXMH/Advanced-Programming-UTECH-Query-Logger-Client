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

public class DRIVER
{

	public static void main(String[] args)
	{
//		(1605048, "password")
		new GUIAuthentication().logIn();
		
	}
}

//StudentTOKEN Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNjA1MDQ4IiwiaWF0IjoxNjA0NDUxMzg4LCJleHAiOjE2MDQ3NjI0Mjh9.zARhLoCB5ZmSnk4XbekAM_1sfmY-fmPUQa4hRrjDWHJH-4KcD6ypqP8Gznv1oUlH-wZ56Ru0NBDZW98fxe-Pxg
//STAFF TOKEN Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTA5OTk5IiwiaWF0IjoxNjA0NTAxMTk3LCJleHAiOjE2MDQ4MTIyMzd9.6PJkHWUDxTfmHASimDMdP9ny3faPcGHxvKI16NyoDS9YPzCUr2EqExtm_wIBv2BYzGvIhWH6XiFR2Wi1tiR4PA
/*
AuthenticationController authenticationController = new AuthenticationController();
AuthResponse authResponse = authenticationController.authenticateUser(new LoginRequest(uname,pwrd));

if(authResponse != null && authResponse.getToken() != null)
{
	
	try {
		Covid19Client covid19Client = ServerApiClientBuilder.defaultClient().buildWithToken(Constants.SERVICE_BASE_URL, authResponse);

		List<Services> services = covid19Client.getAllServices();

		LoggingManager.getLogger(DRIVER.class).info(services.size());

		services.forEach(r -> System.out.println(r.getName()));
	} catch (Exception e1) {
		e1.printStackTrace();
	}
}else{
	LoggingManager.getLogger(DRIVER.class).error("Authentication failed");
}
*/