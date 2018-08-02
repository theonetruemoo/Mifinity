package za.co.mifinity.interview.util;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;

public class LoggerService {

    public static void logInfo(String message, Logger log) {
        log.info(message);
    }

    public static void logError(Exception e, Logger log) {
        log.error(ExceptionUtils.getStackTrace(e));
    }

    public static void logError(String e, Logger log) {
        log.error(e);
    }

    public static void logDebug(String message, Logger log) {
        log.debug(message);
    }
}
