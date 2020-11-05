package covid.client.logging;
import org.apache.log4j.Logger;

import java.util.HashMap;

public abstract class LoggingManager<T> {

    private static HashMap<Class, Logger> loggerMap;

    public static <T> Logger getLogger(T tClass){
        if (loggerMap == null)
            loggerMap = new HashMap<>();
        if (loggerMap.get(tClass.getClass()) == null)
            loggerMap.put(tClass.getClass(), Logger.getLogger(tClass.getClass()));
        return loggerMap.get(tClass.getClass());
    }

}
