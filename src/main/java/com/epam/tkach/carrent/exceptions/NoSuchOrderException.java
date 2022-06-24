package com.epam.tkach.carrent.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NoSuchOrderException extends Exception{
    private static final Logger logger = LogManager.getLogger(NoSuchUserException.class);
    private final String ERROR_MESSAGE = "No such order exception was thrown";

    public NoSuchOrderException() {
        logger.error(ERROR_MESSAGE);
    }

    public NoSuchOrderException(String message) {
        super(message);
        logger.error(ERROR_MESSAGE, message);
    }

    public NoSuchOrderException(String message, Throwable cause) {
        super(message, cause);
        logger.error(ERROR_MESSAGE, message, cause);
    }

    public NoSuchOrderException(Throwable cause) {
        super(cause);
    }

    public NoSuchOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error(ERROR_MESSAGE, message, cause);
    }
}
