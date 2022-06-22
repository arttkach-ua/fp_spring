package com.epam.tkach.carrent.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NoSuchCarException extends Exception{
    private static final Logger logger = LogManager.getLogger(NoSuchCarException.class);
    private final String ERROR_MESSAGE = "No such car exception was thrown";


    public NoSuchCarException() {
        logger.error(ERROR_MESSAGE);
    }

    public NoSuchCarException(String message) {
        super(message);
        logger.error(ERROR_MESSAGE, message);
    }

    public NoSuchCarException(String message, Throwable cause) {
        super(message, cause);
        logger.error(ERROR_MESSAGE, message, cause);
    }

    public NoSuchCarException(Throwable cause) {
        super(cause);
    }

    public NoSuchCarException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error(ERROR_MESSAGE, message, cause);
    }
}
