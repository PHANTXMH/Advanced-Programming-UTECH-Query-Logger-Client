package covid.client.httpclient.service;

import covid.client.models.Session;
import covid.client.models.User;

import java.util.HashMap;

public abstract class SessionManager {

    public static final String USER_KEY = "CURRENT_USER";

    private static HashMap<String, Object> session;

    public static void createUserSession(final String key, final User user){
        Session<User> session = new Session<>(user, true);
        getSessionInstance().put(key, session);
    }

    public static boolean removeByKey(final String key){
        if(getSessionInstance().containsKey(key)){
            getSessionInstance().remove(key);
            return true;
        }
        return false;
    }

    public static Session getByKey(final String key){
        if(getSessionInstance().containsKey(key)){
            return (Session) getSessionInstance().get(key);
        }
        return null;
    }

    private static HashMap<String, Object> getSessionInstance(){
        if (session == null)
            session = new HashMap<String, Object>();
        return session;
    }

}
