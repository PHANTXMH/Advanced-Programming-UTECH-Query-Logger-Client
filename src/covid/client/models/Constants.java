package covid.client.models;

public class Constants {
    public static final String SERVICE_BASE_URL = "https://ap-utech-2020-covid19-chat.herokuapp.com/v1/";
    public static final String AUTHENTICATION_SERVICE_URL_PATH = "auth/login";
    public static final String GET_ALL_SERVICES_URL = "services/all";
    public static final String CREATE_COMPLAINTS_SERVICES_URL = "complaints/create";
    public static final String LIST_COMPLAINTS_BY_STUDENT_ID_SERVICES_URL = "complaints/by/student/id/student_id";
    public static final String LIST_COMPLAINTS_BY_STUDENT_ID_AND_STATUS_SERVICES_URL = "complaints/by/student/id/and/status/student_id/status_";
    public static final String LIST_COMPLAINTS_BY_STATUS_SERVICES_URL = "complaints/all/distinct/user/and/status/status_";
    public static final String DELETE_COMPLAINT_BY_ID_SERVICES_URL = "complaints/delete/complaint_id";
}
