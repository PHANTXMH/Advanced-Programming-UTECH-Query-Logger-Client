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
    public static final String ALL_USERS = "users/all";
    public static final String ALL_USERS_BY_ROLE = "users/by/role/role_";

    // complaint responses
    public static final String REPLY_TO_COMPLAINT_SERVICE_URL = "response/reply";
    public static final String READ_RESPONSE_SERVICE_URL = "response/read/response_id";


    public static final String UPDATE_COMPLAINT_STATUS = "complaints/update/status/id_/status_";

    public static final String CREATE_LIVE_CHAT_AVAILABILITY = "availability/chat/time/create";

    public static final String CREATE_LIVE_CHAT_TIME_SLOT= "availability/chat/time/add/time/slot";



}
