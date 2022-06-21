package com.epam.tkach.carrent.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NoSuchCompleteSetException extends Exception{
    private static final Logger logger = LogManager.getLogger(NoSuchCompleteSetException.class);

    public NoSuchCompleteSetException() {
        logger.error("No such complete set exception was thrown");
    }

    public NoSuchCompleteSetException(String message) {
        super(message);
        logger.error("No such complete set exception was thrown", message);
    }

    public NoSuchCompleteSetException(String message, Throwable cause) {
        super(message, cause);
        logger.error("No such complete set exception was thrown", message, cause);
    }

    public NoSuchCompleteSetException(Throwable cause) {
        super(cause);
    }

    public NoSuchCompleteSetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error("No such complete set exception was thrown", message, cause);
    }
}
