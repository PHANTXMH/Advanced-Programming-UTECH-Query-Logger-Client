package covid.client;
public class DRIVER
{

	public static void main(String[] args)
	{
//		(1605048, "password") || (1608987)
		
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

/*
// move these code to wherever you want to use them
Covid19Client serverClient = ServerClient.getClient(); // crate the API client service instance. this will already have the JWT token from the previous screen when we initialize the abstract class

Session<User> sessionManager = (Session<User>) SessionManager.getByKey(SessionManager.USER_KEY); // get user from session

User user = sessionManager.getData() != null ? sessionManager.getData() : null; // get user data from the session

Assert.notNull(user, "User cannot be null"); // assert that the user is not empty

ApiResponse<Complaints> complaintsApiResponse = serverClient
		.createUserComplaint(new Complaints(new Services(1L), "When is the dealThe is a test complaint and the remaining ones "));

System.out.println(complaintsApiResponse.getMessage());

System.out.println(complaintsApiResponse.getStatus());

List<Complaints> complaintsList = serverClient.getComplaintsByStudentID(user.getId());

complaintsList.forEach(c -> {
	System.out.println(c.getQuery());
	System.out.println(c.getComplainStatus());
});

List<Complaints> complaintsList2 = serverClient.getComplaintsByStatusAndStudentID(user.getId(), ComplainStatus.NEW);

complaintsList2.forEach(c -> {
	System.out.println(c.getQuery());
	System.out.println(c.getComplainStatus());
});

List<Complaints> complaintsList3 = serverClient.getComplaintsByStatus(ComplainStatus.NEW);

if(complaintsList3 != null && !CollectionUtils.isEmpty(complaintsList3)) {
	complaintsList3.forEach(c -> {
		System.out.println(c.getQuery());
		System.out.println(c.getComplainStatus());
	});
}else{
	// list is empty
}

ApiResponse<Complaints> deleteResponse = serverClient.deleteUserComplaint(user.getId());

if(deleteResponse != null && deleteResponse.isSuccess()){
	// delete was successful
	System.out.println(deleteResponse.getMessage());
}else if(deleteResponse != null && !deleteResponse.isSuccess()){
	// unsuccessful
}else{
	// unsuccessful
}

// end of test code -- i will put these code in a unit test class when i have the time but i just added it here to show you how to use the client
*/
