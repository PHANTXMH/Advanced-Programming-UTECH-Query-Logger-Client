package covid.client.configuration;

import covid.client.logging.LoggingManager;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashMap;

public class ApplicationConfiguration {

    private static HashMap<String, Object> appSettings;

    public static String getPropertyValue(final String key) {
        String value = null;
        if (appSettings == null)
            appSettings = new HashMap<String, Object>();
        if(!appSettings.containsKey(key)){
            try {
                InitialContext initialContext = new InitialContext();
                value = (String) initialContext.lookup(String.format("java:comp/env/%s", key));
                appSettings.put(key, value); // add value to map
            }catch (NamingException ex){
                LoggingManager.getLogger(ApplicationConfiguration.class).info(ex.getMessage());
            }
        }
        return value;
    }

}
